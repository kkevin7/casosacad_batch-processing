/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.facades.casosAcad;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author jssbbonilla
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public boolean creator(T entity) {
        try {
            getEntityManager().persist(entity);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

//    public List<T> findRange(int[] range) {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        q.setMaxResults(range[1]);
//        q.setFirstResult(range[0]);
//        return q.getResultList();
//    }
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    /*---------- Metodos Añadidos -------------------*/

    public List<T> findRange(int first, int pageSize) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }

    public List<T> findBy(String parameter, String value, int first, int pageSize) {
        //SELECT p FROM Requisito AS p WHERE p.nombre LIKE "s%"
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        c.select(t).where(cb.like(t.<String>get(parameter), value + "%"));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }
    
    public List<T> filtroForaneo(String parameter, String parameter2, String value, int first, int pageSize){
        //SELECT p FROM Requisito AS p WHERE p.nombre LIKE "s%" AND p.idTipoRequisito.idTipoRequisito="1"
        //SELECT p FROM Requisito AS p WHERE p.p.idTipoRequisito.idTipoRequisito LIKE "s%"
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        c.select(t).where(cb.like(t.<String>get(parameter).<String>get(parameter2), value+"%"));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }

}

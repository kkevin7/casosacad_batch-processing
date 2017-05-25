/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.facades.casosAcad;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

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
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        c.select(t).where(cb.like(t.<String>get(parameter), value + "%"));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }
     
    public List<T> findByAll(String parameter, String value){
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        c.select(t).where(cb.like(t.<String>get(parameter), value+"%"));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }

//    public List<T> findByExt(String parameter, Object value) {
//        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
//        javax.persistence.criteria.Root<T> t = c.from(entityClass);
//        t.join(parameter);
//        c.select(t).where(cb.equal(t.<String>get(parameter), value ));
//        javax.persistence.Query q = getEntityManager().createQuery(c);
//        return q.getResultList();
//   } 

 
 
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
    public List<T> filtroForaneoall(String parameter, String parameter2, String value){
        //SELECT p FROM Requisito AS p WHERE p.nombre LIKE "s%" AND p.idTipoRequisito.idTipoRequisito="1"
        //SELECT p FROM Requisito AS p WHERE p.p.idTipoRequisito.idTipoRequisito LIKE "s%"
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        c.select(t).where(cb.like(t.<String>get(parameter).<String>get(parameter2), value+"%"));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }
    
      
      // #########################################  WALTER #########################
  


         public List<T> findByJoined(String parameter, Object value){

        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        t.join(parameter);
        c.select(t).where(cb.equal(t.get(parameter), value));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }
    public List<T> findByMultiple(String parameter1, Object value1, String parameter2, Object value2){

        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        
        List<Predicate> predicates = new ArrayList<>();
        
          predicates.add(cb.equal(t.<String>get(parameter1), value1));
          predicates.add(cb.equal(t.<String>get(parameter2), value2));
        
        
        
        c.select(t).where(predicates.toArray(new Predicate[]{}));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }
    
    public List<T> findDistinct(String parameter){
       
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        c.select((Selection<? extends T>) t.get(parameter)).distinct(true);
        
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }
    
        public List<T> findByTriple(String parameter1, Object value1, String parameter2, Object value2, String parameter3, Object value3){

        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        
        List<Predicate> predicates = new ArrayList<>();
        
          predicates.add(cb.equal(t.<String>get(parameter1), value1));
          predicates.add(cb.equal(t.<String>get(parameter2), value2));
         
          predicates.add(cb.equal(t.<String>get(parameter3), value3));
        c.select(t).where(predicates.toArray(new Predicate[]{}));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }
      
      
      
      

}

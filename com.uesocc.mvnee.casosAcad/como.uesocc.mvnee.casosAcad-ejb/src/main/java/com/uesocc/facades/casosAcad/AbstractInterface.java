/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.facades.casosAcad;

import java.util.List;

/**
 *
 * @author jssbbonilla
 */
public interface AbstractInterface<T> {
    
    void create(T paso);

    void edit(T paso);

    void remove(T paso);

    T find(Object id);

    List<T> findAll();

//    List<T> findRange(int[] range);

    List<T> findRange(int first, int pageSize);
    
    int count();
    
    //Agregados
    
    boolean creator(T paso);
    
    public List<T> findBy(String parameter, String value, int first, int pageSize);
    
    public List<T> filtroForaneo(String parameter, String parameter2, String value, int first, int pageSize);
    
}

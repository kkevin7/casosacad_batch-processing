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
        
//    public List<T> findByExt(String parameter, Object value);
    
    public List<T> findBy(String parameter, String value, int first, int pageSize);
    
    public List<T> filtroForaneo(String parameter, String parameter2, String value, int first, int pageSize);
    
    public List<T> filtroForaneoall(String parameter, String parameter2, String value);
    
    public List<T> findByAll(String parameter, String value);
    
    
    public List<T> findByJoined(String parameter, Object value);
  
    public List<T> findByMultiple(String parameter1, Object value1, String parameter2, Object value2);
    
    public List<T> findDistinct(String parameter);
        public List<T> findByTriple(String parameter1, Object value1, String parameter2, Object value2, String parameter3, Object value3);

    
    
    


    
}

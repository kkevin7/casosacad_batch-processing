/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.casosAcad.prime.beans;

import com.uesocc.entities.casosAcad.Paso;
import java.util.Comparator;
import org.primefaces.model.SortOrder;
 
public class LazySorter implements Comparator<Paso> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
//    public int compare(Paso car1, Car car2) {
//        try {
//            Object value1 = Car.class.getField(this.sortField).get(car1);
//            Object value2 = Car.class.getField(this.sortField).get(car2);
// 
//            int value = ((Comparable)value1).compareTo(value2);
//             
//            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
//        }
//        catch(Exception e) {
//            throw new RuntimeException();
//        }
//    }

    @Override
    public int compare(Paso o1, Paso o2) {
        try {
            Object value1 = Paso.class.getField(this.sortField).get(o1);
            Object value2 = Paso.class.getField(this.sortField).get(o2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

    
}

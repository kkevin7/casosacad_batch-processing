/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uesocc.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

///**
// *
// * @author jssbbonilla
// */
//@FacesValidator (value = "novacio")
////public class validatorEmpty implements Validator{
////
//////    @Override
//////    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
//////               String texto=  String.valueOf(value);
//////             if(texto.isEmpty() || texto.startsWith(" ") || texto==null){
//////                   
//////                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo Obligatirio",null);
//////                    throw  new ValidatorException(msg);
//////                    
//////        }
//////    }
////
////
////    }
//    


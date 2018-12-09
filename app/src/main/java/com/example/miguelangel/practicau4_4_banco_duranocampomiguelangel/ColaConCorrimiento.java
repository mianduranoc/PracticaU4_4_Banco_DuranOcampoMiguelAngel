package com.example.miguelangel.practicau4_4_banco_duranocampomiguelangel;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Miguel Angel
 */
public class ColaConCorrimiento extends ColaSimple {
    
    public ColaConCorrimiento(int tam) {
        super(tam);
    }
    public boolean encolar(Object dato){
        if (!super.encolar(dato)) {
            if (!corrimiento()) {
              return false;  
            }
            return super.encolar(dato);
        }
       return true; 
    }
    private boolean corrimiento(){
        if (INI==0) {
            return false;
        }
        int T1=0;
        for (int T2=INI; T2 <=FIN; T2++,T1++) {
            vector[T1]=vector[T2];
        }
        INI=0;
        FIN=T1-1;
        return true;
    }

    
}
 
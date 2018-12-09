package com.example.miguelangel.practicau4_4_banco_duranocampomiguelangel;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Miguel Angel
 */
public class ColaSimple {
    protected int INI,FIN;
    protected Object []vector;
    public ColaSimple(int tam){
        INI=FIN=-1;
        vector=new Object[tam];
    }
    public boolean encolar(Object dato){
        if (estaLlena()) {
            return false;
        }
        FIN++;
        if (insertarPrimerElemento()) {
            INI=FIN;
        }
        vector[FIN]=dato;
        return true;
    }
    public boolean desencolar(){
        if (estaVacia()) {
            return false;
        }
        if (unSoloElemento()) {
            FIN=-1;
            INI=FIN;
            return true;
        }
        INI++;
        return true;
    }
    public Object[] mostrado(){
        if (estaVacia()) {
            return null;
        }
        Object temporal[]=new Object[cantidadDatos()];
        for (int i = INI, j=0; i <=FIN; i++,j++) {
            temporal[j]=vector[i];
        }
        return temporal;
    }
    public Object actual(){
        return vector[INI];
    }
    public void actualizarActual(Object actual){
        vector[INI]=actual;
    }
    public int cantidadDatos(){
        return FIN-INI+1;
    }
    protected boolean estaLlena(){
        return FIN==vector.length-1;
    }
    protected boolean estaVacia(){
        return INI==-1 && FIN==-1;
    }
    protected boolean unSoloElemento(){
        return INI==FIN;
    }
    protected boolean insertarPrimerElemento(){
        return INI==-1;
    }
}

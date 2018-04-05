/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import logika.IHra;

/**
 * Třída Observer
 * 
 * @author Margarita Tsakunova
 * @version pro školní rok 2017/2018
 */
public interface Observer {
    
   /**
   * Metoda aktualizace pozorovatele
   */ 
   void update(); 
   
   /**
   * Metoda vytvoření nové hry
   * @param hra
   */ 
   void novaHra(IHra hra);
    
}

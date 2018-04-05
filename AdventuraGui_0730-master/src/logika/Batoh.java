package logika;

import java.util.Map;
import java.util.HashMap;

/**
 *  Třída Batoh - popisuje batoh.
 * 
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *  "Batoh" reprezentuje "úložný prostor" pro sebrané předměty.
 *  Předmět může být přenositelným a naopak. Je vkládán do batohu. Pomocí příkazu
 *  batoh můžeme zjistit obsah batohu.
 *
 *@author     Margarita Tsakunova
 *@version    pro školní rok 2016/2017
 */

public class Batoh
{
    //== Datové atributy ========================================== 
    private static final int KAPACITA = 2; //kolik předmětů lze vložit do batohu
    private Map<String,Predmet> seznamPredmetu; //klíč a k němu přiřazená hodnota
   
    /***************************************************************************
     * Konstruktor třídy
     */
    public Batoh()
    {
        seznamPredmetu = new HashMap<>(); //vytvořená nová mapa, do které se vkládají předměty
    }
    
    /**
     * Metoda vloží předmět do batohu, pokud se tam vejde.
     * 
     * @param predmet
     * @return true, pokud se předmět vloží
     */
    public boolean vlozPredmet(Predmet predmet) {
        if (seznamPredmetu.size() < KAPACITA) { //vloží klíč a hodnotu do mapy
        seznamPredmetu.put(predmet.getNazev(), predmet); 
        return true;
      } 
     
      return false;
    }
   
    /**
     * Metoda k vypsání obsahu batohu.
     * @return 
     */
    public String nazvyPredmetu(){
        
        if(seznamPredmetu.size() == 0) { //počet prvků uložených v mapě je nula
            return "V batohu nic není.";
        }
        String nazvy = "V batohu je/jsou: ";
        for(String nazevPredmetu : seznamPredmetu.keySet()) { //procházení mapy; vrací množinu obsahující klíče - předměty, které jsou v batohu
            nazvy += nazevPredmetu + " ";
        }
        return nazvy;
    }
    
    /**
     * Metoda rozhodne, zda v batohu předmět je.
     * @param nazevPredmetu
     * @return 
     */
    public boolean obsahujePredmet(String nazevPredmetu) {
        return seznamPredmetu.containsKey(nazevPredmetu); //pokud je klíč obsažen v mapě, vrací true
    }
    
                                        
      /**
     * Metoda odebere předmět z batohu.
     * @param nazev
     * @return 
     */
    public Predmet odeberPredmet(String nazev) {
        return seznamPredmetu.remove(nazev); //v mapě se zrusí odpovídající klíč s hodnotou
    }
    
      /**
     * Metoda vrací kapacitu batohu.
     * @return 
     */
    public int getKapacitaBatohu() {
        return KAPACITA;
    }
    // Metoda vrací odkaz na seznam předmětů v batohu
    public Map<String, Predmet> getSeznamPredmetu() {
        return seznamPredmetu;
    }
    
      /**
     * Metoda najde předmět, na který chceme odkázat
     * @param nazvyPredmetu název předmětu, který chceme najít
     * @return 
     */    
    public Predmet getPredmet(String nazvyPredmetu) {
        return seznamPredmetu.get(nazvyPredmetu);
    }
}
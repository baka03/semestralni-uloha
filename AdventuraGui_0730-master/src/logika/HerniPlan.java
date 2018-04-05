package logika;

import java.util.ArrayList;
import java.util.List;
import utils.Observer;
import utils.Subject;


/**
 *  Třída HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */
public class HerniPlan implements Subject{
    
    private Prostor aktualniProstor;
    private boolean vypil; // ověřujeme, zda kapitán Jack Sparrow vypil nějaký předmět    
    private Batoh batoh;
    private boolean vyhra = false;
    private boolean prohra = false;
    private Hra hra;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
     /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví stáj.
     * @param hra
     */
    public HerniPlan(Hra hra) {
        zalozProstoryHry();
        batoh = new Batoh();
        this.hra = hra;
         
    }
   
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví stáj.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        // poslední dva parametry ríkají o pozicích tečky
        Prostor staj = new Prostor("stáj","stáj, ve které se probudil Jack Sparrow", 50, 130);
        Prostor louka = new Prostor("louka", "louka s malinami a květinami. Nachází se tady dům a\n"
            + "vedle něho rostou jahody", 105, 130);
        Prostor opravnaObuvi = new Prostor("opravnaObuvi","potkáš tady svého starého přítele, který\n"
            + "se podělí o tajemství: 'V bažině lze najít drahé mince!' Pokud tyto mince nesebereš, o nic nepřijdeš", 104, 70);
        Prostor bazina = new Prostor("bažina","mazlavá bažina", 175, 100);
        Prostor hospoda = new Prostor("hospoda","hospoda, ve které je nutno se posilnit rumem a/nebo whisky, jinak\n"
            + "nebudeš mít odvahu, abys prošel děsivým lesem", 260, 100);
        Prostor les = new Prostor("les","hustý a děsivý les", 288, 70);
        Prostor lod = new Prostor("loď","loď, na které nás čeká bocman Gibbs, který\n"
            + "ti brání ke vstupu na loď.\n"
            + "Položí ti otázku. Odpovíš-li špatňe, prohraješ.\n"
            + "Otazka zní: 'Kolik je ďáblův tucet?'", 360, 85);
   
        // přiřazují se průchody mezi prostory (sousedící prostory)
        staj.setVychod(louka);
        staj.setVychod(opravnaObuvi);
        louka.setVychod(bazina);
        louka.setVychod(staj);
        opravnaObuvi.setVychod(bazina);
        opravnaObuvi.setVychod(staj);
        bazina.setVychod(hospoda);
        hospoda.setVychod(les);
        les.setVychod(lod);
                
        aktualniProstor = staj;  // hra začíná ve stáji
        
        //předměty, které lze vložit do batohu a jejich zdroje    
        bazina.vlozPredmet(new Predmet("mince", true,"mince.jpg"));
        hospoda.vlozPredmet(new Predmet("rum", true,"rum.jpg"));
        hospoda.vlozPredmet(new Predmet("whisky", true,"whisky.jpg"));
        louka.vlozPredmet(new Predmet("dům", false,"dum.jpg"));
        louka.vlozPredmet(new Predmet("jahody", false,"jahody.jpg"));
    
    }
   
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
     /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory.
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       notifyAllObservers();
    }
    
    /**
     * Metoda rozhodne, zda kapitán Jack Sparrow vypil nějaký nápoj.
     * @return 
     */
    public boolean vypilNapoj(){
        return vypil;
    }
    
    /**
     * Metoda nastavuje, že kapitán Jack Sparrow vypil nějaký nápoj.
     * @param vyp
     */
    public void setVypil(boolean vyp){
        vypil = vyp;
    }
    
   
    /**
     * Metoda vrací obsah batohu.
     * @return 
     */
    public Batoh getBatoh() {
        return batoh;
    }
    
      /**
     *  Metoda rozhodne, zda hra skončila vyhrou.
     *
     *@return     vyhra
     */
    public boolean jeVyhra() {
        return vyhra;
    }

     /**
     *  Metoda nastavuje, že hra skončila vyhrou.
     *
     * @param stav
     */
    public void setVyhra(boolean stav) {
        this.vyhra = stav;
    }

      /**
     *  Metoda rozhodne, zda hra skončila prohrou.
     *
     *@return     prohra
     */
    public boolean jeProhra() {
        return prohra;
    }

    public Hra getHra() {
        return hra;
    }
    
     /**
     *  Metoda nastavuje, že hra skončila prohrou.
     *
     * @param stav
     */
    public void setProhra(boolean stav) {
        this.prohra = stav;
    }
    
    @Override
    public void registerObserver(Observer observer){
        listObserveru.add(observer);
    }
    
    @Override
    public void deleteObserver(Observer observer){
       listObserveru.remove(observer); 
    }
    
    @Override
    public void notifyAllObservers(){
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        } 
    }

}
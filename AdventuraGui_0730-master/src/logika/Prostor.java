package logika;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry.
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class Prostor {

    private String nazev;
    private String popis;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private Map<String,Predmet> seznamPredmetu; // obsahuje seznam všech předmětů v daném prostoru
    
    private double posX;
    private double posY;

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     */
    public Prostor(String nazev, String popis, double posX, double posY) {
        this.nazev = nazev;
        this.popis = popis;
        this.posX = posX;
        this.posY = posY;
        vychody = new HashSet<>();
        seznamPredmetu = new HashMap<>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object obj) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == obj) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(obj instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) obj;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * Metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     * @return 
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v mistnosti/prostoru " + popis + ".\n"
                + popisVychodu();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        
        if (nazevSouseda == null) {
            return null;
         }
       
        for (Prostor sousedni : vychody) {
            
            if (sousedni.getNazev().equals(nazevSouseda)) {
                return sousedni;
            
           }
           
         }
        
        return null;  // prostor nenalezen
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }
    // Vrací seznam všech východů
    public String seznamVychodu() 
    {
        String vracenyText = "vychody:";
        for (Prostor sousedni : vychody) {
             vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }
    // Vrací odkaz na seznam předmětů v této místnosti 
    public Map<String,Predmet> getVeci()
    {
         return this.seznamPredmetu;
    }
    
    
    
       /**
     *  Metoda zjišťuje, zda je předmět v daném prostoru.
     *  
     *  @param nazevPredmetu
     * @return 
     */
    public boolean obsahujePredmet(String nazevPredmetu) {
        return seznamPredmetu.containsKey(nazevPredmetu);
    }
    
     /**
     *  Metoda vloží předmět do prostoru, pokud je v prostoru.
     *  
     *  @param predmet
     * @return 
     */
    public Predmet vlozPredmet(Predmet predmet) {
        seznamPredmetu.put(predmet.getNazev(),predmet);
        if (seznamPredmetu.containsKey(predmet.getNazev())) {
            return predmet;
        }
        
        return null;
    }
    
  
     /**
     * Metoda odebere předmět z prosotru.
     * 
     * @param nazevPredmetu
     * @return nalezenyPredmet, pokud je předmět v prostoru
     */
    public Predmet odeberPredmet (String nazevPredmetu) {
        Predmet nalezenyPredmet;
        if (seznamPredmetu.containsKey(nazevPredmetu)) {
            nalezenyPredmet = seznamPredmetu.get(nazevPredmetu);
            if (nalezenyPredmet.jePrenositelny() ) {
                seznamPredmetu.remove(nazevPredmetu);
                return nalezenyPredmet;
            }
            return null;
        }
        return null;
    }
    
    /**
     * Metoda umožní vypít předmět.
     * @param predmet
     * @return 
     */
    public Predmet vypilPredmet(Predmet predmet) {
        seznamPredmetu.put(predmet.getNazev(),predmet);
        if(seznamPredmetu.containsKey(predmet.getNazev())) {
            return predmet;
        }
        return null;
    }
  
    /** 
    * Metoda vypíše názvy předmětů nacházejících v daném prostoru.
     * @return 
    */ 
   
    public String nazvyPredmetu() { 
       String nazvy = "předměty: "; 
       for (String nazevPredmetu : seznamPredmetu.keySet()){ 
        nazvy += nazevPredmetu + " "; 
       } 
       return nazvy; 
    }

    /**
     * @return the posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * @return the posY
     */
    public double getPosY() {
        return posY;
    }
}

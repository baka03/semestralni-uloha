package logika;



/**
 *  Třída PrikazVyhod implementuje pro hru příkaz vyhod.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Margarita Tsakunova
 * @version   pro školní rok 2016/2017
 */

public class PrikazVyhod implements IPrikaz {
    
    private static final String NAZEV = "vyhoď";
    private HerniPlan plan;

    
    /***************************************************************************
     * Konstuktor třídy
     * 
     * @param plan plán, ve kterém se bude ve hře "vyhazovat" 
     */
    public PrikazVyhod(HerniPlan plan) {
        this.plan = plan;  
  }
    
    /**
     *  Provádí příkaz "vyhoď". Vyhazue předměty z batohu.
     *  Pokud nejsou parametry, vypíše se chybové hlášení
     *
     *  @param parametry - jako  parametr obsahuje jméno předmětu
     *  @return zpráva, kterou vypíše hra hráči
     */ 
    
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo, tak ....
            return "Co mám vyhodit? Musíš zadat název předmětu.";
        }

        String nazevPredmetu = parametry[0];
        Prostor aktualniProstor = plan.getAktualniProstor();
        //zjistí, zda v aktuálním prostoru je předmět
        Batoh batoh = plan.getBatoh();
        Predmet mazany = batoh.getPredmet(nazevPredmetu);

        if (mazany == null) {
            // pokud mazaný předměz není v batohu
            return "Takový předmět s sebou nemám.";            
        }
        else {
            // pokud je předmět smazán z batohu, přesune se do aktualního prostoru
            mazany = batoh.odeberPredmet(nazevPredmetu);
            aktualniProstor.vlozPredmet(mazany);
            plan.notifyAllObservers(); // upozornění!
            return "Vyhodil(a) jsi " + nazevPredmetu;
        }
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}

package logika;



/**
 *  Třída PrikazSeber implementuje pro hru příkaz seber.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Margarita Tsakunova
 * @version   pro školní rok 2016/2017
 */

public class PrikazSeber implements IPrikaz
{
    private static final String NAZEV = "seber";
    private HerniPlan plan;
    
    
    /***************************************************************************
     * Konstuktor třídy
     * 
     * @param plan plán, ve kterém se bude ve hře "sbírat" 
     */
    public PrikazSeber(HerniPlan plan)
    {
        this.plan = plan;
        
    }

    /**
     * Provádí příkaz 'seber'. Sbírá předměty, které se nacházejí v prostoru a které lze sebrat, a pak vkádá je do batohu
     * Pokud nejsou parametry, vypíše se chybové hlášení
     * @return 
     */
    
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo, tak ....
            return "Co mám sebrat? Musíš zadat název předmětu.";
        }

        String nazevPredmetu = parametry[0];
        Prostor aktualniProstor  = plan.getAktualniProstor();
        //zjistí, zda v aktuálním prostoru je předmět
        if (aktualniProstor.obsahujePredmet(nazevPredmetu)) {
            // aktuální prostor obsahuje předmět, a proto tento předmět odebereme
            Predmet predmet = aktualniProstor.odeberPredmet(nazevPredmetu);
            
            if (predmet == null) {
                return "Tento předmět není přenositelný.";
            } else {
                // uložíme předmět do batohu
                 
                if(plan.getBatoh().vlozPredmet(predmet))
                {   
                    plan.notifyAllObservers(); // upozornění!
                    return "Sebral(a) jsi " + nazevPredmetu;
                }
                else
                {
                    aktualniProstor.vlozPredmet(predmet);
                    return "Batoh je plný!";
                }
            }

        }

        //neobsauje předmět
        return "Taková věc tu není!";


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

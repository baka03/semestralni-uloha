package logika;

/**
 *  Třída PrikazBatoh implementuje pro hru příkaz batoh.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Margarita Tsakunova
 * @version   pro školní rok 2016/2017
 */

public class PrikazBatoh implements IPrikaz
{
    private static final String NAZEV = "batoh";
    private HerniPlan plan;
   
    /***************************************************************************
     * Konstruktor třídy
     * 
     * @param plan plán, ve kterém se bude ve hře zobrazovat obsah batohu 
     */
    public PrikazBatoh(HerniPlan plan)
    {
        this.plan = plan;
    }

    /**
     * Metoda slouží pro vypsání obsahu batohu po zadání příkazu batoh.
     * Pokud nejsou parametry, vypíše se chybové hlášení.
     * @return 
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return plan.getBatoh().nazvyPredmetu();
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

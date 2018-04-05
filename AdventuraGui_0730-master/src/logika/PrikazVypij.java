package logika;



/**
 *  Třída PrikazPouzij implementuje pro hru příkaz vypij.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Margarita Tsakunova
 * @version   pro školní rok 2016/2017
 */

public class PrikazVypij implements IPrikaz
{
    private static final String NAZEV = "vypij";
    private HerniPlan plan;   
    
    /**
     *  Konstruktor třídy
     *  
     * @param plan plán, ve kterém se bude ve hře "pít" 
     */    
    public PrikazVypij(HerniPlan plan) {
        this.plan = plan;
    }
    
      /**
     * Provádí příkaz vypij. Zkouší vypít předměty, které jsou už vložené do batohu.
     * @return 
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Co mám vypít?";
        }

        if(parametry.length == 1) {
            switch (parametry[0]) {
                //vyhodnocuje se, zda napsaná hodnota (parametr) odpovídá jedné z těchto
                case "whisky":  return whisky();
                case "rum":  return rum();
                default: return "Takový předmět není v batohu."; //pokud se nenašel žádný z výše uvedených předmětů
            }
        }
        return "Tento příkaz neproběhl.";
    }
    
     /**
     * Metoda zajišťuje, že předmět 'whisky' lze vypít pokud je v batohu.
     */
    private String whisky(){
        if(plan.getBatoh().obsahujePredmet("whisky")){
            plan.getBatoh().odeberPredmet("whisky");
            plan.setVypil(true);
            plan.notifyAllObservers(); // upozornění!
            return "Vypil(a) jsi whisky.";
        }
        return "Nemáš whisky.";
    }
    
     /**
     * Metoda zajišťuje, že předmět 'rum' lze vypít pokud je v batohu.
     */
    private String rum(){
        if(plan.getBatoh().obsahujePredmet("rum")){
            plan.getBatoh().odeberPredmet("rum");
            plan.setVypil(true); 
            plan.notifyAllObservers(); // upozornění!
            return "Vypil(a) jsi rum.";
        }
        return "Nemáš rum.";
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

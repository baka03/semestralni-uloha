package logika;



/**
 *  Třída PrikazOdpovez implementuje pro hru příkaz odpovez.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Margarita Tsakunova
 * @version   pro školní rok 2016/2017
 */

public class PrikazOdpovez implements IPrikaz
{
    private static final String NAZEV = "odpověz";
    private HerniPlan plan;
    private Hra hra;
    
    
     /**
     *  Konstruktor třídy
     *  
     * @param plan plán, ve kterém se bude ve hře "odpovídat" 
     */ 
    public PrikazOdpovez(HerniPlan plan)
    {
        this.plan = plan;
        this.hra = hra;
        
    }
    
    /**
     * Metoda rozhodne, zda bylo na kontrolní otázku odpovězeno správně a ve správném prostoru. Spravná odpoveď je jen "13" nebo "třináct". Bylo-li na kontrolní otázku odpovězeno špatně, hra končí. Odpoví-li hráč správně, vyhrává.
     * @return 
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo, tak ....
            return "Musíš zadat odpověď.";
        }
        if (parametry.length == 1) {
            
            if(plan.getAktualniProstor().getNazev().equals("loď")) { 
              
                if((parametry[0].equals("13") || parametry[0].equals("třináct"))) {
                    plan.setVyhra(true);                    
                    return "Odpověděl(a) jsi správně! Vyhrál(a) jsi!";
                    
             
              } else {
                  plan.setProhra(true);
                  return "Odpověděl(a) jsi špatně! Prohrál(a) jsi!";
                } 
              
                
            }
            else {
                //pokud se příkaz odpověď zadá v jiném prostoru než loď tak...
                return "Nacházíš se ve špatném prostoru."; 
            }
           
        }
        return "odpověď";
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
    


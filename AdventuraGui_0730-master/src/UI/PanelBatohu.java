/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logika.HerniPlan;
import logika.IHra;
import logika.Predmet;
import utils.Observer;

/**
 * Třída PanelBatohu umožňuje zobrazovat list s předměty vpravo v hlavním okně.
 * Zobrazuje předměty, které byly vloženy do batohu
 * 
 * @author Margarita Tsakunova
 * @version pro školní rok 2017/2018
 */
public class PanelBatohu implements Observer{
    
    private HerniPlan plan;
    ListView<Object> list;
    ObservableList<Object> data;
    private TextArea centralText;

    /**
    * Konstruktor pro panel batohu
    * @param plan
    * @param text
    */
    public PanelBatohu(HerniPlan plan, TextArea text) {
       this.plan = plan;
       plan.registerObserver(this);
       centralText = text;
        init();
    }

    /**
    * Metoda vytvoří list pro předměty v batohu
    */
    private void init() {
        list = new ListView<>();
        data = FXCollections.observableArrayList();
        list.setItems(data);
        list.setPrefWidth(150);
        
        list.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            
            /**
            * Metoda vyhazuje předměty z batohu na klik 
            */
            @Override
            public void handle(MouseEvent click)
            {
                if (click.getClickCount() == 1) 
                {
                    int index = list.getSelectionModel().getSelectedIndex() -1; // aby to nehledalo něco, co tam není
                    
                    Map<String, Predmet> seznam;
                    seznam = plan.getBatoh().getSeznamPredmetu();
                    
                    String nazev = "";
                    int pomocna = 0;
                    for (String x : seznam.keySet()) 
                    {
                       if(pomocna == index)
                       {
                           nazev = x;
                       }
                       pomocna++;
                    }
                    
                    if (!nazev.equals("")) // tím právě udělám, aby se na to políčko nedalo klikat
                    {
                    String vstupniPrikaz = "vyhoď "+nazev;
                    String odpovedHry = plan.getHra().zpracujPrikaz("vyhoď "+nazev);

                
                    centralText.appendText("\n" + vstupniPrikaz + "\n");
                    centralText.appendText("\n" + odpovedHry + "\n");
               
                    plan.notifyAllObservers();
                    }
                }
            }
        });
        
        update();
    }
    
    /**
    * Metoda vrací list
    * @return 
    */
    public ListView<Object> getList() {
        return list;
    }
    
    /**
    * Metoda aktualizuje list předmětů v batohu. Zobrazuje obrázky předmětů, 
    * které má hráč u sebe.
    */
    @Override 
    public void update() 
    {        
        Map<String, Predmet> seznam;
        seznam = plan.getBatoh().getSeznamPredmetu();
        data.clear();
        
        data.add("Předměty v batohu:"); // přidání políčka, které nebude reagovat na klikání
        
        for (String x : seznam.keySet()) 
        {
        Predmet pomocny = seznam.get(x);
        ImageView obrazek = new ImageView(new Image(main.Main.class.getResourceAsStream("/zdroje/"+pomocny.getObrazek()), 130, 130, false, false));
        data.add(obrazek);
        }
    }
    
    /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     * @param plan
     */
    public void nastaveniHernihoPlanu (HerniPlan plan){
        this.plan = plan;
        plan.registerObserver(this);
        this.update();
    }

    /**
     * Metoda smaže pozorovatele(tečku na mapě) a vrátí ho do počátečního stavu
     * při spuštění nové hry
     * @param hra
     */
    @Override
    public void novaHra(IHra hra) { // tady tecka se vrati na zacatek!!
        hra.getHerniPlan().deleteObserver(this);
        this.plan = hra.getHerniPlan();
        this.plan.registerObserver(this);
        update();
    } 

}

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
 * Třída PanelPredmetu umožňuje zobrazovat list s předměty vlevo v hlavním okně.
 * Předměty se nachází v různých prostorech
 * 
 * @author Margarita Tsakunova
 * @version pro školní rok 2017/2018
 */
public class PanelPredmetu implements Observer{
    
    private HerniPlan plan;
    ListView<Object> list;
    ObservableList<Object> data;
    private TextArea centralText;

    /**
    * Konstruktor pro panel
    * @param plan
    * @param text
    */
    public PanelPredmetu(HerniPlan plan, TextArea text) {
       this.plan = plan;
       plan.registerObserver(this);
       
       centralText = text;
        init();
    }

    /**
    * Metoda vytvoří list pro předměty
    */
    private void init() {
        list = new ListView<>();
        data = FXCollections.observableArrayList();
        list.setItems(data);
        list.setPrefWidth(200);
        
        list.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            
            /**
            * Metoda sbíra předměty z prostoru na klik 
            */
            @Override
            public void handle(MouseEvent click)
            {
                if (click.getClickCount() == 1) 
                {
                    int index = list.getSelectionModel().getSelectedIndex() -1;// aby to nehledalo něco, co tam není
                    
                    Map<String, Predmet> seznam;
                    seznam = plan.getAktualniProstor().getVeci();
                    
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
                    
                    if (!nazev.equals(""))// tím právě udělám, aby se na to políčko nedalo klikat
                    {
                    String vstupniPrikaz = "seber "+nazev;
                    String odpovedHry = plan.getHra().zpracujPrikaz("seber "+nazev);

                
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
    * Metoda aktualizuje list předmětu. Zobrazuje obrázky předmětů, které se
    * nachází v jednotlivých prostorech
    */
    @Override 
    public void update() 
    {        
        Map<String, Predmet> seznam;
        seznam = plan.getAktualniProstor().getVeci();
        data.clear();
        
        data.add("Předměty v prostoru:"); // přidání políčka, které nebude reagovat na klikání
        
        for (String x : seznam.keySet()) 
        {
        Predmet pomocny = seznam.get(x);
        ImageView obrazek = new ImageView(new Image(main.Main.class.getResourceAsStream("/zdroje/"+pomocny.getObrazek()), 130, 130, false, false));
        data.add(obrazek);
        }
    }
    
    /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry
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
    public void novaHra(IHra hra) {
        hra.getHerniPlan().deleteObserver(this);
        this.plan = hra.getHerniPlan();
        this.plan.registerObserver(this);
        update();
    } 
}
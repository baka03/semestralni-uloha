/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import logika.HerniPlan;
import logika.IHra;
import utils.Observer;

/**
 * Třída PanelVychodu umožňuje zobrazovat list s východy vlevo v hlavním okně
 * 
 * @author Margarita Tsakunova
 * @version pro školní rok 2017/2018
 */
public class PanelVychodu implements Observer
{

    private HerniPlan plan;
    ListView<String> list;
    ObservableList<String> data;
    
    private TextArea centralText;
    private TextField zadejPrikazTextArea;

    /**
     * Konstruktor, který inicializuje panel, zaregistruje ho jako pozorovatele
     * u herního plánu
     *
     * @param plan 
     * @param text
     * @param field
     */
    public PanelVychodu(HerniPlan plan, TextArea text, TextField field)
      {
        this.plan = plan;
        plan.registerObserver(this);

        centralText = text;
        zadejPrikazTextArea = field;
        
        init();
      }
    
    /**
     * Metoda pro inicializaci vlastností listu
     */
    private void init()
      {
        list = new ListView<>();
        data = FXCollections.observableArrayList();
        list.setItems(data);
        list.setPrefWidth(130);
        
        list.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            
            /**
            * Metoda přechází mezi prostory na klik 
            */
            @Override
            public void handle(MouseEvent click)
            {
                if (click.getClickCount() == 1) 
                {
                  if(!list.getSelectionModel().getSelectedItem().equals("Východy:"))
                  {
                    String vstupniPrikaz = "jdi "+list.getSelectionModel().getSelectedItem();
                    String odpovedHry = plan.getHra().zpracujPrikaz("jdi "+list.getSelectionModel().getSelectedItem());
                
                    centralText.appendText("\n" + vstupniPrikaz + "\n");
                    centralText.appendText("\n" + odpovedHry + "\n");
                    
                    
                    if (plan.getHra().konecHry()) 
                    {
                    zadejPrikazTextArea.setEditable(false);
                    centralText.appendText(plan.getHra().vratEpilog());
                    }
                    
                  } 
                    plan.notifyAllObservers();
                }
            }
        });
        update();
      }

    /**
    * Metoda vrací list
    * @return 
    */
    public ListView<String> getList()
      {
        return list;
      }

    /**
     * Metoda pro aktulizaci panelu. Je volána při změně subjektu
     */
    @Override
    public void update()
      {
        String vychody = plan.getAktualniProstor().seznamVychodu();
        data.clear();
        
        data.add("Východy:"); // přidání políčka
        
        String[] oddeleneVychody = vychody.split(" ");
        for (int i = 1; i < oddeleneVychody.length; i++) {
            data.add(oddeleneVychody[i]);
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

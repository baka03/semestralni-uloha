/* Soubor je ulozen v kodovani UTF-8.
* Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. 
*/
package main;

import UI.Mapa;
import UI.MenuPole;
import UI.PanelBatohu;
import UI.PanelPredmetu;
import UI.PanelVychodu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import uiText.TextoveRozhrani;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;

/**
 * Třída Main slouží ke spouštění aplikace
 * 
 * @author Margarita Tsakunova
 * @version pro školní rok 2017/2018
 */
public class Main extends Application {
    
    private Mapa mapa;
    private MenuPole menu;
    private IHra hra;
    private TextArea centerText;// centerText je globální proměnná
    private Stage primaryStage;
    
    private PanelBatohu panelBatohu;
    private PanelPredmetu panelPredmetu;
    private PanelVychodu panelVychodu;
    
    /**
    * Konstruktor vytvoří vše potřebné UI prvky pro hlavní okno
    */
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        hra = new Hra();
        mapa = new Mapa(hra);
        menu = new MenuPole(this);// odkaz na sebe
        
        BorderPane borderPane = new BorderPane();
                
        centerText = new TextArea();
        centerText.setText(hra.vratUvitani());
        centerText.setEditable(false);
        borderPane.setCenter(centerText);
                
        Label zadejPrikazLabel = new Label("Zadej příkaz");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                
         TextField zadejPrikazTextField = new TextField("Sem zadej příkaz");
         zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>() {
         
         /**
         * Metoda zabezpečuje komunikaci s textovým rozhraním hry
         */   
         @Override
         public void handle(ActionEvent event){
         String zadanyPrikaz = zadejPrikazTextField.getText();
         String odpoved = hra.zpracujPrikaz(zadanyPrikaz);
                  
         centerText.appendText("\n" + zadanyPrikaz + "\n");
         centerText.appendText("\n" + odpoved + "\n");
                  
         zadejPrikazTextField.setText("");
                  
         if(hra.konecHry()){
            zadejPrikazTextField.setEditable(false);
         }
                }
         });
                
         FlowPane dolniPanel = new FlowPane();
         dolniPanel.setAlignment(Pos.CENTER);
         dolniPanel.getChildren().addAll(zadejPrikazLabel, zadejPrikazTextField);
         
         //panel prikaz     
         borderPane.setBottom(dolniPanel);
         
         BorderPane listy = new BorderPane();
         panelBatohu = new PanelBatohu(hra.getHerniPlan(), centerText);
         panelVychodu = new PanelVychodu(hra.getHerniPlan(),centerText,zadejPrikazTextField);
         panelPredmetu = new PanelPredmetu(hra.getHerniPlan(),centerText);
         listy.setTop(mapa);
         listy.setCenter(panelPredmetu.getList());
         listy.setRight(panelVychodu.getList());
         
         
         borderPane.setLeft(listy);
         //menu adventury
         borderPane.setTop(menu);
         borderPane.setRight(panelBatohu.getList());
         
         Scene scene = new Scene(borderPane, 1000, 650); // hlavní okno
        
         primaryStage.setTitle("Moje Adventura");
         primaryStage.setScene(scene);
         primaryStage.show();
         
         zadejPrikazTextField.requestFocus();
    }

    /**
     * Metoda, prostřednictvím níž se spouští celá aplikace pomocí
     * zadání příslušného parametru
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args) {
        if(args.length == 0){
            launch(args);
            
        } else {
            if (args[0].equals("-text")){
            IHra hra = new Hra();
             TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
             textoveRozhrani.hraj();
          
        }else{
                System.out.println("Neplatný parametr");
                System.exit(1);
                
                }
        }
        
    }

    /**
    * Metoda vytvoří hovou hru s uvítáním
    */
    public void novaHra() {
        hra = new Hra();
        centerText.setText(hra.vratUvitani());//v tomto textArea se vratí nové uvítání, ale tecka na mape se neposune na začátek
        //to same pro všechny observery
        mapa.novaHra(hra);
        panelBatohu.novaHra(hra);
        panelVychodu.novaHra(hra);
        panelPredmetu.novaHra(hra);
    }

    /**
     * Metoda zobrazí UI prvky
     * 
     * @return the primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}
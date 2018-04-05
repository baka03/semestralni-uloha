/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.Main;

/**
 * Třída MenuPole umožňuje zobrazovat lištu nahoře v hlavním okně.
 * Lišta obsahuje různé položky
 * 
 * @author Margarita Tsakunova
 * @version pro školní rok 2017/2018
 */
public class MenuPole extends MenuBar{
    
    private Main main;
    
    /**
    * Konstruktor pro pole menu
    * @param main
    */
    public MenuPole(Main main){
        this.main = main;
        init();
    }
    
    /**
    * Metoda vytvoří dva menu na liště
    */
    private void init(){
     Menu menuSoubor = new Menu("Adventura");   
     
     MenuItem itemNovaHra = new MenuItem("Nová hra"); // první položka u menu Nová hra
     itemNovaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
     MenuItem itemKonec = new MenuItem("Konec"); // druhá položka
     itemKonec.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
     
     Menu menuHelp = new Menu("Help"); // druhé menu
     
     MenuItem itemOProgramu = new MenuItem("O programu");
     itemOProgramu.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
     MenuItem itemNapoveda = new MenuItem("Nápověda");
     itemNapoveda.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
      
     menuSoubor.getItems().addAll(itemNovaHra, itemKonec);
     menuHelp.getItems().addAll(itemOProgramu, itemNapoveda);
    
      this.getMenus().addAll(menuSoubor, menuHelp);
      
      itemOProgramu.setOnAction(new EventHandler<ActionEvent>(){
          
          /**
          * Metoda vyhazuje předměty z batohu na klik 
          */
          @Override
          public void handle(ActionEvent event){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              
              alert.setTitle("O adventuře");
              alert.setHeaderText("Adventura Jack Sparrow");
              alert.setContentText("Grafická podoba hry\n"
              + "\n"
              + "Verze: pro školní rok 2017/2018\n"
              + "Autor: Margarita Tsakunova");
              alert.initOwner(main.getPrimaryStage());
              alert.showAndWait();
          }    
      });
      
      itemNapoveda.setOnAction(new EventHandler<ActionEvent>(){
          
          /**
          * Metoda zobrazí alert box s určitým obsahem
          */
          @Override
          public void handle(ActionEvent event){ //vytvoreni noveho okna
                  Stage stage = new Stage();
                  stage.setTitle("Nápověda");
                  WebView webview = new WebView();
                  
                  webview.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());
                  
                  stage.setScene(new Scene(webview, 600, 500));
                  stage.show();
          }
      });
      
      itemKonec.setOnAction(new EventHandler<ActionEvent>(){
        
       /**
       * Metoda pro ukončení hry
       */  
        @Override
        public void handle(ActionEvent event){
            System.exit(0);
        }
     });
      
      itemNovaHra.setOnAction(new EventHandler<ActionEvent>(){
        
        /**
        * Metoda zavolá novou instanci hry
        */ 
        @Override
        public void handle(ActionEvent event){
            main.novaHra();
            
        }
     });
        
    }
    
}

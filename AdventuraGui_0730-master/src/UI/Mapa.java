/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 * Třída Mapa umožňuje zobrazovat obrázek s plánem prostorů.
 * Na obrázku bude vyznačen aktuální prostor
 * 
 * @author Margarita Tsakunova
 * @version pro školní rok 2017/2018
 */
public class Mapa extends AnchorPane implements Observer{ //, ObserverHovaHra
    
    private IHra hra;
    public Circle tecka;
    
    /**
    * Konstruktor pro mapu
    * @param hra
    */
    public Mapa(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
        
    }
    
    /**
    * Metoda vytvoří obrázek s mapou
    */
    private void init(){
       ImageView obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.png"),394,217,false,false)); 
       tecka = new Circle(7, Paint.valueOf("red"));
       this.getChildren().addAll(obrazek, tecka);
       update();
       
    }

    /**
    * Metoda aktualizuje pozice tečky na mapě
    */
    @Override
    public void update() {
        this.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosY());
        this.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosX());
    }

    /**
     * Metoda smaže pozorovatele(tečku na mapě) a vrátí ho do počátečního stavu
     * při spuštění nové hry
     * @param hra
     */
    @Override
    public void novaHra(IHra hra) {
        hra.getHerniPlan().deleteObserver(this);
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
    } 
}

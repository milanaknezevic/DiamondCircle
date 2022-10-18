package com.example.diamondcircle.model;

import com.example.diamondcircle.MainController;
import com.example.diamondcircle.MatricaZaPrikaz;
import com.example.diamondcircle.model.mapa.Polje;

import java.util.*;

import static com.example.diamondcircle.Main.log;
import static com.example.diamondcircle.MainController.mainController;
import static com.example.diamondcircle.model.GameService.dimenzija;
import static com.example.diamondcircle.model.GameService.putanjaFigure;
import static java.lang.Thread.sleep;

public class SpecijalnaKarta extends Karta {
    private int brRupa;
    public static List<Polje> poljaNaKomSuRupe = new LinkedList<>();

    public SpecijalnaKarta() {

    }

    public SpecijalnaKarta(String putanja) {
        super(putanja);
        Random rand = new Random();
        this.brRupa = rand.nextInt((dimenzija - 2) + 1) + 2;
    }

    public int getBrRupa() {
        return brRupa;
    }

    public void setBrRupa(int brRupa) {
        this.brRupa = brRupa;
    }

    public void postaviRupe() {
        try {
            Random rand = new Random();
            int i = 0;
            while (i < this.brRupa) {
                Polje x = putanjaFigure.get(rand.nextInt(putanjaFigure.size()));
                if (!x.isImaRupa() && !x.isImaBonus() && !x.equals(putanjaFigure.get(putanjaFigure.size() - 1))) {
                    x.setImaRupa(true);
                    poljaNaKomSuRupe.add(x);
                    mainController.postaviCrneRupe(x);
                    MatricaZaPrikaz.postaviCrneRupeNaMatricu(x);
                    i++;

                    if (x.isImaFigura() && !(x.getFigura() instanceof LebdecaFigura)) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            log(e);
                        }
                        x.getFigura().setFiguraZavrsilaKretanje(true);
                        int a = x.getElement().getX();
                        x.setImaFigura(false);
                        x.setFigura(null);
                        MatricaZaPrikaz.skloniFiguruSaMatrice(x);
                        mainController.skloniFiguru(x);
                    }
                }
            }

            //}

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                log(e);
            }

            synchronized (MainController.lock) {
                for (int j = 0; j < putanjaFigure.size(); j++) {
                    Polje p = putanjaFigure.get(j);
                    int index = putanjaFigure.indexOf(p);
                    putanjaFigure.get(index).setImaRupa(false);
                    mainController.skloniCrneRupe(p);
                    MatricaZaPrikaz.skloniCrneRupeSaMatrice(p);
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        log(e);
                    }
                }
            }
        } catch (Exception e) {
            log(e);
        }
    }
}

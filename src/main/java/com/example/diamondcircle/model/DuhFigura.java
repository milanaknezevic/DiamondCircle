package com.example.diamondcircle.model;

import com.example.diamondcircle.MainController;
import com.example.diamondcircle.model.mapa.Polje;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.diamondcircle.Main.log;
import static com.example.diamondcircle.MainController.mainController;
import static com.example.diamondcircle.model.GameService.*;

public class DuhFigura extends Thread {
    private static DuhFigura instance = null;
    public static Object duhLock = new Object();
    Random rand = new Random();
    public static CopyOnWriteArrayList<Polje> poljaNaKomSuBonusi = new CopyOnWriteArrayList<>();

    public DuhFigura() {
    }

    public static DuhFigura getInstance() {
        if (instance == null) {
            instance = new DuhFigura();
        }
        return instance;
    }

    @Override
    public void run() {
        try {
            while (!krajIgre) {
                int brojDijamanata = rand.nextInt((dimenzija - 2) + 1) + 2;
                int i = 0;
                while (i < brojDijamanata) {
                    if (krajIgre) {
                        break;
                    }
                    synchronized (lock_pause) {
                        if (pauza) {
                            try {
                                lock_pause.wait();
                            } catch (InterruptedException e) {
                                log(e);
                            }
                        }
                    }
                    Polje x = putanjaFigure.get(rand.nextInt(putanjaFigure.size()));
                    if (!x.isImaBonus() && !x.isImaRupa() && !x.equals(putanjaFigure.get(0)) && !x.equals(putanjaFigure.get(putanjaFigure.size() - 1)))//ako vec nema bonus ili ako nije rupa stavi bonus
                    {
                        x.setImaBonus(true);
                        poljaNaKomSuBonusi.add(x);
                        mainController.postaviBonus(x);
                        i++;
                    } else {

                    }

                }
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    log(e);
                }
                synchronized (MainController.lock) {
                    for (int j = 0; j < putanjaFigure.size(); j++) {

                        Polje p = putanjaFigure.get(j);
                        int index = putanjaFigure.indexOf(p);
                        putanjaFigure.get(index).setImaBonus(false);
                        mainController.skloniBonus(p);
                    }
                }

            }
        } catch (Exception e) {
            log(e);
        }
    }
}
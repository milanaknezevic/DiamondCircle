package com.example.diamondcircle.model;

import com.example.diamondcircle.MainController;
import com.example.diamondcircle.MatricaZaPrikaz;
import com.example.diamondcircle.model.mapa.Polje;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.diamondcircle.Main.log;
import static com.example.diamondcircle.MainController.mainController;
import static com.example.diamondcircle.model.GameService.*;
import static java.lang.Thread.sleep;

public abstract class Figura {
    private Boja boja;
    private boolean figuraZavrsilaKretanje=false;
    private boolean figuraPocelaKretanje=false;
    private boolean figuraPreslaCijeluPutanju=false;
    List<Polje> predjenaPolja=new ArrayList<Polje>();
    private Polje pocetnoPolje;
    private Polje poljeNaKojeStajeFigura;
    private Polje poljeSaKojegpocinjeFigura;
    private int bonusZaNaredniPut=0;
    private String ime;
    private Polje poslednjePolje;
    private Polje trenutnoPolje;
    private  boolean izadji=false;

    public Polje getPoslednjePolje() {
        return poslednjePolje;
    }

    public void setPoslednjePolje(Polje poslednjePolje) {
        this.poslednjePolje = poslednjePolje;
    }

    public Polje getPoljeSaKojegpocinjeFigura() {
        return poljeSaKojegpocinjeFigura;
    }

    public void setPoljeSaKojegpocinjeFigura(Polje poljeSaKojegpocinjeFigura) {
        this.poljeSaKojegpocinjeFigura = poljeSaKojegpocinjeFigura;
    }

    private static int id=1;
    private int ukupniPomjeraj;
    private int brojPomjeranjaJedneFigure;

    public Figura(int broj)
    {
        this.ime = "Figura" + id;
        if(broj == 0)
        {
            boja=Boja.CRVENA;
        }
        else  if(broj == 1)
        {
            boja=Boja.ZELENA;
        }else if(broj == 2)
        {
            boja=Boja.PLAVA;
        }
        else  if(broj == 3)
        {
            boja=Boja.ZUTA;
        }
        trenutnoPolje=putanjaFigure.get(0);
        pocetnoPolje=trenutnoPolje;
        poslednjePolje=putanjaFigure.get(putanjaFigure.size()-1);//ako je 7.txt treba biti 24
        id++;
    }

    public Polje getPoljeNaKojeStajeFigura() {
        return poljeNaKojeStajeFigura;
    }

    public void setPoljeNaKojeStajeFigura(Polje poljeNaKojeStajeFigura) {
        this.poljeNaKojeStajeFigura = poljeNaKojeStajeFigura;
    }

    public String getIme() {
        return ime;
    }

    public int getBonusZaNaredniPut() {
        return bonusZaNaredniPut;
    }

    public void setBonusZaNaredniPut(int bonusZaNaredniPut) {
        this.bonusZaNaredniPut = bonusZaNaredniPut;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Boja getBoja() {
        return boja;
    }

    public void setBoja(Boja boja) {
        this.boja = boja;
    }

    public boolean isFiguraPreslaCijeluPutanju() {
        return figuraPreslaCijeluPutanju;
    }

    public void setFiguraPreslaCijeluPutanju(boolean figuraPreslaCijeluPutanju) {
        this.figuraPreslaCijeluPutanju = figuraPreslaCijeluPutanju;
    }

    public int getUkupniPomjeraj() {
        return ukupniPomjeraj;
    }

    public void setUkupniPomjeraj(int ukupniPomjeraj) {
        this.ukupniPomjeraj = ukupniPomjeraj;
    }

    public boolean isFiguraZavrsilaKretanje() {
        return figuraZavrsilaKretanje;
    }

    public void setFiguraZavrsilaKretanje(boolean figuraZavrsilaKretanje) {
        this.figuraZavrsilaKretanje = figuraZavrsilaKretanje;
    }

    public boolean isFiguraPocelaKretanje() {
        return figuraPocelaKretanje;
    }

    public void setFiguraPocelaKretanje(boolean figuraPocelaKretanje) {
        this.figuraPocelaKretanje = figuraPocelaKretanje;
    }

    public int getBrojPomjeranjaJedneFigure() {
        return brojPomjeranjaJedneFigure;
    }

    public void setBrojPomjeranjaJedneFigure(int brojPomjeranjaJedneFigure) {
        this.brojPomjeranjaJedneFigure = brojPomjeranjaJedneFigure;
    }

    public int brojPomjerajaFigure(int indexKrajnjeg)
    {
        int br=0;
        while(indexKrajnjeg < putanjaFigure.size())
        {
            Polje krajnjePolje=putanjaFigure.get(indexKrajnjeg);

            if(krajnjePolje.isImaFigura())
            {
                br++;
                indexKrajnjeg++;

            }
            else {
                break;
            }
        }
        return br;
    }

    public List<Polje> getPredjenaPolja() {
        return predjenaPolja;
    }

    public void setPredjenaPolja(List<Polje> predjenaPolja) {
        this.predjenaPolja = predjenaPolja;
    }

    public void runFigura(Igrac current){
        synchronized (MainController.lock)
        {
            int pom = getBrojPomjeranjaJedneFigure();
            System.out.println("pom " + pom);
            pom+=getBonusZaNaredniPut();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(MatricaZaPrikaz.ANSI_YELLOW + "bonus za naredni put" + bonusZaNaredniPut + MatricaZaPrikaz.ANSI_RESET);
            System.out.println();
            System.out.println();
            System.out.println();
            bonusZaNaredniPut=0;
            if(this instanceof UbrzanoKretanje)
            {
                pom*=2;
            }
            int i=0;
            int indexKrajnjegPolja=putanjaFigure.indexOf(trenutnoPolje) + pom;//nalazi broj pomjeraja
            int br1=brojPomjerajaFigure(indexKrajnjegPolja);
            pom+=br1;
            System.out.println(MatricaZaPrikaz.ANSI_YELLOW + "pom " + pom + MatricaZaPrikaz.ANSI_RESET);

            System.out.println("ukupni pomjeraj " + getUkupniPomjeraj());
           // int indexPoljaNaKojeStajeFigura=putanjaFigure.indexOf(trenutnoPolje)+pom;
           // System.out.println("indeks polja na koje staje fig " + indexPoljaNaKojeStajeFigura);
            setPoljeSaKojegpocinjeFigura(trenutnoPolje);
            if(putanjaFigure.indexOf(trenutnoPolje)+pom<25)
            { setUkupniPomjeraj(pom);
            Polje pomic = putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)+pom);
            setPoljeNaKojeStajeFigura(pomic);
            }
            else {
                int x=putanjaFigure.indexOf(poslednjePolje)-putanjaFigure.indexOf(trenutnoPolje);
                setUkupniPomjeraj(x);
                setPoljeNaKojeStajeFigura(poslednjePolje);
        }
            System.out.println("polje na kom pocinje" + getPoljeSaKojegpocinjeFigura().getElement().getX());
            System.out.println("polje na kom staje " + getPoljeNaKojeStajeFigura().getElement().getX());
         /* try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            mainController.opisKarte(current,this);

            //ovde se treba ispisati na gui opis karte boga mi xd
            while(i<pom && !izadji) {
                //System.out.println(MatricaZaPrikaz.ANSI_YELLOW + "pom " + pom + "i " + i + MatricaZaPrikaz.ANSI_RESET);
                //System.out.println("Pauza");
                synchronized (lock_pause) {
                    if (pauza) {
                        try {
                            lock_pause.wait();
                        } catch (InterruptedException e) {
                            log(e);
                        }
                    }
                }
                if (trenutnoPolje == pocetnoPolje) {
                    predjenaPolja.add(trenutnoPolje);
                    putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setFigura(this);
                    putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setImaFigura(true);
                    if (trenutnoPolje.isImaBonus()) {
                       // System.out.println("Ima bonus");// pom=dodajBonus(pom);// trenutnoPolje.setImaBonus(false);
                        bonusZaNaredniPut++;
                        putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setImaBonus(false);
                        MatricaZaPrikaz.skloniBonusSaMatrice(trenutnoPolje);//mainController.skloniDiamond(trenutnoPolje);
                    }
                    mainController.postaviFiguruNaPolje(trenutnoPolje, this);
                    MatricaZaPrikaz.postaviFiguruNaMatricu(trenutnoPolje, this);
                   // MatricaZaPrikaz.printMatrica();

                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log(e);
                }
                int index = putanjaFigure.indexOf(trenutnoPolje) + 1;//System.out.println("index " + index);
                Polje narednoPolje = trenutnoPolje;
                if (index < putanjaFigure.size()) {
                    narednoPolje = putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje) + 1);
                } else {//System.out.println("KRAJJJJJJJJJJJJJJJJJJJJJJJJJ!!!!!!!");
                    izadji = true;
                    this.setFiguraPreslaCijeluPutanju(true);
                    break;

                }
           /*if(narednoPolje==poslednjePolje)
           {//System.out.println("KRAJJJJJJJJJJJJJJJJJJJJJJJJJ!!!!!!!");
               izadji=true;
               this.setFiguraPreslaCijeluPutanju(true);//break;
           }*/
                if (narednoPolje.isImaFigura() && narednoPolje != poslednjePolje) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log(e);
                    }
                    Polje temp = narednoPolje;
                    while (temp.isImaFigura() && temp != poslednjePolje) {
                        if (temp.isImaBonus()) {
                          //  System.out.println("Ima bonus");// pom=dodajBonus(pom);
                            bonusZaNaredniPut++;
                            putanjaFigure.get(putanjaFigure.indexOf(temp)).setImaBonus(false);
                            MatricaZaPrikaz.skloniBonusSaMatrice(trenutnoPolje);//mainController.skloniDiamond(trenutnoPolje);
                        }
                        predjenaPolja.add(temp);
                        temp = putanjaFigure.get(putanjaFigure.indexOf(temp) + 1);
                        i++;

                    }
                    narednoPolje = temp;
                }
                putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setFigura(null);
                putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setImaFigura(false);
                mainController.skloniFiguru(trenutnoPolje);
                MatricaZaPrikaz.skloniFiguruSaMatrice(trenutnoPolje);
                //MatricaZaPrikaz.printMatrica();
                trenutnoPolje = narednoPolje;
                predjenaPolja.add(trenutnoPolje);
                putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setFigura(this);
                putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setImaFigura(true);// System.out.print("pom " + pom);
                if (trenutnoPolje.isImaBonus()) {
                   // System.out.println("Ima bonus");
                    bonusZaNaredniPut++;
                    putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setImaBonus(false);
                    MatricaZaPrikaz.skloniBonusSaMatrice(trenutnoPolje);
                    mainController.skloniDiamond(trenutnoPolje);
                }
                mainController.postaviFiguruNaPolje(trenutnoPolje, this);
                MatricaZaPrikaz.postaviFiguruNaMatricu(trenutnoPolje, this);// MatricaZaPrikaz.printMatrica();
                i++;

            }
            if (izadji) {
                putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setFigura(null);
                putanjaFigure.get(putanjaFigure.indexOf(trenutnoPolje)).setImaFigura(false);
                mainController.skloniFiguru(trenutnoPolje);
                MatricaZaPrikaz.skloniFiguruSaMatrice(trenutnoPolje);
                // MatricaZaPrikaz.printMatrica();
            }
        }

    }
}

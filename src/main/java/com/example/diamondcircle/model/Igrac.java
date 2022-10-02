package com.example.diamondcircle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Igrac {
    private String ime;
    private List<Figura> figureIgraca = new ArrayList<Figura>();
    private int bojaIgraca;
    Random rand = new Random();
    private boolean igracZavrsio;
    private static int id = 1;
    private Figura trenutnaFigura;
    private boolean igracZavrsioKretanje = false;
    private int brojPomjerajaFigure;//za koje ce pomjeriti svoju figuru

    public Igrac() {
        this.ime = "Igrac" + id;
        ++id;
        this.igracZavrsio = false;

        //igrac zauzima boju za figure
        int boja;
        while (true) {
            boja = rand.nextInt(4);
            if ((GameService.zauzeteBoje.contains(boja))) {
                continue;
            } else {
                GameService.zauzeteBoje.add(boja);
                bojaIgraca = boja;
                break;
            }
        }
        //igrac dobija 4 random figure

        while (this.figureIgraca.size() < 4)//ide manje od 4
        {
            int x = rand.nextInt(3);

            if (x == 0) {
                ObicnaFigura obicnaFigura = new ObicnaFigura(boja);
                this.figureIgraca.add(obicnaFigura);
            } else if (x == 1) {
                LebdecaFigura lebdecaFigura = new LebdecaFigura(boja);
                this.figureIgraca.add(lebdecaFigura);
            } else if (x == 2) {
                SuperBrzaFigura superBrzaFigura = new SuperBrzaFigura(boja);
                this.figureIgraca.add(superBrzaFigura);
            }
        }
        setFigureIgraca(figureIgraca);
        Collections.shuffle(figureIgraca);


    }

    public boolean isIgracZavrsioKretanje() {
        return igracZavrsioKretanje;
    }

    public void setIgracZavrsioKretanje(boolean igracZavrsioKretanje) {
        this.igracZavrsioKretanje = igracZavrsioKretanje;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public List<Figura> getFigureIgraca() {
        return figureIgraca;
    }

    public void setFigureIgraca(List<Figura> figureIgraca) {
        this.figureIgraca = figureIgraca;
    }

    public boolean isIgracZavrsio() {
        return igracZavrsio;
    }

    public void setIgracZavrsio(boolean igracZavrsio) {
        this.igracZavrsio = igracZavrsio;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Igrac.id = id;
    }


    public int getBojaIgraca() {
        return bojaIgraca;
    }

    public void setBojaIgraca(int bojaIgraca) {
        this.bojaIgraca = bojaIgraca;
    }

    public int getBrojPomjerajaFigure() {
        return brojPomjerajaFigure;
    }

    public void setBrojPomjerajaFigure(int brojPomjerajaFigure) {
        this.brojPomjerajaFigure = brojPomjerajaFigure;
    }

    public Figura getTrenutnaFigura() {
        return trenutnaFigura;
    }

    public void setTrenutnaFigura(Figura trenutnaFigura) {
        this.trenutnaFigura = trenutnaFigura;
    }


    public Figura uzmiSlobodnuFiguru(Igrac igrac) {
        return igrac.getFigureIgraca().stream().filter(e -> !e.isFiguraZavrsilaKretanje() && !e.isFiguraPreslaCijeluPutanju()).
                findFirst().orElse(null);
    }

    public void play() {

        Figura temp = uzmiSlobodnuFiguru(this);
        if (temp != null) {
            trenutnaFigura = temp;
            trenutnaFigura.setBrojPomjeranjaJedneFigure(brojPomjerajaFigure);
            System.out.println("Naziv Figure " + trenutnaFigura.getIme());
            System.out.println("boja " + trenutnaFigura.getBoja());
            System.out.println("pomjeraj Figure " + trenutnaFigura.getBrojPomjeranjaJedneFigure());
            //mainController.opisKarte(this,trenutnaFigura);
            trenutnaFigura.runFigura(this);
            //nakon sto se pojede poslednja figura iyvuce mi jos karata pa tek onda kao bude kraj igre :(
           /* Figura pom=uzmiSlobodnuFiguru(this);
            if(pom == null)
            {
                this.setIgracZavrsio(true);
            }*/

        } else {
            System.out.println("Igrac " + this.getIme() + " nema vise figura");
            this.setIgracZavrsio(true);
        }


    }
}

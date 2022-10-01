package com.example.diamondcircle.model;

import com.example.diamondcircle.MainController;
import com.example.diamondcircle.MatricaZaPrikaz;
import com.example.diamondcircle.model.mapa.Element;
import com.example.diamondcircle.model.mapa.Polje;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.diamondcircle.Main.log;
import static com.example.diamondcircle.MainController.mainController;
import static java.lang.Thread.sleep;
import static javafx.application.Application.setUserAgentStylesheet;


public class GameService {

    public static int dimenzija;
    public static int brojIgraca;
    public static boolean krajIgre=false;
    public static  Object lock_pause=new Object();
    public static boolean pauza=false;
    public Karta trenutnaKarta;
    public Igrac trenutniIgrac;
   // private static GameService instance = null;
    public static List<Polje> putanjaFigure = new LinkedList<>();
    public static List<Integer> zauzeteBoje = new ArrayList<>();//na nivou svega treba
    public static int velicina=dimenzija*dimenzija;
    public static Polje[] matrica = new Polje[velicina];
   public int ukupnoVrijeme=0;
    public long trajanjeIgre;


    public int pomjeraj;

    private List<Karta> karte = new LinkedList<>();
    private static List<Igrac> igraci = new LinkedList<>();




    public GameService()
    {
        setPutanjuFigura();
        dodajIgrace();
        dodajKarte();
       // Collections.shuffle(igraci);
        Collections.shuffle(karte);
    }
    /*public static GameService getInstance()
    {
        if(instance == null)
        {
            instance=new GameService();
        }
        return instance;
    }*/
    public  void setPutanjuFigura()
    {

        if(dimenzija == 7)
        {
             Path path=  Path.of("src" + File.separator + "main" +File.separator + "resources" + File.separator + "7.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        }
        else  if(dimenzija == 8)
        {
            Path path=  Path.of("src" + File.separator + "main" +File.separator + "resources" + File.separator + "8.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        }
        else  if(dimenzija == 9)
        {
            Path path=  Path.of("src" + File.separator + "main" +File.separator + "resources" + File.separator + "9.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        }
        else  if(dimenzija == 10)
        {
            Path path=  Path.of("src" + File.separator + "main" +File.separator + "resources" + File.separator + "10.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        }
    }

    public  void citajIzFajlovaZaPutanjuFigure(Path path) {
        try{


            String line= Files.readString(path);
            System.out.println(line);
            List<String> stringList = Arrays.asList(line.split("#"));
            for(String s:stringList)
            {
                int x = Integer.parseInt(s);
                putanjaFigure.add(new Polje(new Element(x)));
            }
        }catch(IOException e){
          log(e);
        }
    }

    public static boolean isPauza() {
        return pauza;
    }

    public static void setPauza(boolean pauza) {
        GameService.pauza = pauza;
    }

    public  void dodajKarte(){

        for(int i=0;i<10;i++)
        {
            karte.add(new ObicnaKarta("src"+File.separator+"main"+File.separator+"resources"+File.separator+
                    "pictures"+File.separator+"1.png"));
            karte.add(new ObicnaKarta("src"+File.separator+"main"+File.separator+"resources"+File.separator+
                    "pictures"+File.separator+"2.png"));
            karte.add(new ObicnaKarta("src"+File.separator+"main"+File.separator+"resources"+File.separator+
                    "pictures"+File.separator+"3.png"));
            karte.add(new ObicnaKarta("src"+File.separator+"main"+File.separator+"resources"+File.separator+
                    "pictures"+File.separator+"4.png"));
        }

        for(int i=0;i<12;i++)
        {
            karte.add(new SpecijalnaKarta("src"+File.separator+"main"+File.separator+"resources"+File.separator+
                    "pictures"+File.separator+"joker.png"));
        }
    }

    public  void dodajIgrace()
    {
        for(int i =0; i<brojIgraca;i++)
        {

            igraci.add(new Igrac());
        }

    }

    public static boolean isKrajIgre() {
        return krajIgre;
    }

    public static void setKrajIgre(boolean krajIgre) {
        GameService.krajIgre = krajIgre;
    }

    public void igra()  throws InterruptedException {

        DuhFigura duhFigura = DuhFigura.getInstance();
        duhFigura.start();
        List<Igrac> pomIgraci = new LinkedList<Igrac>(igraci);
        List<Karta> pomKarta = new LinkedList<Karta>(karte);
        while(!krajIgre)
        {
           /* System.out.println("Da li je pauza");
                 synchronized (MainController.lock)
                    {*/
                        trenutnaKarta=pomKarta.remove(0);
                        mainController.prikaziKartu(trenutnaKarta);
                        if(trenutnaKarta instanceof ObicnaKarta)
                        {
                            trenutniIgrac = pomIgraci.remove(0);
                            System.out.println("trenutni igrac " + trenutniIgrac.getIme());
//                            for (Igrac i: pomIgraci)
//                                System.out.println("svi igraci " +  i.getIme());
                            pomjeraj=((ObicnaKarta) trenutnaKarta).getPomjeraj();
                            trenutniIgrac.setBrojPomjerajaFigure(pomjeraj);
                            trenutniIgrac.play();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                log(e);
                            }
                            if(!trenutniIgrac.isIgracZavrsio())
                            {
                                pomIgraci.add(trenutniIgrac);
                            }
                            if(pomIgraci.size()<1)
                            {
                                krajIgre=true;
                                break;
                            }
                        }
                        else if(trenutnaKarta instanceof SpecijalnaKarta)
                        {
                            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!Opet Spec karta!!!!!!!!!!!!!!!!!!!!!!!!!");
                            mainController.opisSpecijalneKarte(trenutnaKarta);
                            ((SpecijalnaKarta) trenutnaKarta).postaviRupe();
                        }
                        pomKarta.add(trenutnaKarta);
        }
        upisiUFajl();
}

    public void provjeriKraj()
    {
        int br=0;
        for(Igrac i: igraci)
        {
            if(i.isIgracZavrsio())
            {
                br++;
            }
        }
        if(br==igraci.size())
        {
            setKrajIgre(true);
            System.out.println("Kraj igreeee");
           // System.out.println("upisan fajl");
        }
    }
    private String removeLastChar(String s)
    {
//returns the string after removing the last character
        return s.substring(0, s.length() - 1);
    }

    public void upisiUFajl() {
        try {

            String fileName=  "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "example"
                    + File.separator + "diamondcircle" + File.separator + "rezultatiIgre" + File.separator +
                    String.format("IGRA_%d.txt",Calendar.getInstance().getTimeInMillis());
            PrintWriter pw = new PrintWriter(fileName);
            int id = 1;
            for (int i = 0; i < igraci.size(); i++) {
                String igrac = "Igra " + id;
                id++;
                pw.println(igrac + " - " + igraci.get(i).getIme());
                for (Figura figura : igraci.get(i).getFigureIgraca()) {
                    String predjenaPolja="";
                    for (int k=0;k<figura.getPredjenaPolja().size();k++)
                    {
                    predjenaPolja+=figura.getPredjenaPolja().get(k).getElement().getX() + "-";
                    }
                    String string=removeLastChar(predjenaPolja);
                    String stiglaDoCIlja = figura.isFiguraPreslaCijeluPutanju() ? "da" : "ne";
                    pw.println("\t"+ figura.getIme() + " ("+figura.getClass().getSimpleName() +"," + figura.getBoja() + ") - predjeni put (" + string
                            + ") - stigla do cilja (" + stiglaDoCIlja + ")");
                }
                id++;
                pw.println();
            }
            pw.println();
            System.out.println("Ukupno vrijeme trajanja igre: " + trajanjeIgre + "[s]");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pw.println("Ukupno vrijeme trajanja igre: " + trajanjeIgre + "[s]");
            pw.close();

        }
        catch (IOException e) {
           log(e);
        }
    }

    public static List<Igrac> getIgraci() {
        return igraci;
    }

    public void setIgraci(List<Igrac> igraci) {
        this.igraci = igraci;
    }

    public List<Karta> getKarte() {
        return karte;
    }

    public void setKarte(List<Karta> karte) {
        this.karte = karte;
    }

}



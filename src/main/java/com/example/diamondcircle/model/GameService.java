package com.example.diamondcircle.model;

import static com.example.diamondcircle.MainController.mainController;
import com.example.diamondcircle.model.mapa.Element;
import com.example.diamondcircle.model.mapa.Polje;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.example.diamondcircle.Main.log;



public class GameService {

    public static int dimenzija;

    public static int brojIgraca;
    public static boolean krajIgre = false;
    public static Object lock_pause = new Object();
    public static boolean pauza = false;
    public Karta trenutnaKarta;
    public Igrac trenutniIgrac;
    // private static GameService instance = null;
    public static List<Polje> putanjaFigure = new LinkedList<>();
    public static List<Integer> zauzeteBoje = new ArrayList<>();//na nivou svega treba
    public static int velicina = dimenzija * dimenzija;
    public static Polje[] matrica = new Polje[velicina];
    public int ukupnoVrijeme = 0;
    public long trajanjeIgre;


    public int pomjeraj;

    private List<Karta> karte = new LinkedList<>();
    private static List<Igrac> igraci = new LinkedList<>();


    public GameService() {
        setPutanjuFigura();
        dodajIgrace();
        dodajKarte();
        Collections.shuffle(igraci);
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
    public void setPutanjuFigura() {

        if (dimenzija == 7) {
            Path path = Path.of("src" + File.separator + "main" + File.separator + "resources" + File.separator + "7.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        } else if (dimenzija == 8) {
            Path path = Path.of("src" + File.separator + "main" + File.separator + "resources" + File.separator + "8.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        } else if (dimenzija == 9) {
            Path path = Path.of("src" + File.separator + "main" + File.separator + "resources" + File.separator + "9.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        } else if (dimenzija == 10) {
            Path path = Path.of("src" + File.separator + "main" + File.separator + "resources" + File.separator + "10.txt");
            citajIzFajlovaZaPutanjuFigure(path);
        }
    }

    public void citajIzFajlovaZaPutanjuFigure(Path path) {
        try {


            List<String> line = Files.readAllLines(path);
            System.out.println(line);
            List<String> stringList = Arrays.asList(line.get(0).split("#"));
            for (String s : stringList) {
                int x = Integer.parseInt(s);
                putanjaFigure.add(new Polje(new Element(x)));
            }
            String[] parts=line.get(1).split("#");
            mainController.row1=Integer.parseInt(parts[0]);
            mainController.col1=Integer.parseInt(parts[0]);
            mainController.a1=Integer.parseInt(parts[1]);
            mainController.a2=Integer.parseInt(parts[1]);
        } catch (IOException e) {
            log(e);
        }
    }

    public static boolean isPauza() {
        return pauza;
    }

    public static void setPauza(boolean pauza) {
        GameService.pauza = pauza;
    }

    public void dodajKarte() {

        for (int i = 0; i < 10; i++) {
            karte.add(new ObicnaKarta("src" + File.separator + "main" + File.separator + "resources" + File.separator +
                    "pictures" + File.separator + "1.png"));
            karte.add(new ObicnaKarta("src" + File.separator + "main" + File.separator + "resources" + File.separator +
                    "pictures" + File.separator + "2.png"));
            karte.add(new ObicnaKarta("src" + File.separator + "main" + File.separator + "resources" + File.separator +
                    "pictures" + File.separator + "3.png"));
            karte.add(new ObicnaKarta("src" + File.separator + "main" + File.separator + "resources" + File.separator +
                    "pictures" + File.separator + "4.png"));
        }

        for (int i = 0; i < 12; i++) {
            karte.add(new SpecijalnaKarta("src" + File.separator + "main" + File.separator + "resources" + File.separator +
                    "pictures" + File.separator + "joker.png"));
        }
    }

    public void dodajIgrace() {
        for (int i = 0; i < brojIgraca; i++) {

            igraci.add(new Igrac());
        }

    }

    public static boolean isKrajIgre() {
        return krajIgre;
    }

    public static void setKrajIgre(boolean krajIgre) {
        GameService.krajIgre = krajIgre;
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

    public void igra() {
        try {
            DuhFigura duhFigura = DuhFigura.getInstance();
            duhFigura.start();
            List<Igrac> pomIgraci = new LinkedList<Igrac>(igraci);
            List<Karta> pomKarta = new LinkedList<Karta>(karte);
            while (!krajIgre) {
                trenutnaKarta = pomKarta.remove(0);
                mainController.prikaziKartu(trenutnaKarta);
                if (trenutnaKarta instanceof ObicnaKarta) {
                    trenutniIgrac = pomIgraci.remove(0);
                    pomjeraj = ((ObicnaKarta) trenutnaKarta).getPomjeraj();
                    trenutniIgrac.setBrojPomjerajaFigure(pomjeraj);
                    trenutniIgrac.play();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log(e);
                    }
                    if (!trenutniIgrac.isIgracZavrsio()) {
                        pomIgraci.add(trenutniIgrac);
                    }
                    if (pomIgraci.size() < 1) {
                        krajIgre = true;
                        break;
                    }
                } else if (trenutnaKarta instanceof SpecijalnaKarta) {
                    mainController.opisSpecijalneKarte(trenutnaKarta);
                    ((SpecijalnaKarta) trenutnaKarta).postaviRupe();
                }
                pomKarta.add(trenutnaKarta);
            }
            upisiUFajl();

        } catch (Exception e) {
            log(e);
        }
    }

    public Figura uzmiSlobodnuFiguruPOMOC(Igrac igrac) {
        return igrac.getFigureIgraca().stream().filter(e -> !e.isFiguraZavrsilaKretanje() && !e.isFiguraPreslaCijeluPutanju()).
                findFirst().orElse(null);
    }

    private String removeLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }

    public void upisiUFajl() {
        try {

            String fileName = "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "example"
                    + File.separator + "diamondcircle" + File.separator + "rezultatiIgre" + File.separator +
                    String.format("IGRA_%d.txt", Calendar.getInstance().getTimeInMillis());
            PrintWriter pw = new PrintWriter(fileName);
            int id = 1;
            for (int i = 0; i < igraci.size(); i++) {
                String igrac = "Igrac " + id;
                id++;
                pw.println(igrac + " - " + igraci.get(i).getIme());
                for (Figura figura : igraci.get(i).getFigureIgraca()) {
                    String predjenaPolja = "";
                    for (int k = 0; k < figura.getPredjenaPolja().size(); k++) {
                        predjenaPolja += figura.getPredjenaPolja().get(k).getElement().getX() + "-";
                    }
                    String string = removeLastChar(predjenaPolja);

                    String stiglaDoCIlja = figura.isFiguraPreslaCijeluPutanju() ? "da" : "ne";
                    pw.println("\t" + figura.getIme() + " (" + figura.getClass().getSimpleName() + "," + figura.getBoja() + ") - predjeni put (" + string
                            + ") - stigla do cilja (" + stiglaDoCIlja + ")");
                }
                id++;
                pw.println();
            }
            pw.println();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log(e);
            }
            pw.println("Ukupno vrijeme trajanja igre: " + trajanjeIgre + "[s]");
            pw.close();

        } catch (IOException e) {
            log(e);
        }
    }
}



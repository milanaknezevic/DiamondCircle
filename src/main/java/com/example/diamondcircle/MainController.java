package com.example.diamondcircle;

import com.example.diamondcircle.model.*;
import com.example.diamondcircle.model.mapa.Polje;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.diamondcircle.KretanjeFigure.kretanjeFigure;
import static com.example.diamondcircle.Main.log;
import static com.example.diamondcircle.model.GameService.*;
import static java.lang.Thread.sleep;
public class MainController implements Initializable {
    //public static GameService game_service = new GameService();
    private Stage stage=new Stage();
    private Scene scene;
    Figura pom = null;
    private static int brojac=0;
    public static Object lock=new Object();
    private Parent root;

    public static MainController mainController;

    public static int row1;
    public static int col1;
    private static int matricaNacrtana=0;
    public static  GameService game_service;
    public static StringBuilder builder= new StringBuilder();
    @FXML
    public Label brojIgara=new Label();
    @FXML
    public Label LabelaZaVrijemeTrajanjaIgre=new Label();
    @FXML
    public ListView<String> listaFiguraIgraca=new ListView<>();

    @FXML
    public GridPane igraciNabrojani=new GridPane();
    @FXML
    public TextArea opisIgre= new TextArea();
    @FXML
    public javafx.scene.control.TextArea content=new TextArea();
    @FXML
    public ListView<String> fileList=new ListView<>();
    @FXML
    public javafx.scene.control.Button start;
    @FXML
    public Button pause;
    public int pomoc=0;

    String nazivFigureZaPrikazKretanja;
    @FXML
    public javafx.scene.image.ImageView TreutnaKarta=new ImageView();
    @FXML
    public  GridPane matrica=new GridPane();
    @FXML
    private ChoiceBox<Integer> choiceBox1=new ChoiceBox<>();
    @FXML
    private ChoiceBox<Integer> choiceBox2=new ChoiceBox<>();
    ObservableList<Integer> dimenzijeMatriceList = FXCollections
            .observableArrayList(7, 8, 9, 10);
    ObservableList<Integer> brojIgracaList = FXCollections
            .observableArrayList(2,3,4);

    @FXML
    private void inicijalizujChoiceBox() {

        choiceBox1.setItems(dimenzijeMatriceList);
        choiceBox1.setValue(7);
        choiceBox2.setItems(brojIgracaList);
        choiceBox2.setValue(2);

    }

    public void showFiles() {

        File[] files = new File("src"+File.separator+"main"+File.separator+"java"+File.separator+
                "com"+File.separator+"example"+File.separator+"diamondcircle"+File.separator+"results").listFiles();
        fileList.getItems().addAll(Arrays.stream(files).map(File::getName).collect(Collectors.toList()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setBrojOdigranihIgara(brojIgara);
        if(matricaNacrtana == 0)
        {
            nacrtajMatricu();
            matricaNacrtana++;
        }
        setIgrace();
        List<String> figureIgracaList=new ArrayList<>();
        for(int j=0;j<brojIgraca;j++)
        {
            for(int k=0;k<getIgraci().get(0).getFigureIgraca().size();k++)
            {
                System.out.println(getIgraci().get(j).getFigureIgraca().get(k).getIme());
                figureIgracaList.add(getIgraci().get(j).getFigureIgraca().get(k).getIme());
            }
        }

        // Kako sortirati :(
        Collections.sort(figureIgracaList);
        listaFiguraIgraca.getItems().addAll(figureIgracaList);
        listaFiguraIgraca.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                nazivFigureZaPrikazKretanja = listaFiguraIgraca.getSelectionModel().getSelectedItem();
                System.out.println("selected item " + nazivFigureZaPrikazKretanja);
            }
        });

    }

    public void setIgrace()
    {

        for(int j=0;j<brojIgraca;j++)
        {
            int boja=getIgraci().get(j).getBojaIgraca();
            if(boja ==0)//crvena
            {
                Label label=new Label(getIgraci().get(j).getIme()+ "    ");
                label.setTextFill(Color.RED);
                label.setStyle("-fx-font-size: 16");
                igraciNabrojani.add(label,j,0);
            } else if(boja ==1)//zelena
            {
                Label label=new Label(getIgraci().get(j).getIme()+ "    ");
                label.setTextFill(Color.GREEN);
                label.setStyle("-fx-font-size: 16");
                igraciNabrojani.add(label,j,0);
            }
            else if(boja ==2)//plava
            {
                Label label=new Label(getIgraci().get(j).getIme()+ "    ");
                label.setTextFill(Color.BLUE);
                label.setStyle("-fx-font-size: 16");
                igraciNabrojani.add(label,j,0);
            } else//zuta
            {
                Label label=new Label(getIgraci().get(j).getIme()+ "    ");
                label.setTextFill(Color.YELLOW);
                label.setStyle("-fx-font-size: 16");
                igraciNabrojani.add(label,j,0);
            }

        }




    }
    public void pokreni(ActionEvent actionEvent){

        System.out.println("Pokreni");

        if (brojac == 0) {
            new Thread(() -> {
                try {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        log(e);
                    }
                    game_service.igra();
                } catch (InterruptedException e) {
                    log(e);
                }
            }).start();
            new Thread(() -> {
                mjerenjeVremena();
               // measureGameDuration();
            }).start();
            new Thread(() ->
            {
                while (!krajIgre) {
                    try {
                        sleep(1000);
                        MatricaZaPrikaz.printMatrica();
                    } catch (Exception e) {
                        log(e);
                    }
                }
            }

            ).start();
            brojac++;
        }
        start.setVisible(false);
        pause.setVisible(true);
        game_service.setPauza(false);
        synchronized (lock_pause) {
            if (!pauza) {
                lock_pause.notifyAll();
            }
        }


    }
    public void pauzirajSimulaciju(ActionEvent actionEvent) {

        System.out.println("Pauza");
        pause.setVisible(false);
        start.setVisible(true);
        game_service.setPauza(true);


    }

   public void prikaziKartu(Karta karta)
   {

       File file = new File(karta.getPutanjaDoSlike());
       javafx.scene.image.Image image = new Image(file.toURI().toString(), 300, 300, false, false);
       Platform.runLater(() -> TreutnaKarta.setImage(image));

   }

//ovo je pravi kod za diamond
    public void postaviDiamond(Polje p1)
    {int x=getX(p1);
        int y=getY(p1);
        Circle circle=new Circle(10,Color.DEEPPINK);
        Platform.runLater(()->matrica.add(circle,y,x));
    }
    public void skloniDiamond(Polje p1)
    {
        try {int x=getX(p1);
            int y=getY(p1);
            Node currentNode = null;
            ObservableList<Node> childrens = matrica.getChildren();
            for (Node node : childrens) {
                if (node instanceof Circle && matrica.getRowIndex(node) == x && matrica.getColumnIndex(node) == y) {
                    currentNode = node;
                }
            }
            synchronized (lock) {
                Node finalCurrentNode = currentNode;
                if (finalCurrentNode != null) {
                    Platform.runLater(() -> matrica.getChildren().remove(finalCurrentNode));
                }
            }
        }
        catch (Exception e)
        {
            log(e);
        }
    }
   public void skloniDiamondSliciaDiamond(Polje p1)
   {
       try {
           int x=getX(p1);
           int y=getY(p1);
           Node currentNode = null;
           ObservableList<Node> childrens = matrica.getChildren();
           for (Node node : childrens) {
               if (node instanceof ImageView && matrica.getRowIndex(node) == x && matrica.getColumnIndex(node) == y) {
                   currentNode = node;
               }
           }
           synchronized (lock) {
               Node finalCurrentNode = currentNode;
               if (finalCurrentNode != null) {
                   Platform.runLater(() -> matrica.getChildren().remove(finalCurrentNode));
               }
           }
       }
       catch (Exception e)
       {
           log(e);
       }
   }

   public void postaviDiamondSlicicaDiamond(Polje p1) {
       int x=getX(p1);
       int y=getY(p1);
       FileInputStream imageStream = null;
       try {
           imageStream = new FileInputStream("src"+ File.separator+"main"+File.separator+"resources"+File.separator+
                   "slikaBonusa"+File.separator+"diamond.png");
       } catch (FileNotFoundException e) {
           log(e);
       }
       Image image = new Image(imageStream);
     Platform.runLater(()->  matrica.add(new ImageView(image), x, y));


   }
    @FXML
    public void skloniCrneRupe(Polje p1) {
        try {int x=getX(p1);
            int y=getY(p1);
            Node currentNode = null;
            ObservableList<Node> childrens = matrica.getChildren();
            for (Node node : childrens) {
                if (node instanceof Ellipse && matrica.getRowIndex(node) == x && matrica.getColumnIndex(node) == y) {
                    currentNode = node;
                }
            }
            synchronized (lock) {
                Node finalCurrentNode = currentNode;
                if (finalCurrentNode != null) {
                    Platform.runLater(() -> matrica.getChildren().remove(finalCurrentNode));
                }
            }
        }
        catch (Exception e)
        {
            log(e);
        }

    }
    @FXML
    public void postaviCrneRupe(Polje polje)
    {
        int x=getX(polje);
        int y=getY(polje);
        Ellipse elipse=new Ellipse(10,10);
        elipse.setFill(Color.BLACK);
        Platform.runLater(()->matrica.add(elipse,y,x));;
    }

    @FXML
    public void postaviFiguruNaPolje(Polje p1,Figura figura)
    {  int x=getX(p1);
        int y=getY(p1);
        Rectangle rectangle=new Rectangle(row1,col1);
        Label label=new Label();
        if(figura.getBoja().equals(Boja.CRVENA))
        {
            rectangle.setFill(Color.RED);
            if(figura instanceof ObicnaFigura)
            {
                label.setText("OF");
            }
            else if(figura instanceof SuperBrzaFigura)
            {
                label.setText("SF");
            }
            else
            {
                label.setText("LF");
            }
        }
        else if(figura.getBoja().equals(Boja.ZELENA))
        {
            rectangle.setFill(Color.GREEN);
            if(figura instanceof ObicnaFigura)
            {
                label.setText("OF");
            }
            else if(figura instanceof SuperBrzaFigura)
            {
                label.setText("SF");

            }
            else
            {
                label.setText("LF");
            }
        }
        else if(figura.getBoja().equals(Boja.PLAVA))
        {
            rectangle.setFill(Color.BLUE);
            if(figura instanceof ObicnaFigura)
            {
                label.setText("OF");
            }
            else if(figura instanceof SuperBrzaFigura)
            {
                label.setText("SF");
            }
            else
            {
                label.setText("LF");
            }
        }
        else
        {
            rectangle.setFill(Color.YELLOW);
            if(figura instanceof ObicnaFigura)
            {
                label.setText("OF");
            }
            else if(figura instanceof SuperBrzaFigura)
            {
                label.setText("SF");
            }
            else
            {
                label.setText("LF");
            }
        }
        Platform.runLater(()->
        {
            matrica.add(rectangle,y,x);
            matrica.add(label,y,x);
        });
    }

    @FXML
    public void skloniFiguru(Polje p)
    {
        try {
            int x=getX(p);
            int y=getY(p);
            Node currentNode = null;
            Node currentNode1 = null;
            ObservableList<Node> childrens = matrica.getChildren();
            for (Node node : childrens) {
                if (node instanceof Rectangle && matrica.getRowIndex(node) == x && matrica.getColumnIndex(node) == y) {
                    currentNode = node;
                } else if (node instanceof Label && matrica.getRowIndex(node) == x && matrica.getColumnIndex(node) == y) {
                    currentNode1 = node;
                }
            }
            synchronized (lock) {
                Node finalCurrentNode = currentNode;
                Node finalCurrentNode1 = currentNode1;
                if (finalCurrentNode != null) {
                    Platform.runLater(() -> matrica.getChildren().remove(finalCurrentNode));
                }
                if (finalCurrentNode1 != null) {
                    Platform.runLater(() -> matrica.getChildren().remove(finalCurrentNode1));
                }
            }
        }
        catch (Exception e)
        {
            log(e);
        }
    }

    public int getX(Polje p)
    {
        int pozicija=p.getElement().getX();
        return (pozicija-1)/dimenzija;

    }
    public int getY(Polje p)
    {
        int pozicija=p.getElement().getX();
        return (pozicija - 1) % dimenzija ;
    }

    public void setDimension()
    {


        if(dimenzija==7)
        {
            row1=45;
            col1=45;
        }
        else if(dimenzija==8)
        {
            row1=40;
            col1=40;
        }
        else if(dimenzija==9)
        {
            row1=35;
            col1=35;
        }
        else if(dimenzija==10)
        {
            row1=30;
            col1=30;
        }


    }
    public void nacrtajMatricu()
    {
        game_service = new GameService();//game_service = GameService.getInstance();
        mainController = this;
        setDimension();
        int vel = dimenzija * dimenzija;

        for (int i = 0; i < dimenzija; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(col1);
            col.setPrefWidth(col1);
            matrica.getColumnConstraints().add(col);
        }
        for (int i = 0; i < dimenzija; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(row1);
            row.setPrefHeight(row1);
            matrica.getRowConstraints().add(row);

        }
        int content=1;
        for (int i = 0; i < dimenzija; i++) {
            for (int j = 0; j < dimenzija; j++) {
                Text text = new Text(" " + String.valueOf(content));
                text.setStyle("-fx-text-alignment: center");
                matrica.add(text, j, i);
                content++;
            }
        }


    }
    public void mjerenjeVremena()
    {
        int broj=0;
        while(!krajIgre)
        {
            if(!pauza) {
                game_service.trajanjeIgre=broj;
                String vrijeme = broj + " [s]";
                Platform.runLater(() -> LabelaZaVrijemeTrajanjaIgre.setText("Vrijeme trajanja igre: " + vrijeme));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log(e);
                }
                broj++;
            }
            else
            {
                System.out.print("");
            }
        }

    }


   /* public void measureGameDuration() {
     //   return new Thread(() -> {
            int h = 0, m = 0, s = 0;
            while (!krajIgre) {
                if (!pauza) {
                    String time = String.format("%d h %d m %d s", h, m, s);
                   // gameService.setElapsedTime(time);
                    Platform.runLater(() -> LabelaZaVrijemeTrajanjaIgre.setText(time));

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        log(e);
                    }
                    s++;
                    if (s >= 60) {
                        m++;
                        s %= 60;
                    }
                    if (m >= 60) {
                        h++;
                        m %= 60;
                    }
                }
            }
            System.out.printf("Game OVER Total time: %d h %d m %d s%n", h, m, s);
            //gameService.setElapsedTime(String.format("%d h %d m %d s", h, m, s));
        }
*/

    public void setBrojOdigranihIgara(Label brojIgaraLabel) {

        String path = "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "example" + File.separator + "diamondcircle" + File.separator + "rezultatiIgre";
        File file = new File(path);
        int fileCounter = 0;
        String str[] = file.list();
        for (String s : str) {
            File fls = new File(file, s);
            if (fls.isFile() && fls.getName().startsWith("IGRA") && fls.getName().endsWith(".txt")) {
                fileCounter++;
            }
        }
        System.out.println("broj fajlova " + fileCounter);
        brojIgaraLabel.setText("Trenutni broj odigranih igara: " + fileCounter);

    }
    public void switchToRezultati(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Rezultati.fxml"));
            //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Rezultati");
            scene = new Scene(root,900,650);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();        }
        catch (Exception e)
        {
            log(e);
        }
    }

    public void opisKarte(Igrac igrac,Figura trenutnaFigura)
    {


        builder.append("Na potezu je " + igrac.getIme() + " , ");
        builder.append(trenutnaFigura.getIme() + ", prelazi " + trenutnaFigura.getUkupniPomjeraj() + " polja, pomjera se sa pozicije ");
        builder.append(trenutnaFigura.getPoljeSaKojegpocinjeFigura().getElement().getX() + " na " + trenutnaFigura.getPoljeNaKojeStajeFigura().getElement().getX());
        String str=builder.toString();
        Platform.runLater(() -> opisIgre.setText(str));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log(e);
        }
        builder.delete(0,builder.length());

    }

    public void opisSpecijalneKarte(Karta trenutnaKarta)
    {

        builder.append("Specijalna karta, kreirano: " + ((SpecijalnaKarta) trenutnaKarta).getBrRupa() + " rupa.");
        String str = builder.toString();
        Platform.runLater(() -> opisIgre.setText(str));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log(e);
        }
        builder.delete(0, builder.length());


    }


  /*  public void showFileContent(MouseEvent mouseEvent) {
        StringBuilder resultStringBuilder = new StringBuilder();
        String name=fileList.getSelectionModel().getSelectedItem();
        File file = new File("src"+File.separator+"main"+File.separator+"java"+File.separator+
                "com"+File.separator+"example"+File.separator+"diamondcircle"+File.separator+"results" + File.separator + name);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
            content.setText(resultStringBuilder.toString());
        } catch (IOException e) {
            log(e);
        }

    }*/


    public void prikaziKretanjeIzabraneFigure(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("kretanjeFigure.fxml"));
            Scene scene = new Scene(root, 600, 500,Color.GRAY);
            Stage newStage=new Stage();
            newStage.setTitle("kretanje " + nazivFigureZaPrikazKretanja);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();
            for(int j=0;j<brojIgraca;j++)
            {
                for(int k=0;k<getIgraci().get(0).getFigureIgraca().size();k++)
                {
                    if(getIgraci().get(j).getFigureIgraca().get(k).getIme().equals(nazivFigureZaPrikazKretanja))
                    {
                        pom=getIgraci().get(j).getFigureIgraca().get(k);
                    }
                }
            }
            if(pom !=null)
            {
                kretanjeFigure.prikaziKretanjeFigure(pom);
            }
            else{
                System.out.printf("nzm, ubij seee");
            }

        }
        catch (Exception e)
        {
            log(e);
        }

    }
}
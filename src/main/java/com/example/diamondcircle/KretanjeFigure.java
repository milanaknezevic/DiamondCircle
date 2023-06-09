package com.example.diamondcircle;

import com.example.diamondcircle.model.Boja;
import com.example.diamondcircle.model.Figura;
import com.example.diamondcircle.model.GameService;
import com.example.diamondcircle.model.mapa.Polje;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.diamondcircle.Main.log;
import static com.example.diamondcircle.MainController.mainController;
import static com.example.diamondcircle.model.GameService.dimenzija;

public class KretanjeFigure implements Initializable {
    public GridPane figurePane = new GridPane();
    private static int matricaNacrtana = 0;
    public static GameService game_service;
    public static KretanjeFigure kretanjeFigure;


    public void nacrtajMatricuKretanjaFigure() {
      //  setDimension();
        for (int i = 0; i < dimenzija; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(mainController.col1);
            col.setPrefWidth(mainController.col1);
            figurePane.getColumnConstraints().add(col);
        }
        for (int i = 0; i < dimenzija; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(mainController.row1);
            row.setPrefHeight(mainController.row1);
            figurePane.getRowConstraints().add(row);
        }
        int content = 1;
        for (int i = 0; i < dimenzija; i++) {
            for (int j = 0; j < dimenzija; j++) {
                Text text = new Text(" " + String.valueOf(content));
                text.setStyle("-fx-text-alignment: center");
                figurePane.add(text, j, i);
                content++;
            }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nacrtajMatricuKretanjaFigure();
        kretanjeFigure = this;
    }

    public void prikaziKretanjeFigure(Figura figura) {
        try {
            for (Polje p : figura.getPredjenaPolja()) {
                int pozicija = p.getElement().getX();
                int y = (pozicija - 1) % dimenzija;
                int x = (pozicija - 1) / dimenzija;
                Rectangle rectangle = new Rectangle(mainController.row1, mainController.col1);
                Boja boja = figura.getBoja();
                setBoja(rectangle, boja);
                Platform.runLater(() ->
                {
                    figurePane.add(rectangle, y, x);
                });
            }
        } catch (Exception e) {
            log(e);
        }

    }

    public void setBoja(Rectangle rectangle, Boja boja) {
        if (boja == Boja.CRVENA) {
            rectangle.setFill(Color.RED);
        } else if (boja == Boja.ZUTA) {
            rectangle.setFill(Color.YELLOW);
        } else if (boja == Boja.PLAVA) {
            rectangle.setFill(Color.BLUE);
        } else {
            rectangle.setFill(Color.GREEN);
        }
    }
}

package com.example.diamondcircle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.diamondcircle.Main.log;
import static com.example.diamondcircle.model.GameService.brojIgraca;
import static com.example.diamondcircle.model.GameService.dimenzija;

public class UnesiParametre implements Initializable {
    private Stage stage = new Stage();
    private Scene scene;
    @FXML
    private ChoiceBox<Integer> choiceBox1 = new ChoiceBox<>();
    @FXML
    private ChoiceBox<Integer> choiceBox2 = new ChoiceBox<>();
    ObservableList<Integer> dimenzijeMatriceList = FXCollections
            .observableArrayList(7, 8, 9, 10);
    ObservableList<Integer> brojIgracaList = FXCollections
            .observableArrayList(2, 3, 4);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inicijalizujChoiceBox();

        } catch (Exception e) {
            log(e);
        }
    }

    public void switchToScene2(ActionEvent event) throws IOException, InterruptedException {

        dimenzija = choiceBox1.getSelectionModel().getSelectedItem();//System.out.print(selectedDimenzija);
        brojIgraca = choiceBox2.getSelectionModel().getSelectedItem();//System.out.print(selectedBrojIgraca);
        MatricaZaPrikaz mzp = new MatricaZaPrikaz();
        for (int i = 0; i < dimenzija * dimenzija; i++) {
            mzp.matricaPOM[i] = new MatricaZaPrikaz();
        }
        Parent root = FXMLLoader.load(getClass().getResource("Igra.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Igra");
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
       /* if ((dimenzija < 7 || dimenzija > 10) || (brojIgraca < 2 || brojIgraca > 4)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Neispavan unos!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            System.exit(0);
        } else {
            stage.show();
        }*/
        stage.show();


    }

    @FXML
    private void inicijalizujChoiceBox() {
        choiceBox1.setItems(dimenzijeMatriceList);
        choiceBox1.setValue(7);
        choiceBox2.setItems(brojIgracaList);
        choiceBox2.setValue(2);
    }
}

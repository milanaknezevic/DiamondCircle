package com.example.diamondcircle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.example.diamondcircle.Main.log;

public class Rezultati implements Initializable {

    @FXML
    public TextArea sadrzajFajla = new TextArea();
    String currentFile;
    @FXML
    public ListView<String> fileList = new ListView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        izlistajFajlove();
    }

    public void izlistajFajlove() {
        String pathname = "src" + File.separator + "main" + File.separator + "java" + File.separator +
                "com" + File.separator + "example" + File.separator + "diamondcircle" + File.separator + "rezultatiIgre";
        File[] files = new File(pathname).listFiles();
        fileList.getItems().addAll(Arrays.stream(files).map(File::getName).collect(Collectors.toList()));
        fileList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                StringBuilder resultStringBuilder = new StringBuilder();
                currentFile = fileList.getSelectionModel().getSelectedItem();

                File file = new File("src" + File.separator + "main" + File.separator + "java" + File.separator +
                        "com" + File.separator + "example" + File.separator + "diamondcircle" + File.separator + "rezultatiIgre" + File.separator + currentFile);
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        resultStringBuilder.append(line).append("\n");
                    }
                    sadrzajFajla.setText(resultStringBuilder.toString());
                } catch (IOException e) {
                    log(e);
                }
            }
        });
    }


}

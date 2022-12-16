package com.example.diamondcircle;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    public static Stage primaryStage;
    public static Scene scene;


    public static final Logger logger = Logger.getLogger("MyLog");
    private static FileHandler fileHandler;

    //Logger
    static {
        try {
            String file_name = "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "example" + File.separator + "diamondcircle" + File.separator + "myLogger" + File.separator + "myLogs.log";
            File f = new File(file_name);
            if (!f.exists()) {

                f.createNewFile();

            } else {
                fileHandler = new FileHandler(file_name, true);
                logger.addHandler(fileHandler);
                logger.getLogger(Main.class.getName()).setUseParentHandlers(false);
            }

        } catch (IOException e) {
            logger.severe(e.fillInStackTrace().toString());

        } catch (SecurityException e) {
            logger.severe(e.fillInStackTrace().toString());

        }
    }

    public static void log(Throwable e) {
        logger.severe(e.fillInStackTrace().toString());
        //e.printStackTrace();
    }


    @Override
    public void start(Stage stage) throws IOException {

        try {
            // Parent root = FXMLLoader.load(getClass().getResource("UnosParametara.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UnosParametara.fxml"));
            // Scene scene = new Scene(root);
            Scene scene = new Scene(fxmlLoader.load(), 600, 378);
            stage.setScene(scene);
            stage.setTitle("Unos Parametara");
            stage.setResizable(false);
            stage.setOnCloseRequest(e->
            {
                Platform.exit();
                System.exit(0);
            });
            stage.show();
        } catch (Exception e) {
            log(e);
        }


    }

    public static void main(String[] args) {
        launch();
    }
}
package com.simon.simonsstopwatch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        AtomicBoolean isRunning = new AtomicBoolean(false);

        AtomicInteger startTime = new AtomicInteger();

        BorderPane root = new BorderPane();
        root.setStyle( "-fx-background-color: red" );

        Label stopwatchLabel = new Label("00:00:05");
        stopwatchLabel.setStyle("-fx-font-size: 32;" );
        root.setCenter( stopwatchLabel );

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER );
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        startButton.setStyle( "-fx-font-size: 24;" );
        stopButton.setStyle( "-fx-font-size: 24;" );
        buttons.getChildren().addAll( startButton, stopButton );
        root.setBottom( buttons );

        startButton.setOnAction( e -> {
            isRunning.set(true);
            startTime.set( (int) ( new Date().getTime() / 1000 ) ) ;
            Thread thread = new Thread( () -> {
                while(true) {
                    try {

                        Thread.sleep( 100 );
                        int secondsRunning = (int) ( new Date().getTime() / 1000 - startTime.get() );

                        int hours = secondsRunning / 3600;
                        secondsRunning %= 3600;

                        int minutes = secondsRunning / 60;
                        secondsRunning %= 60;

                        int seconds = secondsRunning;

                        String displayedTime = hours + ":" + minutes + ":" + seconds;
                        Platform.runLater( () -> stopwatchLabel.setText( displayedTime ) );
                        if(!isRunning.get())
                            break;

                    } catch (InterruptedException ex) {}
                }
            } );
            thread.start();
        } );

        stopButton.setOnAction( e -> {
            isRunning.set(false);
        } );

        Scene scene = new Scene( root, 400, 400 );

        stage.setScene( scene );
        stage.show();
    }
}
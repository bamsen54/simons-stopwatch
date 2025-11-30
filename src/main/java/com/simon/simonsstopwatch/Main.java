
// Simon Toivola SY25 Objektorienterad Programmering Uppgift 1 - tidtagarur

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
import java.util.concurrent.atomic.AtomicLong;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        AtomicBoolean isRunning = new AtomicBoolean( false );

        AtomicLong startTime = new AtomicLong();

        BorderPane root = new BorderPane();
        root.setStyle( "-fx-background-color: black" );

        Label stopwatchLabel = new Label( "00:00:00" );
        stopwatchLabel.setStyle("-fx-font-size: 48; -fx-text-fill: orange " );
        root.setCenter( stopwatchLabel );

        HBox buttons = new HBox();
        buttons.setAlignment( Pos.CENTER );
        Button startButton = new Button( "Start" );
        Button stopButton  = new Button( "Stop" );

        startButton.setStyle( "-fx-font-size: 24;" );
        stopButton.setStyle(  "-fx-font-size: 24;" );
        buttons.getChildren().addAll( startButton, stopButton );
        root.setBottom( buttons );

        startButton.setOnAction( e -> {
            isRunning.set( true );
            startTime.set( new Date().getTime() );

            Thread thread = new Thread( () -> {
                while(true) {
                    try {

                        Thread.sleep( 100 );

                        int millisecondsRunnning   = (int) ( new Date().getTime()  - startTime.get() );
                        final String displayedTime = getDisplayedTime( millisecondsRunnning );
                        Platform.runLater( () -> stopwatchLabel.setText( displayedTime ) );

                        if( !isRunning.get() )
                            break;

                    }

                    catch (InterruptedException ex) {}
                }
            } );
            thread.start();
        } );

        stopButton.setOnAction( e -> {
            isRunning.set( false );
        } );

        Scene scene = new Scene( root, 400, 400 );

        stage.setTitle( "Simons tidtagarur" );
        stage.setScene( scene );
        stage.show();
    }

    private static String getDisplayedTime( int millisecondsRunnning ) {
        int tenthsOfASecond = millisecondsRunnning / 100 % 10;

        int secondsRunning = millisecondsRunnning / 1000;
        int hours = secondsRunning / 3600;
        secondsRunning %= 3600;

        int minutes = secondsRunning / 60;
        secondsRunning %= 60;

        int seconds = secondsRunning;

        String hourString   = hours + "";
        String minuteString = minutes >= 10 ? minutes + "" : "0" + minutes;
        String secondString = seconds >= 10 ? seconds + "" : "0" + seconds;

        return hourString + ":" + minuteString + ":" + secondString + ":" + tenthsOfASecond;
    }
}
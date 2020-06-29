package it.polimi.ingsw;

import javafx.application.Application;

import java.io.IOException;

public class GuiRunner {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(App.class);
            }
        }.start();
    }

}

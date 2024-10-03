package it.italiandudes.mctranslationmapper.javafx.controllers;

import it.italiandudes.mctranslationmapper.javafx.Client;
import javafx.fxml.FXML;

public final class ControllerSceneLoading {

    //Initialize
    @FXML
    private void initialize() {
        Client.getStage().setResizable(true);
    }
}
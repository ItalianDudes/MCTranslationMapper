package it.italiandudes.mctranslationmapper.javafx.controllers;

import it.italiandudes.idl.common.Logger;
import it.italiandudes.mctranslationmapper.exceptions.MappingValidationException;
import it.italiandudes.mctranslationmapper.javafx.Client;
import it.italiandudes.mctranslationmapper.javafx.alerts.ErrorAlert;
import it.italiandudes.mctranslationmapper.javafx.components.SceneController;
import it.italiandudes.mctranslationmapper.javafx.scenes.SceneLoading;
import it.italiandudes.mctranslationmapper.javafx.scenes.SceneTranslationMapper;
import it.italiandudes.mctranslationmapper.mapping.MCLangMap;
import it.italiandudes.mctranslationmapper.mapping.MappingManager;
import it.italiandudes.mctranslationmapper.utils.Defs;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class ControllerSceneMainMenu {

    // EDT
    @FXML
    private void newMapping() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create the Mapping");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MCTranslationMapping File", "*." + Defs.Resources.FILE_EXTENSION));
        fileChooser.setInitialDirectory(new File(Defs.JAR_POSITION).getParentFile());
        File fileMapping;
        try {
            fileMapping = fileChooser.showSaveDialog(Client.getStage().getScene().getWindow());
        } catch (IllegalArgumentException e) {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileMapping = fileChooser.showSaveDialog(Client.getStage().getScene().getWindow());
        }
        if (fileMapping == null) return;

        SceneController thisScene = Client.getScene();
        Client.setScene(SceneLoading.getScene());

        File finalFileMapping = fileMapping;
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        try {
                            MCLangMap mapping = MappingManager.createMapping(finalFileMapping);
                            Platform.runLater(() -> Client.setScene(SceneTranslationMapper.getScene(mapping)));
                        } catch (IOException e) {
                            Logger.log(e);
                            Platform.runLater(() -> {
                                new ErrorAlert("ERRORE", "Errore di I/O", "Si e' verificato un errore durante la creazione del mapping.");
                                Client.setScene(thisScene);
                            });
                        }
                        return null;
                    }
                };
            }
        }.start();
    }
    @FXML
    private void openMapping() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the Mapping");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MCTranslationMapping", "*." + Defs.Resources.FILE_EXTENSION));
        fileChooser.setInitialDirectory(new File(Defs.JAR_POSITION).getParentFile());
        File fileMapping;
        try {
            fileMapping = fileChooser.showOpenDialog(Client.getStage().getScene().getWindow());
        } catch (IllegalArgumentException e) {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileMapping = fileChooser.showOpenDialog(Client.getStage().getScene().getWindow());
        }
        if (fileMapping == null) return;

        SceneController thisScene = Client.getScene();
        Client.setScene(SceneLoading.getScene());

        File finalFileMapping = fileMapping;
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        try {
                            MCLangMap mapping = MappingManager.readMapping(finalFileMapping);
                            Platform.runLater(() -> Client.setScene(SceneTranslationMapper.getScene(mapping)));
                        } catch (FileNotFoundException e) {
                            Logger.log(e);
                            Platform.runLater(() -> {
                                new ErrorAlert("ERORE", "Errore di I/O", "File non trovato!");
                                Client.setScene(thisScene);
                            });
                        } catch (MappingValidationException e) {
                            Logger.log(e);
                            Platform.runLater(() -> {
                                new ErrorAlert("ERRORE", "Errore di Parsing", "Si e' verificato un errore durante la validazione del mapping: " + e.getMessage());
                                Client.setScene(thisScene);
                            });
                        }
                        return null;
                    }
                };
            }
        }.start();
    }
}
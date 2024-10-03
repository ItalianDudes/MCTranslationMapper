package it.italiandudes.mctranslationmapper.javafx.controllers;

import it.italiandudes.idl.common.Logger;
import it.italiandudes.mctranslationmapper.mapping.MCLangMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import org.jetbrains.annotations.NotNull;

public final class ControllerSceneTranslationMapper {

    // Attributes
    private MCLangMap mapping = null;
    private volatile boolean configurationComplete = false;

    // Attribute Initializers
    public void configurationComplete(@NotNull final MCLangMap mapping) {
        this.mapping = mapping;
        configurationComplete = true;
    }

    // Graphic Elements

    // Initialize
    @FXML
    private void initialize() {
        // TODO: Implementation of the whole scene
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        //noinspection StatementWithEmptyBody
                        while (!configurationComplete);
                        Logger.log(mapping.toString());
                        return null;
                    }
                };
            }
        }.start();
    }

    // EDT

}

package it.italiandudes.mctranslationmapper.javafx.scenes;

import it.italiandudes.idl.common.Logger;
import it.italiandudes.mctranslationmapper.javafx.components.SceneController;
import it.italiandudes.mctranslationmapper.javafx.controllers.ControllerSceneTranslationMapper;
import it.italiandudes.mctranslationmapper.javafx.utils.JFXDefs;
import it.italiandudes.mctranslationmapper.mapping.MCLangMap;
import it.italiandudes.mctranslationmapper.utils.Defs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class SceneTranslationMapper {

    // Scene Generator
    @NotNull
    public static SceneController getScene(@NotNull final MCLangMap mapping) {
        try {
            FXMLLoader loader = new FXMLLoader(Defs.Resources.get(JFXDefs.Resources.FXML.FXML_TRANSLATION_MAPPER));
            Parent root = loader.load();
            ControllerSceneTranslationMapper controller = loader.getController();
            controller.configurationComplete(mapping);
            return new SceneController(root, controller);
        } catch (IOException e) {
            Logger.log(e);
            System.exit(-1);
            return null;
        }
    }
}

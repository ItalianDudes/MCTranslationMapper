package it.italiandudes.mctranslationmapper.utils;

import it.italiandudes.mctranslationmapper.MCTranslationMapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Defs {

    // App File Name
    public static final String APP_FILENAME = "MCTranslationMapping";

    // Charset
    public static final String DEFAULT_CHARSET = "UTF-8";

    // Jar App Position
    public static final String JAR_POSITION;
    static {
        try {
            JAR_POSITION = new File(MCTranslationMapper.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Resources Location
    public static final class Resources {

        // File Extension
        public static final String FILE_EXTENSION = "mctranslationmapping";

        //Resource Getters
        public static URL get(@NotNull final String resourceConst) {
            return Objects.requireNonNull(MCTranslationMapper.class.getResource(resourceConst));
        }
        public static InputStream getAsStream(@NotNull final String resourceConst) {
            return Objects.requireNonNull(MCTranslationMapper.class.getResourceAsStream(resourceConst));
        }

    }
}

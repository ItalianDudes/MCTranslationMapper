package it.italiandudes.mctranslationmapper.exceptions;

import org.jetbrains.annotations.NotNull;

public final class MappingValidationException extends Exception {
    public MappingValidationException(@NotNull final String message) {
        super(message);
    }
    public MappingValidationException(@NotNull final String message, @NotNull final Throwable cause) {
        super(message, cause);
    }
}

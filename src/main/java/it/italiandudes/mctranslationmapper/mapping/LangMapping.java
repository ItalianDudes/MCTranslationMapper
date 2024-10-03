package it.italiandudes.mctranslationmapper.mapping;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public enum LangMapping {
    EN_US("English (US)"),
    IT_IT("Italiano (Italia)");

    // Attributes
    @NotNull private final String name;

    // Constructor
    LangMapping(@NotNull final String name) {
        this.name = name;
    }

    // Static
    @Nullable
    public static LangMapping getLangMappingByISOName(@NotNull final String isoName) {
        for (LangMapping mapping : LangMapping.values()) {
            if (mapping.getISOName().equals(isoName)) return mapping;
        }
        return null;
    }

    // Methods
    public @NotNull String getName() {
        return name;
    }
    public @NotNull String getISOName() {
        return this.name().toLowerCase();
    }
    @Override
    public String toString() {
        return name;
    }
}

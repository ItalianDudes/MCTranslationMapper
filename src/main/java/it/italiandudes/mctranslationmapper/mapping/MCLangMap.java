package it.italiandudes.mctranslationmapper.mapping;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public final class MCLangMap {

    // Attributes
    @NotNull private final File path;
    @NotNull private final JSONObject mapping;

    // Constructors
    public MCLangMap(@NotNull final File path, @NotNull final JSONObject mapping) {
        this.path = path;
        this.mapping = mapping;
    }

    // Methods
    @NotNull
    public File getPath() {
        return path;
    }
    @NotNull
    public JSONObject getMapping() {
        return mapping;
    }
    public void saveMappingIntoFile() throws IOException {
        MappingManager.writeMapping(this);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MCLangMap)) return false;

        MCLangMap mcLangMap = (MCLangMap) o;
        return getPath().equals(mcLangMap.getPath()) && getMapping().equals(mcLangMap.getMapping());
    }
    @Override
    public int hashCode() {
        int result = getPath().hashCode();
        result = 31 * result + getMapping().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return mapping.toString(2);
    }
}

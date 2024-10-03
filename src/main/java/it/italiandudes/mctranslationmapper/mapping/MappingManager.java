package it.italiandudes.mctranslationmapper.mapping;

import it.italiandudes.mctranslationmapper.exceptions.MappingValidationException;
import it.italiandudes.mctranslationmapper.utils.JSONManager;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressWarnings("unused")
public final class MappingManager {

    // Constants
    public static final String VERSION = "1.0";

    // Methods
    @NotNull
    public static MCLangMap createMapping(@NotNull final File path) throws IOException {
        JSONObject mapping = new JSONObject();
        mapping.put(Keys.KEY_VERSION, VERSION);
        mapping.put(LangMapping.EN_US.getISOName(), new JSONObject());
        MCLangMap map = new MCLangMap(path, mapping);
        writeMapping(map);
        return map;
    }
    @NotNull
    private static MCLangMap validateMapping(@NotNull final MCLangMap mcMapping) throws MappingValidationException {
        JSONObject mapping = mcMapping.getMapping();
        try {
            String version = mapping.getString(Keys.KEY_VERSION);
            if (!version.equals(VERSION)) throw new MappingValidationException("Version mismatch! Current Version: " + VERSION + "; File Version: " + version);
        } catch (JSONException e) {
            throw new MappingValidationException("Version key not found!", e);
        }
        int fallBackEntryCount;
        JSONObject enUS;
        try {
            enUS = mapping.getJSONObject(LangMapping.EN_US.getISOName());
            if (enUS == null) throw new MappingValidationException("English EN_US is null!");
            fallBackEntryCount = enUS.keySet().size();
        } catch (JSONException e) {
            throw new MappingValidationException("English EN_US not found!", e);
        }
        for (String key : enUS.keySet()) {
            if (enUS.isNull(key)) throw new MappingValidationException("The fallback EN_US cannot have null entries!");
        }
        for (String keySet : mapping.keySet()) {
            if (keySet.equals(Keys.KEY_VERSION) || keySet.equals(LangMapping.EN_US.getISOName())) continue;
            LangMapping langMap = LangMapping.getLangMappingByISOName(keySet);
            if (langMap == null) throw new MappingValidationException("The key value \"" + keySet + "\" is not expected!");
            try {
                JSONObject lang = mapping.getJSONObject(langMap.getISOName());
                if (lang == null) throw new MappingValidationException("The key \"" + keySet + "\" doesn't exist!");
                if (lang.keySet().size() != fallBackEntryCount) throw new MappingValidationException("Fallback EN_US and \"" + keySet + "\" doesn't have the same entry count: fallback " + fallBackEntryCount + ", " + keySet + " " + lang.keySet().size());
                if (!lang.keySet().equals(enUS.keySet())) throw new MappingValidationException("Fallback EN_US and \"" + keySet + "\" doesn't have the same keys");
            } catch (JSONException e) {
                throw new MappingValidationException("The key \"" + keySet + "\" is expected to be a JSONObject, but it isn't!", e);
            }
        }
        return mcMapping;
    }
    public static void writeMapping(@NotNull final MCLangMap mapping) throws IOException {
        JSONManager.writeJSON(mapping.getMapping(), mapping.getPath());
    }
    public static void exportMapping(@NotNull final MCLangMap mcMapping, @NotNull final File exportDirectory) throws IOException {
        if (!exportDirectory.exists() || !exportDirectory.isDirectory()) return;
        String dirPath = exportDirectory.getAbsolutePath();
        if (!dirPath.endsWith(File.separator)) dirPath += File.separator;
        JSONObject mapping = mcMapping.getMapping();
        JSONObject enUS = mapping.getJSONObject(LangMapping.EN_US.getISOName());
        for (LangMapping lang : LangMapping.values()) {
            if (mapping.has(lang.getISOName())) {
                JSONObject translation = mapping.getJSONObject(lang.getISOName());
                JSONObject writableJSON = new JSONObject();
                for (String key : translation.keySet()) {
                    String value = null;
                    if (!translation.isNull(key)) value = translation.getString(key);
                    if (value == null) value = enUS.getString(key);
                    writableJSON.put(key, value);
                }

                File destination = new File(dirPath + lang.getISOName() + ".json");
                JSONManager.writeJSON(writableJSON, destination);
            }
        }
    }
    @NotNull
    public static MCLangMap readMapping(@NotNull final File mappingFile) throws FileNotFoundException, MappingValidationException {
        return validateMapping(new MCLangMap(mappingFile, JSONManager.readJSON(mappingFile)));
    }


    // Keys
    public static final class Keys {
        public static final String KEY_VERSION = "version";
    }

}

package cz.speedy.musicbot.language;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private final ResourceBundle language;

    public LanguageManager(Locale locale) {
        this.language = ResourceBundle.getBundle("langs.language", locale);
    }

    public ResourceBundle getLanguage() {
        return language;
    }

    public String getString(String key, Object... args) {
        return MessageFormat.format(language.getString(key), args);
    }
}

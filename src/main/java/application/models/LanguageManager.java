package application.models;

public class LanguageManager {
    private static LanguageManager instance;
    private String selectedLanguage;

    private LanguageManager() {
        // Initialize the selected language
        selectedLanguage = "sq_AL";
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String language) {
        selectedLanguage = language;
    }
}

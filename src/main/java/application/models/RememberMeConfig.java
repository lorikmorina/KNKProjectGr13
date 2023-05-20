package application.models;

import application.controllers.logInController;

import java.io.*;
import java.util.Properties;

public class RememberMeConfig {
      private static final String CONFIG_FILE = "config.properties";
    public static void clearSavedCredentials() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            boolean deleted = configFile.delete();
            if (deleted) {
                System.out.println("Configuration file deleted successfully.");
            } else {
                System.out.println("Failed to delete configuration file.");
            }
        }
    }
}

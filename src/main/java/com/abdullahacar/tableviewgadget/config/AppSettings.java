package com.abdullahacar.tableviewgadget.config;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AppSettings {

    public static ResourceBundle RESOURCE_BUNDLE = getDefaultResourceBundle();

    /**
     * PRODUCTION
     */
//    public static final String SERVER_ADDRESS = "http://api.cdek.com.tr:";
//    public static final int SERVER_PORT = 8090;
//    public static final String PATH = "/CdekAPI";

    /**
     * LOCAL
     */
    public static final String SERVER_ADDRESS = "http://localhost:";
    public static final int SERVER_PORT = 8080;
    public static final String PATH = "/";

    public static String URL() {
        return SERVER_ADDRESS + SERVER_PORT + PATH;
    }

    public static ResourceBundle getDefaultResourceBundle() {
        return ResourceBundle.getBundle("com/Translations/lang", Locale.getDefault());
    }



}

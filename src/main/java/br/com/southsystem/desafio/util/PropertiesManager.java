package br.com.southsystem.desafio.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesManager {

    private static PropertiesManager instance;
    private static final Object lock = new Object();

    private static Properties param = new Properties();
    private static final Logger logger = Logger.getLogger(PropertiesManager.class.getName());

    private PropertiesManager(){
    }

    public static PropertiesManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertiesManager();
                instance.loadData();
            }
        }
        return instance;
    }


    private void loadData() {
        try {
            param.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Properties file not found", e);
        }
    }


    public String getProperty(String key) {
        return param.getProperty(key);
    }
}

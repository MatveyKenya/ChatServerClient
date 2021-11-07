package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsReader {
    final private Properties properties;

    public static void main(String[] args) {
        SettingsReader sr = new SettingsReader("settingsServer.txt");
        String port = sr.getSetting("port");
        System.out.println(port);
    }

    public SettingsReader(String nameFile){
        properties = new Properties();
        File file = new File(nameFile);
        try{
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSetting(String keyWord){
        return properties.getProperty(keyWord);
    }
}

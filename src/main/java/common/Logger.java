package common;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {

    static private Logger instance;
    static FileWriter writer = null;
    static private String nameFileLog = "log.txt";
    final private static Date dateNow = new Date();
    final private static SimpleDateFormat formatForDateNow = new SimpleDateFormat("(yyyy.MM.dd- hh:mm:ss.SSS) ");

    private Logger(){
        try {
            writer = new FileWriter(nameFileLog, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    static public Logger getInstance(String nameFile) {
        if (instance != null){
            System.out.println("Current name of file Log is - " + nameFileLog + " - can not be changed");
            return null;
        }
        nameFileLog = nameFile;
        return getInstance();
    }

    public synchronized void log(String msg) {
        System.out.println(msg);
        try{
            writer.write(msg + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logTime(String msg){
        log(timeNow() + msg);
    }

    public static String timeNow(){
        return formatForDateNow.format(dateNow);
    }
}

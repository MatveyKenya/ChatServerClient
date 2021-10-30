package chat_server;

import java.io.FileWriter;
import java.io.IOException;

public final class Logger {

    static private Logger instance;
    static FileWriter writer = null;

    private Logger(){
        try {
            writer = new FileWriter("log.txt", true);
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

    public synchronized void log(String msg) {
        System.out.println(msg);
        try{
            writer.write(msg + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

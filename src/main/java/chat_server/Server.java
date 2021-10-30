package chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    final int PORT;

    // todo Определить параметры подключения в конструкторе Читать из файла
    public Server() {
        PORT = 23334;
    }

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {
        Logger logger = Logger.getInstance();
        //ExecutorService poolExecutor = Executors.newCachedThreadPool();
        try (ServerSocket servSocket = new ServerSocket(PORT)){
            //  Ждем подключения клиента и отправляем в новый поток для дальнейшей работы
            while (true){
                logger.log("Ждем клиента...");
                try (Socket socket = servSocket.accept()){
                    logger.log("отправляем в отдельный поток клиента - " + socket);
                    //poolExecutor.execute(new ClientDialog(socket));
                    new Thread(new ClientDialog(socket)).start();
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

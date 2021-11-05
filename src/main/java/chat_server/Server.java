package chat_server;

import common.Logger;
import common.SettingsReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server{
    final private int PORT;
    final private String NAME_LOG = "log_Server.txt";
    final private String FILE_SETTINGS = "settingsServer.txt";
    final private ArrayList<ClientDialog> clients = new ArrayList<>();// список клиентов, которые будут подключаться к серверу
    final Logger logger;

    public Server() {
        PORT = Integer.parseInt(new SettingsReader(FILE_SETTINGS).getSetting("port"));
        logger = Logger.getInstance(NAME_LOG);
    }

    public void start() {
        Socket socket;
        try (ServerSocket servSocket = new ServerSocket(PORT)){
            logger.logTime("Сервер запущен");
            while (true){
                logger.logTime("Ждем клиента...");
                try {
                    socket = servSocket.accept();//  Ждем подключения клиента и отправляем в новый поток для дальнейшей работы
                    logger.logTime("Отправляем в отдельный поток клиента - " + socket);
                    ClientDialog client = new ClientDialog(socket, this);
                    clients.add(client);
                    new Thread(client).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // отправляем сообщение всем клиентам
    public void sendMessageToAllClients(String msg) {
        logger.logTime(msg);
        for (ClientDialog o : clients) {
            o.sendMsg(Logger.timeNow() + msg);
        }
    }

    // удаляем клиента из коллекции при выходе из чата
    public void removeClient(ClientDialog client) {
        clients.remove(client);
    }

}

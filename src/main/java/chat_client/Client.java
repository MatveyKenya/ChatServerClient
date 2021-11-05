package chat_client;

import common.Logger;
import common.SettingsReader;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    final private String HOSTNAME;
    final private int PORT;
    final private String CLIENT_NAME;
    final private String FILE_SETTINGS = "settingsClient.txt";
    final private Logger logger;

    public Client(String clientName) {
        CLIENT_NAME = clientName;
        logger = Logger.getInstance("log_" + CLIENT_NAME + ".txt");
        SettingsReader sr = new SettingsReader(FILE_SETTINGS);// читаем из файла
        HOSTNAME = sr.getSetting("host");
        PORT = Integer.parseInt(sr.getSetting("port"));
    }

    public void start() {
        try (// Определяем сокет сервера
             Socket socket = new Socket(HOSTNAME, PORT);
             // Получаем входящий и исходящий потоки информации
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {
            String msg;
            logger.logTime("*** Начало сеанса " + CLIENT_NAME + " ***");
            while (true) {
                while (in.ready()){ // печатаем все что прилетело от сервера если есть
                    logger.log(in.readLine());
                }
                System.out.println("Введите сообщение или (ENTER - обновить сообщения, //+ENTER - выйти): ");
                msg = scanner.nextLine();
                if ("".equals(msg)){
                    continue;
                }
                if ("//".equals(msg)) {
                    logger.logTime("*** конец сеанса " + CLIENT_NAME + " ***");
                    out.println(msg);
                    break;
                }
                out.println("[" + CLIENT_NAME + "]: " + msg);

                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package chat_client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String HOSTNAME;
    private final int PORT;

    public Client() {
        HOSTNAME = "127.0.0.1";
        PORT = 23334;
    }

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        try (// Определяем сокет сервера
             Socket socket = new Socket(HOSTNAME, PORT);
             // Получаем входящий и исходящий потоки информации
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {
            String msg;
            while (true) {
                System.out.println("Введите сообщение или /exit для завершения: ");
                msg = scanner.nextLine();
                out.println(msg);
                if ("/exit".equals(msg)) {
                    break;
                }
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

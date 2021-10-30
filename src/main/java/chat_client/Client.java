package chat_client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    //private final String HOSTNAME;
    //private final String PORT;

    public Client() {
    }

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            // Определяем сокет сервера
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 23334);
            //  подключаемся к серверу
            socketChannel.connect(socketAddress);
            // Получаем входящий и исходящий потоки информации
            try (Scanner scanner = new Scanner(System.in)) {
                //  Определяем буфер для получения данных
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                String msg;
                while (true) {
                    System.out.println("Введите сообщение (или 'end' для выхода):");
                    msg = scanner.nextLine();
                    socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                    if ("end".equals(msg)) {
                        break;
                    }
                    int bytesCount = socketChannel.read(inputBuffer);
                    System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                    inputBuffer.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

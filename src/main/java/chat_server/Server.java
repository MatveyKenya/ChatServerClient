package chat_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    final String HOSTNAME;
    final int PORT;

    // todo Определить параметры подключения в конструкторе Читать из файла
    public Server() {
        HOSTNAME = "127.0.0.1";
        PORT = 23334;
    }

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {
        Logger logger = Logger.getInstance();
        try (final ServerSocketChannel serverChannel = ServerSocketChannel.open()){
            //  Занимаем порт, определяя серверный сокет
            serverChannel.bind(new InetSocketAddress(HOSTNAME, PORT));
            ExecutorService poolExecutor = Executors.newCachedThreadPool();
            while (true) {
                // Ждем подключения клиента и получаем потоки для дальнейшей работы
                logger.log("привет");
                System.out.println("ждем нового клиента...");
                try (SocketChannel socketChannel = serverChannel.accept()) {
                    System.out.println("отправляем в пул клиента - " + socketChannel.socket());
                    poolExecutor.execute(new ClientDialog(socketChannel));
                }
            }
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

}

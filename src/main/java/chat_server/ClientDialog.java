package chat_server;

import common.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientDialog implements Runnable {
    final private Socket socket;
    final private Server server;
    final private Logger logger = Logger.getInstance();
    private PrintWriter out;
    private BufferedReader in;
    // количество клиента в чате, статичное поле
    private static int clients_count = 0;

    public ClientDialog(Socket socket, Server server) {
        clients_count++;
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            this.out = out;
            String msg;
            server.sendMessageToAllClients("Новый участник вошёл в чат! Всего в чате = " + clients_count);
            while (!socket.isClosed()) {
                if (in.ready()) {
                    msg = in.readLine();
                    server.sendMessageToAllClients(msg);
                }
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void sendMsg(String msg){
        out.println(msg);
    }

    public void close() {
        // удаляем клиента из списка
        server.removeClient(this);
        clients_count--;
        server.sendMessageToAllClients("Участник покинул чат. Всего в чате = " + clients_count);
    }
}

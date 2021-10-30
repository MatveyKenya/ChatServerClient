package chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientDialog implements Runnable {
    final Socket socket;
    Logger logger = Logger.getInstance();

    public ClientDialog(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while (true) {
                if ((line = in.readLine()) != null){
                    // Выход если от клиента получили end
                    if (line.equals("/exit")) {
                        break;
                    }
                    logger.log(line);
                    //вычисляем и пишем ответ
                    out.println("Эхо от сервера - " + line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

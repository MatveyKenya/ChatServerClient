package chat_server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ClientDialog implements Runnable {
    final SocketChannel socketChannel;

    public ClientDialog(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try{
            // Определяем буфер для получения данных
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            while (socketChannel.isConnected()) {
                // читаем данные из канала в буфер
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println("сервер прочитал");
                // если из потока читать нельзя, перестаем работать с этим клиентом
                if (bytesCount == -1) {
                    break;
                }
                //  получаем переданную от клиента строку в нужной кодировке и очищаем буфер
                final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                inputBuffer.clear();
                if ("end".equals(msg)) {
                    return;
                }
                //отправляем сообщение обратно
                socketChannel.write(ByteBuffer.wrap(("Server ЭХО - " + msg).getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

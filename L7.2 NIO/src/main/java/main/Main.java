package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;


public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static final int PORT = 5050;

    /**
     * Сервер принимает соединения не блокирующим способом. Когда соединение
     * установлено, создается сокет, который регистрируется с селектором для
     * чтения/записи. Чтение/запись выполняется над этим сокетом, когда селектор
     * разблокируется.
     */
    public static void main(String[] args) throws IOException {
        // Канал будет читать данные в ByteBuffer, посылаемые методом PrintWriter.println().
        //  Декодирование этого потока байт требует кодовой страницы для кодировки по умолчанию.
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer buffer = ByteBuffer.allocate(64);
        SocketChannel ch = null;
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector sel = Selector.open();
        try {
            ssc.configureBlocking(false);

            // Локальныйы адрес, на котором он будет слушать соединения
            // Примечание: Socket.getChannel() возвращает null, если с ним не
            // ассоциирован канал, как показано ниже.
            // т.е выражение (ssc.socket().getChannel() != null) справедливо
            ssc.socket().bind(new InetSocketAddress(PORT));

            // Канал заинтересован в событиях OP_ACCEPT
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
            logger.info("Server started");
            logger.info("Server on port: " + PORT);
            while (true) {
                sel.select();
                Iterator it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey skey = (SelectionKey) it.next();
                    it.remove();
                    if (skey.isAcceptable()) {
                        ch = ssc.accept();
                        logger.info("Accepted connection from:" + ch.socket());
                        ch.configureBlocking(false);
                        ch.register(sel, SelectionKey.OP_READ);
                    }
                    else {
                        // Обратите внимание, что не выполняется проверка, если
                        // в канал
                        // можно писать или читать - для упрощения.
                        ch = (SocketChannel) skey.channel();
                        ch.read(buffer);
                        CharBuffer cb = cs.decode((ByteBuffer) buffer.flip());
                        String response = cb.toString();
                        if (response.indexOf("Bye") != -1) {
                            logger.info("Closing connection from:" + ch.socket());
                            ch.close();
                        } else {
                            logger.info("Echoing : " + response);
                            ch.write((ByteBuffer) buffer.rewind());
                        }
                        buffer.clear();
                    }
                }
            }
        } finally {
            if (ch != null) {ch.close();}
            ssc.close();
            sel.close();
        }
    }
}

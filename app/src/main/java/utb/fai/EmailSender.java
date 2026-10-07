package utb.fai;

import java.net.*;

import java.io.*;

public class EmailSender {
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public EmailSender(String host, int port) throws UnknownHostException, IOException {
       this.socket = new Socket(host,port);
       this.in = socket.getInputStream();
       this.out = socket.getOutputStream();
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException{
byte[] buffer = new byte[256];
        int len;
        String message;

        try {
            message = "EHLO localhost\r\n";
            out.write(message.getBytes());
            out.flush();
            Thread.sleep(100);
            if (in.available() > 0) {
                len = in.read(buffer, 0, 256);
                System.out.write(buffer, 0, len);
            }

            message = "MAIL FROM:<" + from + ">\r\n";
            out.write(message.getBytes());
            out.flush();
            Thread.sleep(100);
            if (in.available() > 0) {
                len = in.read(buffer, 0, 256);
                System.out.write(buffer, 0, len);
            }

            // RCPT TO
            message = "RCPT TO:<" + to + ">\r\n";
            out.write(message.getBytes());
            out.flush();
            Thread.sleep(100);
            if (in.available() > 0) {
                len = in.read(buffer, 0, 256);
                System.out.write(buffer, 0, len);
            }

            message = "DATA\r\n";
            out.write(message.getBytes());
            out.flush();
            Thread.sleep(100);
            if (in.available() > 0) {
                len = in.read(buffer, 0, 256);
                System.out.write(buffer, 0, len);
            }

            message = "Subject: " + subject + "\r\n\r\n" + text + "\r\n.\r\n";
            out.write(message.getBytes());
            out.flush();
            Thread.sleep(100);
            if (in.available() > 0) {
                len = in.read(buffer, 0, 256);
                System.out.write(buffer, 0, len);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        try {
            if (out != null && in != null) {
                String message = "QUIT\r\n";
                out.write(message.getBytes());
                out.flush();
                
                Thread.sleep(100);
                byte[] buffer = new byte[256];
                if (in.available() > 0) {
                    int len = in.read(buffer, 0, 256);
                    System.out.write(buffer, 0, len);
                }
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

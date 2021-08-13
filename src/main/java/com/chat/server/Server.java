package main.java.com.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class Server {

    private int DEFAULT_BUFFER_SIZE = 1024;

    private Socket socket;
    public PrintWriter writer;
    public BufferedReader reader;

    public int writeCount;
    public int writeSize;

    public int readCount;
    public int readSize;

    public Server() {};

    public Server(String ip, int port) throws IOException {
        connect(ip, port);
    }

    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    
    private String encodeHeading(String msg){
        return Integer.toString(msg.length()) + "\n\n" + msg;
    }

    public void send(String msg){
        writer.write(encodeHeading(msg));
        writer.flush();
    }

    private int findHeading(String msg){
        int i = msg.indexOf("\n\n");
        return i > -1 ? i : 0;
    }
    private int findHeading(char[] msg){
        boolean last = false;
        int i = 0;
        for (int x=0; x<msg.length; x++){
            if (msg[x] == '\n'){
                if (last) {
                    i = x-1;
                    break;
                } else {
                    last = true;
                }
            } else {
                last = false;
            }
        }
        return i;
    }

    private String getMessage(String msg, int i){
        return msg.length() > i+2 ? msg.substring(i+2) : "";
    }
    private char[] getMessage(char[] msg, int i){
        return Arrays.copyOfRange(msg, i+2, msg.length);
    }

    private int getSize(String msg, int i){
        return Integer.parseInt(msg.substring(0, i));
    }
    private int getSize(char[] msg, int i){
        return Integer.parseInt(new String(msg, 0, i));
    }

    public String receive(){
        double timer = System.currentTimeMillis();
        try {
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int r=0, h, local_r;

            do {
                local_r = reader.read(buf, r, DEFAULT_BUFFER_SIZE-r);
                if(local_r != -1){
                    r += local_r;
                }
                h = findHeading(buf);
                sleep(1);
            } while (h==0);

            int msgSize = getSize(buf, h);
            char[] out = new char[msgSize];
            r -= h+2;
            System.arraycopy(buf, h+2, out, 0, r);

            do {
                local_r = reader.read(out, r, msgSize-r);
                if(local_r != -1){
                    r += local_r;
                }
            } while (r < msgSize);

            return new String(out);

        } catch (IOException | InterruptedException e) {
            return "";
        } catch (NumberFormatException e) {
            System.out.println("Wrong message format");
            return "";
        } catch (TimeoutException e) {
            System.out.println("Timed out");
            try {
                disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return "";
        }
    }

    public String request(String msg) {
        send(msg);
        return receive();
    }

    public void disconnect() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}

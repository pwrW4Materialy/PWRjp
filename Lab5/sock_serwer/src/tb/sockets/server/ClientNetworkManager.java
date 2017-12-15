package tb.sockets.server;

import java.io.*;
import java.net.Socket;

public class ClientNetworkManager {

    private PrintWriter printWriter;
    private BufferedReader receiveRead;
    private Socket sock;

    private int status = 0;

    public ClientNetworkManager() {
    }

    public boolean connect(String ConnectIP, int port) {
        try {
            sock = new Socket(ConnectIP, port);

            OutputStream outputStream = sock.getOutputStream();
            this.printWriter = new PrintWriter(outputStream, true);

            InputStream inputStream = sock.getInputStream();
            this.receiveRead = new BufferedReader(new InputStreamReader(inputStream));

            status = 1;
        }
        catch (Exception e) {
            System.out.println("Can't connect to server, " + e.toString());
            status = 0;
            return false;
        }

        return true;
    }

    public String receive() {
        try {
            String receiveMessage;
            if ((receiveMessage = this.receiveRead.readLine()) != null) {
                return receiveMessage;
            }
        }
        catch (Exception e) {
            System.out.println("receive() failed to read buffer, " + e.toString());
        }

        return "ServerError";
    }

    public void send(String input) {
        try {
            this.printWriter.println(input);
            this.printWriter.flush();
        }
        catch (Exception e) {
            System.out.println("failed to send msg to server, " + e.toString());
        }
    }

    public void disconnect(){
        try {
            if (sock != null){
                sock.close();
                status = 0;
            }
        }
        catch (Exception e){
            System.out.println("unable to disconnect/no active connection, " + e.toString());
        }
    }

    public int getStatus() {
        return status;
    }
}


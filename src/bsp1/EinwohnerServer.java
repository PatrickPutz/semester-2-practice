package bsp1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EinwohnerServer {

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(1111)){

            while(true){
                System.out.println("Waiting for client...");
                Socket client = serverSocket.accept();

                EinwohnerHandler eh = new EinwohnerHandler(client);

                Thread thread = new Thread(eh);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

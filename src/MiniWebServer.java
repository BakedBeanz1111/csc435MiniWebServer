import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//From the sample provided code, ListenWorker is Worker
class Worker extends Thread {

    Socket sock;

    Worker(Socket s) {

        sock = s;
    }

    public void run() {

        PrintStream out = null;
        BufferedReader in = null;

        try {

            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());

            try {

                System.out.println("Sending HTML response now...");
            }

        } catch (IOException x) {

            System.out.println("ERROR:" + x.getMessage());
        }
    }



}

public class MiniWebServer {

    public static void main(String args[]) throws IOException {

        int qLength = 6;
        int primaryPort = 2540;

        Socket sock;

        System.out.println("Amad's Mini Web Server is listening on port(" + primaryPort + ")...");

        ServerSocket serverSocket = new ServerSocket(primaryPort, qLength);

        while(true) {

            sock = serverSocket.accept();
            new Worker(sock).start();
        }
    }
}
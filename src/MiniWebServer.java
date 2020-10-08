import java.io.*;
import java.net.*;

/*

    As per the requirements of the checklist, here are the answers to the requested questions!

    Q1) Explained how MIME-types are used to tell the browser what data is coming.

    A1) The following answer was reached from reading Stack Overflow/Wikipedia/FireFox Documentation.

        A MIME type is a label used to identify a type of data. If the browser see's an ID like "application/mp3",
        it knows to the data incoming is an MP3 and it needs to launch some application to handle it, ust like it would
        a pdf or some other specialized file format. I thought a clever explanation I saw was like referring to MIME-type
        as like a file extention for the internet browser. It see's an ID and then knows what it needs to do to handle
        that properly.

    Q2) Explained how you would return the contents of requested files (web pages) of type HTML (text/html)

    A2) Parse the HTML request request to string? This is a very vague question. The contents of a web file are
        contents of an HTML file full of tags and other stuff like that. To return an HTML file's contents, wouldn't
        you just parse it to a string and then you can return it.

    Q3) Explained how you would return the contents of requested files (web pages) of type TEXT (text/plain)

    A3) Parse a text file to binary? This is also a very vague question. The content of a text file can be any ASCII
        characters. To return the contents of that file, you could parse it to a binary bitstream and send it somewhere.
 */


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
			
			String socketData;
			
			String name;
			int num1;
			int num2;
			
			while(true) {
				
				socketData = in.readLine();
				
				if(socketData.contains("GET")) {
					
					String[] parameters = socketData.split("&");
					String[] values = new String[3];
					
					int i = 0;
					
					for(String a : parameters) {
						
						String value = a.substring(a.indexOf("=")+1);
						
						//System.out.println(value);
						
						values[i] = value;
						//System.out.println(i + " " + values[i]);
						i++;
						
						if(i == 3)
							i = 0;
													
					}
					
					name = values[0];
					num1 = Integer.parseInt(values[1]);
					
					String[] garbage = values[2].split(" ");
					num2 = Integer.parseInt(garbage[0]);
					
					int sum = num1 + num2;
					
					System.out.println("You entered name: " + name);
					System.out.println("The sum of " + num1 + " + " + num2 + " is: " + sum);
			
					
				}
				System.out.flush();
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
        System.out.println("Point Firefox browser to http://localhost:2540/abc.\n");

        ServerSocket serverSocket = new ServerSocket(primaryPort, qLength);

        while(true) {

            sock = serverSocket.accept();
            new Worker(sock).start();
        }
    }
}
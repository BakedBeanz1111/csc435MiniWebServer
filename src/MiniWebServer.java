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
//Our worker class extends the java thread class to expand functionality
class Worker extends Thread {

    Socket sock;

	//Default constructor for creating new worker object
    Worker(Socket s) {

        sock = s;
    }

	//This function executes upon each new creation of worker
    public void run() {

        PrintStream out = null;
        BufferedReader in = null;

        try {

			//input from the socket connection
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			//Text output to send to client
            out = new PrintStream(sock.getOutputStream());
			
			//All of the data that comes through the socket connection
			String socketData;
			
			//Declaring variables to be used for later
			String name;
			int num1;
			int num2;
			
			while(true) {
				
				socketData = in.readLine();
				
				//If the line that contains "GET" exists on the in response, then do some parsing!
				if(socketData.contains("GET")) {
					
					//The formatted data for the URL is like Name=Something&num1=Somethingelse&num2=someotherthing
					
					//All the URL parameters are split by the & symbol, so I just split that line into multiple strings to parse and manipulate the variables
					String[] parameters = socketData.split("&");
					String[] values = new String[3]; //Since there are 3 "&" symbols in the URL, we have 3 parameters to parse out of the URL
					
					int i = 0;
					
					//The following code was inspired by what I found on StackOverflow
					//https://stackoverflow.com/questions/19431710/splitting-an-array-of-strings-in-java-using-split
					//I was googling advice on how to split and trim strings
					for(String a : parameters) {
						
						String value = a.substring(a.indexOf("=")+1);
						
						values[i] = value;
						i++;
						
						//i = 0 -> Name
						//i = 1 -> num1
						//i = 2 -> num2
						
						//If i becomes 3, reset back to 0
						if(i == 3)
							i = 0;
													
					}
					
					name = values[0];
					num1 = Integer.parseInt(values[1]);
					
					//Had to trim the trailing value of num2 from the space onward
					String[] garbage = values[2].split(" ");
					num2 = Integer.parseInt(garbage[0]);
					
					int sum = num1 + num2;
					
					System.out.println("You entered name: " + name);
					System.out.println("The sum of " + num1 + " + " + num2 + " is: " + sum);
					
					//I tried using what I found in WebResponse.java to try and figure out how I can modify the HTML to get a response on the screen but was unsuccessful
					//String HTMLResponse = "<html> <h1> You Entered name: " + name + " Your sum is: " + sum +  "</h1> <p><p> <hr> <p>";
					//out.println(HTMLResponse);
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
        System.out.println("Point Firefox browser to the WebAdd.html page!");

        ServerSocket serverSocket = new ServerSocket(primaryPort, qLength);

        while(true) {

            sock = serverSocket.accept();
            new Worker(sock).start();
        }
    }
}
package clientsideprj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// This class is made to be the thread that communicates with the server.

public class ConcurrentClient extends Thread {
    private double elaspedTime;
    private double totalTime;
    private long startTime;
    private String serverCommand;
    private String myHost;
    
    // Port is used as constants
    public static final int portNumber = 3030;
    
    public ConcurrentClient(String command, String myHost) {
        this.serverCommand = command;
        this.elaspedTime = 0;
        this.myHost = myHost;
    }
    
    public double getElaspedTime() {
        return this.elaspedTime;
    }
    
    public double getTotalTime() {
	return this.totalTime;
    }
	
    public String getServerCommand() {
        return this.serverCommand;
    }
    
    public void run() {
        try {
        String str;
	    String line = null;
	    System.out.println("My host = "+this.myHost+" portNumber ="+this.portNumber);
            // Do the stuffs
            Socket socket = new Socket(this.myHost, this.portNumber);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    System.out.println("End of intialization");
	    totalTime = 0;
	    startTimer();// Start the timer
            output.println(this.serverCommand);
	    
	    while((line = input.readLine()) != null) {
		endTimer();
		this.totalTime += this.elaspedTime;
            	System.out.println(line);
		startTimer();
	    }
	    endTimer();
	    this.totalTime += this.elaspedTime;
	    System.out.println("----------------------------------------------------------------------");
            socket.close();
        }
        catch (UnknownHostException a1) {
            a1.printStackTrace();
        }
        catch (IOException a2) {
            a2.printStackTrace();
        }
        catch (Exception a3) {
            System.out.printf("Exception thrown%n");
        }
    }
    
    private void startTimer() {
        this.startTime = System.currentTimeMillis();
    }
    
    private void endTimer() {
        long currentTime = System.currentTimeMillis();
        this.elaspedTime = currentTime - this.startTime; //Calculating the elapsed time.
    }
}
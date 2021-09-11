package serversideprj;

import java.io.*;
import java.net.*;

//import java.lang.management.*;

public class serverside {

          
    
    public static void main(String[] args) {
        //Declarations  of server socket 
        
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        String command;
        System.out.println("I am running here. My pulse is good today ");
        
        //Server socket opened at port 3030
        try {
           serverSocket = new ServerSocket(3030);
           System.out.println("Socket created now .");
        }
        catch (IOException e) {
            System.out.println(e);
         
        }
        try {
        
           boolean run = true;
           while (run) {
               //Open the Input Output stream
               clientSocket = serverSocket.accept();
               System.out.println("Client is Accepted");
               PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
               BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

               //listen for client commands from the client server
               
               while(!clientSocket.isClosed() && ((command = in.readLine()) != null)) {
                   System.out.println(command);
                   
                   if (command.equals("exit")) {
                      System.out.println("Exiting.............");
                      run = false;
                      break;
                   }
                   
                   else {
                      try {
                         Runtime runtime = Runtime.getRuntime();
                         Process pr = runtime.exec(command);
        
                         BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        
                         String line = null;
                         while ((line = input.readLine()) != null) {
                            System.out.println(line);
                            out.println(line);
                         }
                         
                         out.close();
                         System.out.println("Completed the process");         
                         pr.waitFor();
                      } 
                   
                      catch (Exception a1) {
                         System.out.println("Runtime Catch Exception");
                         System.out.println(a1.toString());
                      }                  
                   }
               }
           }

        }
        
   
        
   catch (IOException a2) {
	   System.out.println(a2);
   }
        
    // End of the main method.
  
}
}


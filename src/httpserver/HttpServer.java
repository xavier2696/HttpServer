/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author xavier2696
 */
public class HttpServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException { 
        ServerSocket server = new ServerSocket(80); 
        System.out.println("Listening for connection on port 80...");
        Thread pool[] = new Thread[20]; 
        while (true) { 
            Socket socket = server.accept();
            ServerRequest cliente = new ServerRequest(socket);
            boolean lleno = true;
            for(int i=0;i<pool.length; i++){
                if(pool[i] == null){
                    pool[i] = new Thread(cliente);
                    pool[i].start();
                    lleno = false;
                    break;
                }
            }
            if(lleno){
                Thread hilo = new Thread(cliente);
                hilo.start();
            }
        } 
    }

    
}

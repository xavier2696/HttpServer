/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package httpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;


/**
 *
 * @author xavier2696
 */
public class ServerRequest implements Runnable{

    boolean[] isRun; 
    String CRLF = "\r\n";
    Socket serverSocket; 
    String resultLine;			
    PrintStream output;
    String text;
    String requestLine;	
    DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date date = new Date();
    String fileRoute;
    
    public ServerRequest(Socket skt) throws IOException{
        serverSocket = skt;
	text = "";
	output = new PrintStream(serverSocket.getOutputStream());
        fileRoute="";
    }
    
    public void run(){
        try{
            InputStream byteStream = serverSocket.getInputStream();
            InputStreamReader charStream = new  InputStreamReader(byteStream);
            BufferedReader input = new BufferedReader(charStream);
            requestLine = input.readLine();
            String clientIP = serverSocket.getInetAddress().toString().substring(1); 
            int clientPort = serverSocket.getPort();
            String clientInf = new String("[Connection]" + CRLF+ clientIP + ": " + clientPort + CRLF + requestLine + CRLF);
            ProcessRequest();
            String inf = new String(clientInf + resultLine);
            text=text+inf;

            
        }catch (Exception e){
        }
    }
    
    private static String getFileExtension(String fileName) {
		
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
                return "";
        }
    }
    
    private void ProcessRequest() throws Exception {
		String headLine = null;
		String statusLine = null;
		String closeLine = "Connection: close" + CRLF; 
		String timeLine = null;
		String ServerLine = "Server: " + InetAddress.getLocalHost().getHostName() + CRLF;
		String lengthLine = null;
		String typeLine = null;
		String entityBody = null;
		StringTokenizer token = new StringTokenizer(requestLine);
		String method = token.nextToken();
		if( method.equals("GET") || method.equals("HEAD")){
			
			fileRoute += token.nextToken().substring(1);		
			
			try{
				FileInputStream fileStream = new FileInputStream(fileRoute);
                                
				String type = getFileExtension(fileRoute);
                                if(token.nextToken().equals("HTTP/1.1")){
                                    statusLine = "HTTP/1.1 200 OK" + CRLF;
                                }else{
                                    statusLine = "HTTP/1.0 200 OK" + CRLF;
                                }
				timeLine = "Date: " + fullDateFormat.format(date) + CRLF;
				lengthLine = "Content-Length: " + (new Integer(fileStream.available())).toString() + CRLF;
				typeLine = getContentType(type) + CRLF;
				headLine = statusLine + closeLine + timeLine + ServerLine + lengthLine + typeLine + CRLF;
				output.print(headLine);				
				
				if( !(method.equals("HEAD")) ){
					byte[] buffer = new byte[1024];
					int bytes = 0;	
					while ((bytes = fileStream.read(buffer)) != -1) {
						output.write(buffer, 0, bytes);
					}
				}
				
				fileStream.close();
				resultLine = "Result: succeed!" + CRLF + CRLF;
			}
			catch(IOException e){
                            if(token.nextToken().equals("HTTP/1.1")){
                                    statusLine = "HTTP/1.1 404 not found" + CRLF;
                                }else{
                                    statusLine = "HTTP/1.0 404 not found" + CRLF;
                                }
				output.println(statusLine);
                                output.close();
			}
		}else if(method.equals("POST")){
                    System.out.println("Post succesful");
                    fileRoute += "post.html";		
			
			try{
				FileInputStream fileStream = new FileInputStream(fileRoute);
				String type = getFileExtension(fileRoute);
                                if(token.nextToken().equals("HTTP/1.1")){
                                    statusLine = "HTTP/1.1 200 OK" + CRLF;
                                }else{
                                    statusLine = "HTTP/1.0 200 OK" + CRLF;
                                }
				timeLine = "Date: " + fullDateFormat.format(date) + CRLF;
				lengthLine = "Content-Length: " + (new Integer(fileStream.available())).toString() + CRLF;
				typeLine = getContentType(type) + CRLF;
				headLine = statusLine + closeLine + timeLine + ServerLine + lengthLine + typeLine + CRLF;
				output.print(headLine);				
				
				if( !(method.equals("HEAD")) ){
					byte[] buffer = new byte[1024];
					int bytes = 0;	
					while ((bytes = fileStream.read(buffer)) != -1) {
						output.write(buffer, 0, bytes);
					}
				}
				
				fileStream.close();
				resultLine = "Result: succeed!" + CRLF + CRLF;
			}
			catch(IOException e){
                            if(token.nextToken().equals("HTTP/1.1")){
                                    statusLine = "HTTP/1.1 404 not found" + CRLF;
                                }else{
                                    statusLine = "HTTP/1.0 404 not found" + CRLF;
                                }
				output.println(statusLine);
                                output.close();
			}
                    
                }else if(method.equals("PUT")){
                    System.out.println("PUT successful");
                    fileRoute += "put.html";		
			
			try{
				FileInputStream fileStream = new FileInputStream(fileRoute);
				String type = getFileExtension(fileRoute);
                                if(token.nextToken().equals("HTTP/1.1")){
                                    statusLine = "HTTP/1.1 200 OK" + CRLF;
                                }else{
                                    statusLine = "HTTP/1.0 200 OK" + CRLF;
                                }
				timeLine = "Date: " + fullDateFormat.format(date) + CRLF;
				lengthLine = "Content-Length: " + (new Integer(fileStream.available())).toString() + CRLF;
				typeLine = getContentType(type) + CRLF;
				headLine = statusLine + closeLine + timeLine + ServerLine + lengthLine + typeLine + CRLF;
				output.print(headLine);				
				
				if( !(method.equals("HEAD")) ){
					byte[] buffer = new byte[1024];
					int bytes = 0;	
					while ((bytes = fileStream.read(buffer)) != -1) {
						output.write(buffer, 0, bytes);
					}
				}
				
				fileStream.close();
				resultLine = "Result: succeed!" + CRLF + CRLF;
			}
			catch(IOException e){
                            if(token.nextToken().equals("HTTP/1.1")){
                                    statusLine = "HTTP/1.1 404 not found" + CRLF;
                                }else{
                                    statusLine = "HTTP/1.0 404 not found" + CRLF;
                                }
				output.println(statusLine);
                                output.close();
			}
                 
                }
		
	}
    
    private  String getContentType(String suffix) {
		String contentType = null;
		switch(suffix){
		
			case "jpe":
			case "jpeg":
			case "jpg":
				contentType = "Content-Type: image/jpeg";
				break;
			case "txt":
			case "stm":
			case "htm":
			case "html":
				contentType = "Content-Type: text/html";
				break;
			case "css":
				contentType = "Content-Type: text/css";
				break;
			case "gif":
				contentType = "Content-Type: image/gif";
				break;
			case "png":
				contentType = "Content-Type: image/png";
				break;
			case "pdf":
				contentType = "Content-Type: application/pdf";
				break;
			case "doc":
			case "docx":
				contentType = "Content-Type: application/msword";
				break;
			case "mp3":
				contentType = "Content-Type: audio/mp3";
				break;
			default:
				resultLine = "Formato no reconocido...";
				break;
		}
		return contentType;
	}
    
}

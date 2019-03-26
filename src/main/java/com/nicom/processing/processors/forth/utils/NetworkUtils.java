package com.nicom.processing.processors.forth.utils;

import java.net.*;
import java.io.*;

import java.net.UnknownHostException;
import java.net.InetAddress;

public class NetworkUtils {

    private static URL aURL;
    private static Socket aSocket;
    
    /**
     * 
     * @param urlStr ="http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING"
     * "http://www.oracle.com/"
     * 
     * @throws java.net.MalformedURLException
     */
    public static void setNetworkUrl(String urlStr) throws MalformedURLException{ // 
        aURL = new URL(urlStr);
    }
    
    public static void showUrlDetails(){
        
        System.out.println("protocol = " + aURL.getProtocol());
        System.out.println("authority = " + aURL.getAuthority());
        System.out.println("host = " + aURL.getHost());
        System.out.println("port = " + aURL.getPort());
        System.out.println("path = " + aURL.getPath());
        System.out.println("query = " + aURL.getQuery());
        System.out.println("filename = " + aURL.getFile());
        System.out.println("ref = " + aURL.getRef());
    }
    
    public static String urlReader( URL url) throws IOException{
        StringBuilder stb=new StringBuilder();
        
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));
        
        String inputLine;
        int count=0;
        while ((inputLine = in.readLine()) != null){
            if(count>0){
                stb.append("\n");
            }
            stb.append(inputLine);
            count++;
        }
        System.out.println(stb);        
        return stb.toString();
    }
    
    public static void setSocket(String hostName,int portNumber) throws IOException{
        aSocket= new Socket(hostName, portNumber);
    }
    
    public void sendToSocket(String outputMessage) throws IOException{
         PrintWriter out =new PrintWriter(aSocket.getOutputStream(), true);
         out.println(outputMessage);
    }
    
    public static String receiveFromSocket() throws IOException{
        StringBuilder stb=new StringBuilder();
        String fromServer;
        BufferedReader in = new BufferedReader( new InputStreamReader(aSocket.getInputStream()));
        
        int count=0;
        while ((fromServer = in.readLine()) != null) {
            if(count>0){
                stb.append("\n");
            }
            stb.append(fromServer);            
        }
        
        return stb.toString();
    }
    
    public static void closeSocket() throws IOException{
        aSocket.close();
    }
    
    public static boolean pingEmulator(String ipAddress) throws IOException{
        String result;
        InetAddress inet = InetAddress.getByName(ipAddress);
        System.out.println("Sending Ping Request to " + ipAddress);
        boolean isReachable = inet.isReachable(50000);
        System.out.println("Is host reachable? " + isReachable);
        return isReachable;
    }
    
    public static InetAddress[] getInetAddresses(String urlStr) throws UnknownHostException {
    	InetAddress[] inetAddresses = InetAddress.getAllByName(urlStr);
        for(InetAddress inetAddress : inetAddresses){
            System.out.println("HostAddress: "+inetAddress.getHostAddress());
            System.out.println("HostName: "+inetAddress.getHostName());
            System.out.println("CanonicalHostName :"+inetAddress.getCanonicalHostName());
            System.out.println("==========================");
        }
        
        return inetAddresses;    	
    }
    
    public static String getHostAddress(String urlStr) throws UnknownHostException {
    	InetAddress[] inetAddresses = InetAddress.getAllByName(urlStr);
    	InetAddress inetAddress=inetAddresses[0];
    	String hostAddress=inetAddress.getHostAddress();
    	System.out.println("HostAddress: "+hostAddress);
    	return hostAddress;
    }

    public static String getHostName(String urlStr) throws UnknownHostException {
    	InetAddress[] inetAddresses = InetAddress.getAllByName(urlStr);
    	InetAddress inetAddress=inetAddresses[0];
    	String hostName=inetAddress.getHostName();
    	System.out.println("HostName: "+hostName);
    	return hostName;
    }

}

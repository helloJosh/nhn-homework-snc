package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) {
        runProgram(args);
    }
    public static void runProgram(String[] args) {
        Options options = new Options();
        Option listenOption = new Option("l", false, "Run Server listen mode");
        options.addOption(listenOption);

        CommandLineParser parser = new DefaultParser();
        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption('l')){
                System.out.println("Running in Server listen mode");
                int port = Integer.parseInt(cmd.getArgs()[0]);
                runServer(port);

            } else{
                String[] remainArgs = cmd.getArgs();
                if(remainArgs.length == 2){
                    String host = remainArgs[0];
                    int port = Integer.parseInt(remainArgs[1]);
                    runClient(host, port);
                    System.out.println("Connecting to host: "+ host + ",port: "+ port);
                } else{
                    System.err.println("args error");
                    System.exit(1);
                }
            }
        } catch (ParseException | NumberFormatException e){
            System.err.println("connection error");
        } 
    }
    public static void runServer(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            Socket socket = serverSocket.accept();
            SendThread sendThread = new SendThread(socket);
            sendThread.start();
            
            ReadThread readThread = new ReadThread(socket);
            readThread.start();

            while(!Thread.currentThread().isInterrupted()){
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void runClient(String s, int port){
        try(Socket socket = new Socket(s, port)){

            SendThread sendThread = new SendThread(socket);
            sendThread.start();
            ReadThread readThread = new ReadThread(socket);
            readThread.start();

            while(!Thread.currentThread().isInterrupted()){
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
}
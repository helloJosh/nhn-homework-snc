package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread implements Runnable{
    
    private Socket socket;
    private Thread thread;
    public ReadThread(Socket socket){
        this.socket = socket;
        this.thread = new Thread(this);
    }
    @Override
    public void run(){
        try{
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(!(line = reader.readLine()).equals("exit")){
                socket.getOutputStream().write(line.getBytes());
                socket.getOutputStream().write("\n".getBytes());
            }
        } catch (IOException e) {
            System.err.println("Socket Error "+e.getMessage());
        }
    }
    public void start(){
        thread.start();
    }

}

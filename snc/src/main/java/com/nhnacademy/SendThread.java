package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SendThread implements Runnable{
    private Socket socket;
    private Thread thread;
    public SendThread(Socket socket){
        this.socket = socket;
        this.thread = new Thread(this);
    }
    @Override
    public void run(){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while(!(line = input.readLine()).equals("exit")){
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Socket Error "+e.getMessage());
        }
    }
    public void start(){
        thread.start();
    }

}

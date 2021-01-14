package com.hit.server;
import com.hit.service.Controller;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server implements Runnable, PropertyChangeListener {

    ServerSocket server;
    Socket socket;
    Executor executor ;
    Controller controller;
    int port;
    static boolean on = true;
    public Server(int port){
        this.port=port;
        run();
    }

    @Override
    public void run() {
        try {
            server =new ServerSocket(port);//create new server
            executor = Executors.newFixedThreadPool (5);//Manages the request queue
            controller=new Controller();//create a new controller
            while (on) {
                socket = server.accept();//wait of new request
                executor.execute(new HandleRequest<String>(socket,controller));//create a new request for the server
            }
        }
        catch (Exception e){e.printStackTrace();}
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    @Override
    //change the status of server ,if get start -power the server,if stop shutdown the server
    public void propertyChange(PropertyChangeEvent evt) {
        String action =(String) evt.getNewValue();//get the string action

        switch(action) {
            case "start"://if start the server is up
                if(on == false) {
                    on = true;
                    new Thread(this).start();
                    break;
                }
                else
                    System.out.println("Server is already ON\n");
                break;
            case "stop"://if stop the server is stopped
                if(on == false)
                    System.out.println("Server is already OFF\n");
                else {
                    try {
                        on=false;
                        server.close();
                    }
                    catch(IOException e){e.printStackTrace();}
                }
                break;
            default:
                System.out.println("Not a valid command");
                break;
        }
    }
}



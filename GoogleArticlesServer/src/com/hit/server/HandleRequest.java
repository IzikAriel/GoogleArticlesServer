package com.hit.server;
import com.hit.dm.DataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.service.Controller;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest<T> implements Runnable {

    Socket socket;
    Controller controller;
    Response<DataModel> response;

    public HandleRequest(Socket socket,Controller controller){

        this.socket = socket;
        this.controller=controller;
        response=new Response<DataModel>("Failed",null);
    }

    @Override
    public void run(){
        try
        {
                Scanner reader = new Scanner(new InputStreamReader (socket.getInputStream()));
                PrintWriter writer = new PrintWriter (new OutputStreamWriter (socket.getOutputStream()));
                Gson gson = new Gson();
                String req = reader.nextLine();//read request from client
                Type ref = new TypeToken<Request<DataModel>>() {}.getType();
                Request<DataModel> request= gson.fromJson(req, ref);//covert the string to request
                String action= request.getAction();//take the action
                switch (action)
                {
                    case "search"://case of choose a regular search
                                    response.setData(controller.search(request.getBody()));
                                    if(response.getData().getFoundArticles().size()>0)//check if the response founded results
                                        response.setMessage("Success");
                                    else
                                        response.setMessage("Failed");
                                    break;
                    case "load"://case that the client want to load a article from DB
                                    response.setData(controller.load(request.getBody()));
                                    if(response.getData().getTarget().size()>0)//check if the response get the article
                                        response.setMessage("Success");
                                    else
                                        response.setMessage("Failed");
                                    break;

                    case "searchByMostMentions"://case of client choose search by most mentions
                        response.setData(controller.searchByMostMentions(request.getBody()));
                        if(response.getData().getFoundArticles().size()>0)//check if the response have get articles in results
                            response.setMessage("Success");
                        else
                            response.setMessage("Failed");
                        break;
                    case "searchByMostPopular" ://case of client choose search by most popular
                        response.setData(controller.searchByMostPopular(request.getBody()));
                        if(response.getData().getFoundArticles().size()>0)//check if the response have get articles in results
                            response.setMessage("Success");
                        else
                            response.setMessage("Failed");
                        break;

                }
                String mes = gson.toJson(response);//put the response in string
                writer.println(mes);//send the response to client
                writer.flush();//clear the buffer
     }
        catch (IOException e){e.printStackTrace();}
        finally {
            try {this.socket.close();}
            catch (IOException e){e.printStackTrace();}
        }
    }
}
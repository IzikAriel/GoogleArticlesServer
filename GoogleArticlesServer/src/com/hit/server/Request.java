package com.hit.server;

public class Request<T> {
    String action;
    T body ;

    public Request(String action, T body){
        this.action = action;
        this.body = body;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getBody(){return body;}

    public void setBody(T body){this.body = body;}
}

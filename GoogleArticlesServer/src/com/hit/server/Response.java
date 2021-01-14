package com.hit.server;
public class Response<T>
{
    String message;
    T data ;

    public Response(String messeage, T data) {
        this.message = messeage;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package com.hit.dao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface Idao <T>{

    ArrayList<T> get() throws FileNotFoundException;
    void add(T article) throws IOException;
    boolean remove(T article);
    void updatePopular(T article) throws IOException;
    T loadPage(String name) throws FileNotFoundException;
}

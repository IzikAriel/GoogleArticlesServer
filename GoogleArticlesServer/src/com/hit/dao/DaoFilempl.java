package com.hit.dao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.Article;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DaoFilempl implements Idao<Article>{
    Gson gson;

    public DaoFilempl(){gson = new Gson();}

    private ArrayList<String> getNames(){
        //the function get all the articles names and put them in arrayList of strings
        ArrayList<String> articles = new ArrayList();
        String path = "DB//ArticleFileNames.json";//get the file of articles names
        try (Reader reader = new FileReader(path)){
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            articles = gson.fromJson(reader, listType);//put all names in arrayList of strings
        }
        catch (IOException e){
            System.out.println("Failed to retrieve Data, Please verify File availability ");
            e.printStackTrace();
            return null;
        }
        return articles;
    }

    @Override
    public ArrayList<Article> get(){
        //the function return arrayList of all articles

        ArrayList<String> articlesNames = getNames();//get all the names of articles
        ArrayList<Article> articles = new ArrayList();
        try {
            for (String file : articlesNames) {
                Article temp = gson.fromJson(new FileReader("DB//Articles//" + file + ".json"), Article.class);
                articles.add(temp);
            }
        }
        catch (FileNotFoundException e){System.out.println("file not found ");}
        return articles;
    }

    @Override
    //function add a new article to data base
    public void add(Article article){
        try {
            String temp = gson.toJson(article);
            FileWriter fileWriter = new FileWriter("DB//Articles//" + article.gettitle() + ".json");
            fileWriter.write(temp);
            fileWriter.close();
            if (!fileExists(article.gettitle())) {
                updateArticleNames(article.gettitle());
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }

    @Override
    //the function remove article from data base
    public boolean remove(Article article) {
        updateArticleNames(article.gettitle());
        Path path = Paths.get("DB//Articles//" + article.gettitle() + ".json");
        File cfile = new File(path.toString());
        if (!cfile.exists()){
            System.out.println("file doesn't exist");
            return false;
        }
        if (!cfile.delete()){
            System.out.println("Failed to delete file");
            return false;
        }
        return true;
    }

    @Override
    //update the number of viewer
    public void updatePopular(Article article){

        article.setPopular(article.getPopular() + 1);
        add(article);
    }

    @Override
    //the function load article from data base
    public Article loadPage(String name){
        try {
            Article target = gson.fromJson(new FileReader("DB//Articles//" + name + ".json"), Article.class);
            updatePopular(target);
            return target;
        }
        catch (FileNotFoundException e){return null;}
    }

    //the function check if the file is Exists
    private boolean fileExists(String name){
        ArrayList<String> names = getNames();
        if (names.contains(name)) return true;
        return false;
    }

    //the function update the file with all the articles names
    private void updateArticleNames(String name){
        ArrayList<String> names = getNames();
        if (names.contains(name))
            names.remove(name);
        else names.add(name);
        String temp = gson.toJson(names);
        String path = "DB//ArticleFileNames.json";
        try (Writer writer = new FileWriter(path)){
            writer.write(gson.toJson(names));
        }
        catch (IOException e){e.printStackTrace();}
    }
}



package com.hit.service;
import com.hit.algorithm.IAlgoSearch;
import com.hit.algorithm.KMPStringMatching;
import com.hit.algorithm.NaiveStringMatching;
import com.hit.algorithm.RabinKarpStringMatching;
import com.hit.dao.DaoFilempl;
import com.hit.dao.Idao;
import com.hit.dm.Article;
import com.hit.dm.DataModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ArticleService {

    IAlgoSearch mySearch;
    Idao idao;
    private ArrayList<Article> articles;

    public ArticleService() {
        this.idao = new DaoFilempl();
    }

    public IAlgoSearch getMySearch() {
        return mySearch;
    }

    public void setMySearch(IAlgoSearch mySearch) {
        this.mySearch = mySearch;
    }

    public Idao getIdao() {
        return idao;
    }

    public void setIdao(Idao idao) {
        this.idao = idao;
    }


    public void whichAlgo(String algo)
    {
        switch (algo)//get the algorithm ,and create new search
        {
            case "kmp":mySearch=new KMPStringMatching();
                break;
            case "naive":mySearch=new NaiveStringMatching();
                break;
            case "rabinkarp": mySearch=new RabinKarpStringMatching();
                break;
        }
    }

    //get Details to search and do a regular search ,return all the articles with this search
    public DataModel search(DataModel item)
    {
            whichAlgo( item.getChosendAlgorithm());
            articles= get();

        for(Article article:articles)
        {
            ArrayList<Integer> founded = mySearch.search(article.getContent(),item.getKey());
            if(founded!=null && founded.size() > 0)
                item.getFoundArticles().add(article.gettitle());
        }
        return item;

    }


    //get Details to search , and search by most mention and return all the articles with this search
    public DataModel searchByMostMentions(DataModel item) {
        whichAlgo( item.getChosendAlgorithm());
        articles = get();
        ArrayList<String > ar = new ArrayList<String>();
        Map<Integer,String > map = new TreeMap<Integer,String>();
        int num =0 ;
        for(Article article:articles) {

            num = mySearch.countOfReference(article.getContent(), item.getKey());
            if (num != -1) {
                map.put(num, article.gettitle());
            }
        }
            for (Integer key : map.keySet()){ar.add(map.get(key));}
            item.setFoundArticles(ar);
            return item;
    }

    //get Details to search , and search by most popular and return all the articles with this search
    public DataModel searchByMostPopular(DataModel item) {
            whichAlgo( item.getChosendAlgorithm());
            articles = get();
            ArrayList<String > ar = new ArrayList<>();
            Map<Integer,String > map = new TreeMap<Integer,String>();
            for(Article article:articles)
            {
                if(mySearch.search(article.getContent(),item.getKey())!=null)
                   map.put(article.getPopular(), article.gettitle());
            }
            for (Integer key : map.keySet()){ar.add(map.get(key));}
            item.setFoundArticles(ar);
            return item;
    }

    //load a page that client ask
    public DataModel load(DataModel item) {
        try{articles=idao.get();}
        catch (FileNotFoundException e){}
                try {
                item.setTarget(CovertToMap(loadPage(item.getKey())));
                return item;
            }
                catch (Exception e){}
        return null;
    }

    //the function return all the articles from DB
    public ArrayList<Article> get() {

        try{return idao.get();}
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    //the function add a new article to DB
    public void add(Article article) {

        try{idao.add(article);}
        catch(IOException e){e.printStackTrace();}
    }

    //the function remove article from DB
    public void remove(Article article)
    {
        idao.remove(article);
    }

    //the function load article from DB
    public Article loadPage(String name) {

        try{return (Article) idao.loadPage(name);}
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

//the function covert article to hash map
    public Map<String,String> CovertToMap(Article atricle)
    {
        Map<String, String> target  = new HashMap<>();
        target.put("title",atricle.gettitle());
        target.put("content",atricle.getContent());
        target.put("author",atricle.getAuthor());
        target.put("publish",atricle.getPublish());
        target.put("subject",atricle.getSubject());
        target.put("popular",String.valueOf(atricle.getPopular()));
        return target;
    }
}

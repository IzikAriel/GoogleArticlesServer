package com.hit.dm;
import java.util.ArrayList;
import java.util.Map;
public class DataModel{

    String key;
    ArrayList<String> foundArticles;
    Map<String,String> target;
    String chosenAlgorithm;

    public DataModel(String key,ArrayList<String> foundArticles, Map<String,String> target, String chosenAlgorithm){
        this.key = key;
        this.foundArticles = foundArticles;
        this.target =target;
        this.chosenAlgorithm = chosenAlgorithm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getFoundArticles() {
        return foundArticles;
    }

    public void setFoundArticles(ArrayList<String> foundArticles) {
        this.foundArticles = foundArticles;
    }

    public Map<String, String> getTarget() {
        return target;
    }

    public void setTarget(Map<String,String> target) {
        this.target = target;
    }

    public String getChosendAlgorithm() {
        return chosenAlgorithm;
    }

    public void setChosendAlgorithm(String chosenAlgorithm) {
        this.chosenAlgorithm = chosenAlgorithm;
    }
}

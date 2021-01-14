package com.hit.dm;

public class Article {

    private String title ,author,subject,publish,content;
    private int popular;
    public Article(String title, String author, String subject, String publish, String content){
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publish = publish;
        this.content = content;
    }
    public int getPopular(){return popular;}//get count of viewers

    public void setPopular(int popular) {
        this.popular = popular;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void settitle(String name) {
        this.title = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String gettitle() {
        return title;
    }

    public String getAuthor(){return author;}

    public String getSubject() {return subject;}

    public String getPublish(){return publish;}
}

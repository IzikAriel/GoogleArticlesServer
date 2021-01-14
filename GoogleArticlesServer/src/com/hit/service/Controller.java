package com.hit.service;
import com.hit.dm.DataModel;

public class Controller {
    private ArticleService service;

    public Controller()
    {
        service=new ArticleService();
    }

    public DataModel search(DataModel item){return service.search(item);}

    public DataModel searchByMostMentions(DataModel item){return service.searchByMostMentions(item);}

    public DataModel searchByMostPopular(DataModel item){return service.searchByMostPopular(item);}

    public DataModel load(DataModel item){ return service.load(item);}
}

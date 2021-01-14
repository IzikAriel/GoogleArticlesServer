package test.com.hit.service;
import com.hit.dm.Article;
import com.hit.service.ArticleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DaoFilemplTest {

    ArticleService ser;
    @Before
    public void setUp() {
        ser = new ArticleService();
    }

    @Test
    public void get() {
        try {
            ArrayList<Article>  ar = ser.getIdao().get();
            Assert.assertEquals("Harry Potter", ar.get(0).gettitle());
            }
        catch (Exception e){}
    }

    @Test
    public void add() {
        try {
            ArrayList<Article> ar = ser.getIdao().get();
            Article temp = ar.get(0);
            temp.setPopular(20);
            ser.getIdao().add(temp);
            ar = ser.getIdao().get();
            Assert.assertEquals(20, ar.get(0).getPopular());
        }
        catch (IOException e){}
    }

    @Test
    public void updatePopular() {
        try {
            ArrayList<Article> ar = ser.getIdao().get();
            Article temp = ar.get(0);
            temp.setPopular(20);
            ser.getIdao().updatePopular(temp);
            ar = ser.getIdao().get();
            Assert.assertEquals(21, ar.get(0).getPopular());
        }
        catch (IOException e) {}
    }

    @Test
    public void loadPage(){
        try {
            Article ar = (Article) ser.getIdao().loadPage("Harry Potter");
            Assert.assertNotNull(ar);
        }
        catch (FileNotFoundException e) {}
    }
}
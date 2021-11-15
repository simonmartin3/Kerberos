package JDBCAccess;

import DTO.Article;
import MySqlAccess.MysqlAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.beans.Beans;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBeanBdArticle {

    @BeforeAll
    static void setup()
    {
        MysqlAccess.initialize("jdbc:mysql://localhost:3306/BD_GFB","eric","eric");
    }
    @Test
    void testGetListArticle()
    {
        try {
            BeanBdArticle beanBdArticle = (BeanBdArticle) Beans.instantiate(null, "JDBCAccess.BeanBdArticle");
            List<Article> lArticle = new ArrayList<Article>();
            lArticle.add(new Article(1, "Chou de Bruxelles",50, true));
            lArticle.add(new Article(2, "Chou-fleur",75, false));
            lArticle.add(new Article(3, "Chou frisé",48, true));
            lArticle.add(new Article(4, "Chou moellier",35, true));
            lArticle.add(new Article(5, "Chou de Milan",150, false));

            List<Article> lArticleFromDb = beanBdArticle.getListArticle(MysqlAccess.getConnection());
            assertEquals(lArticle, lArticleFromDb);
        }
        catch(SQLException se)
        {
            fail("SQLException : " + se);
        }
        catch(ClassNotFoundException cnfe)
        {
            fail("ClassNotFoundException : " + cnfe);
        }
        catch(IOException ioe)
        {
            fail("IOException : " + ioe);
        }
    }

    @Test
    void testUpdateArticle()
    {
        try {
            Connection con =MysqlAccess.getConnection();
            con.setAutoCommit(false);
            BeanBdArticle beanBdArticle = (BeanBdArticle) Beans.instantiate(null, "JDBCAccess.BeanBdArticle");
            List<Article> lArticle = new ArrayList<Article>();
            lArticle.add(new Article(1, "Chou de Bruxelles",49, true));
            lArticle.add(new Article(2, "Chou-fleur",75, false));
            lArticle.add(new Article(3, "Chou frisé",48, true));
            lArticle.add(new Article(4, "Chou moellier",35, true));
            lArticle.add(new Article(5, "Chou de Milan",150, false));

            assertTrue(beanBdArticle.updateQuantiteArticle(MysqlAccess.getConnection(), new Article(1, "Chou de Bruxelles", 1, true)));

            List<Article> lArticleFromDb = beanBdArticle.getListArticle(con);
            assertEquals(lArticle, lArticleFromDb);
        }
        catch(SQLException se)
        {
            fail("SQLException : " + se);
        }
        catch(ClassNotFoundException cnfe)
        {
            fail("ClassNotFoundException : " + cnfe);
        }
        catch(IOException ioe)
        {
            fail("IOException : " + ioe);
        }
    }
}

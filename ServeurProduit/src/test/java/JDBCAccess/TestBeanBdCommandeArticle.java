package JDBCAccess;

import DTO.Article;
import MySqlAccess.MysqlAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.beans.Beans;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestBeanBdCommandeArticle {
    @BeforeAll
    static void setup()
    {
        MysqlAccess.initialize("jdbc:mysql://localhost:3306/BD_GFB","eric","eric");
    }

    @Test
    void testinsertCommandeArticles()
    {
        try{
            Connection con = MysqlAccess.getConnection();
            BeanBdCommandeArticle beanBdCommandeArticle = (BeanBdCommandeArticle) Beans.instantiate(null,"JDBCAccess.BeanBdCommandeArticle");
            con.setAutoCommit(false);
            int idCommande = 2;
            List<Article> lArticleToInsert = new ArrayList<Article>();
            lArticleToInsert.add(new Article(1, "Chou de Bruxelles",2, true));
            lArticleToInsert.add(new Article(2, "Chou-fleur",5, false));
            lArticleToInsert.add(new Article(3, "Chou frisé",10, true));
            lArticleToInsert.add(new Article(4, "Chou moellier",4, true));
            lArticleToInsert.add(new Article(5, "Chou de Milan",3, false));
            assertTrue(beanBdCommandeArticle.insertCommandeArticles(con,lArticleToInsert,idCommande));

        }
        catch(SQLException se)
        {
            fail("SQLException : " + se);
        }
        catch(ClassNotFoundException cnfe)
        {
            fail("ClassNotFoundException : " + cnfe);
        }
        catch(Exception e)
        {
            fail("Exception : " + e);
        }
    }

    @Test
    void testinsertCommandeArticle()
    {
        try{
            Connection con = MysqlAccess.getConnection();
            BeanBdCommandeArticle beanBdCommandeArticle = (BeanBdCommandeArticle) Beans.instantiate(null,"JDBCAccess.BeanBdCommandeArticle");
            con.setAutoCommit(false);
            int idCommande = 2;
            Article articleToInsert = new Article(1,"Choux de Bruxelles", 2,true);
            assertTrue(beanBdCommandeArticle.insertCommandeArticle(con,articleToInsert,idCommande));

        }
        catch(SQLException se)
        {
            fail("SQLException : " + se);
        }
        catch(ClassNotFoundException cnfe)
        {
            fail("ClassNotFoundException : " + cnfe);
        }
        catch(Exception e)
        {
            fail("Exception : " + e);
        }
    }
}

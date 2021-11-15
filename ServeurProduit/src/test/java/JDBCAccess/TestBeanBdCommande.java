package JDBCAccess;

import MySqlAccess.MysqlAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.beans.Beans;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestBeanBdCommande {

    @BeforeAll
    static void setup()
    {
        MysqlAccess.initialize("jdbc:mysql://localhost:3306/BD_GFB","eric","eric");
    }

    @Test
    void testInsertCommande()
    {
        try{
            Connection con = MysqlAccess.getConnection();
            con.setAutoCommit(false);
            BeanBdCommande beanBdCommande = (BeanBdCommande) Beans.instantiate(null,"JDBCAccess.BeanBdCommande");
            assertTrue(beanBdCommande.insertCommande(con, "MonClient"));

        }
        catch(SQLException se)
        {
            fail("SQLException : " + se);
        }
        catch(ClassNotFoundException cnfe)
        {
            fail("ClassNotFOundException : " + cnfe);
        }
        catch(Exception e)
        {
            fail("Exception : "+e);
        }
    }
}

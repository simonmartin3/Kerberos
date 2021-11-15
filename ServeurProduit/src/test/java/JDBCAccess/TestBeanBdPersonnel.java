package JDBCAccess;

import MySqlAccess.MysqlAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.beans.Beans;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBeanBdPersonnel {

    @BeforeAll
    static void setup()
    {
        MysqlAccess.initialize("jdbc:mysql://localhost:3306/BD_GFB","eric","eric");
    }

    @Test
    void testGetEmailPersonnelOnCategorie()
    {
        try{
            Connection con = MysqlAccess.getConnection();
            BeanBdPersonnel beanBdPersonnel = (BeanBdPersonnel) Beans.instantiate(null,"JDBCAccess.BeanBdPersonnel");
            con.setAutoCommit(false);
            int idCommande = 2;
            List<String> lEmail =new ArrayList<>();
            lEmail.add("clovis.premier@hotmail.com");
            lEmail.add("louis.quatorze@hotmail.com");
            lEmail.add("napoleon.bonaparte@hotmail.com");
            lEmail.add("henri.legrand@hotmail.com");

           List<String> lEmailCategorie1 = beanBdPersonnel.getEmailPersonnelOnCategorie(MysqlAccess.getConnection(),1);
           List<String> lEmailCategorie2 = beanBdPersonnel.getEmailPersonnelOnCategorie(MysqlAccess.getConnection(),2);
           List<String> lEmailCategorie3 = beanBdPersonnel.getEmailPersonnelOnCategorie(MysqlAccess.getConnection(),3);
           List<String> lEmailCategorie4 = beanBdPersonnel.getEmailPersonnelOnCategorie(MysqlAccess.getConnection(),4);

           assertEquals(1,lEmailCategorie1.size());
           assertEquals(1,lEmailCategorie2.size());
           assertEquals(1,lEmailCategorie3.size());
           assertEquals(1,lEmailCategorie4.size());
            assertEquals(lEmail.get(0), lEmailCategorie1.get(0));
            assertEquals(lEmail.get(1), lEmailCategorie2.get(0));
            assertEquals(lEmail.get(2),lEmailCategorie3.get(0));
            assertEquals(lEmail.get(3), lEmailCategorie4.get(0));
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

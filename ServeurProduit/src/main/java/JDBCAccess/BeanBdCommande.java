package JDBCAccess;

import DTO.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BeanBdCommande {
    public BeanBdCommande()
    {}


    public synchronized boolean insertCommande(Connection con, String nomClient) throws SQLException
    {
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO COMMANDE (NomClient) VALUES (?)");
        preparedStatement.setString(1,nomClient);
        return preparedStatement.executeUpdate()==1;
    }

    public synchronized int getLastId(Connection con) throws SQLException
    {
        PreparedStatement preparedStatement = con.prepareStatement("SELECT ID FROM COMMANDE ORDER BY ID DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt("ID");
        }
        return -1;

    }
}

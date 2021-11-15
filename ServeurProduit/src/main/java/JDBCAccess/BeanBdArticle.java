package JDBCAccess;

import DTO.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanBdArticle {

    public BeanBdArticle(){}
    public synchronized List<Article> getListArticle(Connection con) throws SQLException, ClassNotFoundException
    {
        List<Article> lArticle = new ArrayList<Article>();
        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM ARTICLE");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next())
        {
            lArticle.add(new Article(resultSet.getInt("id"), resultSet.getString("nom"),
                    resultSet.getInt("quantite"),resultSet.getInt("bio")==1));
        }
        return lArticle;
    }

    public synchronized boolean updateQuantiteArticle(Connection con, Article article) throws SQLException, ClassNotFoundException
    {
        PreparedStatement preparedStatement = con.prepareStatement(
                "UPDATE ARTICLE " +
                " SET" +
                " QUANTITE = QUANTITE - ?" +
                " WHERE ID = ?");
        preparedStatement.setInt(1, article.getQuantite());
        preparedStatement.setInt(2,article.getId());
        return preparedStatement.executeUpdate() == 1;
    }


    public synchronized boolean updateQuantiteArticles(Connection con, List<Article> lArticle) throws SQLException, ClassNotFoundException
    {
        boolean returnVal = true;

        for(Article article : lArticle)
        {
            returnVal=updateQuantiteArticle(con, article);
            if(!returnVal)
                break;
        }

        return returnVal;
    }

}

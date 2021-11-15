package JDBCAccess;

import DTO.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BeanBdCommandeArticle {
    public BeanBdCommandeArticle()
    {}

    public synchronized boolean insertCommandeArticles(Connection con, List<Article> lArticle, int idCommande) throws SQLException
    {
        boolean returnVal = true;

        for(Article article : lArticle)
        {
            returnVal=insertCommandeArticle(con, article, idCommande);
            if(!returnVal)
                break;
        }

        return returnVal;
    }

    public synchronized boolean insertCommandeArticle(Connection con, Article article, int idCommande) throws SQLException
    {
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO COMMANDE_ARTICLE(idCommande, idArticle, quantite) VALUES (? , ?, ?)");
        preparedStatement.setInt(1, idCommande);
        preparedStatement.setInt(2, article.getId());
        preparedStatement.setInt(3, article.getQuantite());
        return preparedStatement.executeUpdate()==1;
    }
}

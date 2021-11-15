package JDBCAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanBdPersonnel {
    public BeanBdPersonnel()
    {}

    public synchronized List<String> getEmailPersonnelOnCategorie(Connection con, int categorie) throws SQLException {
        List<String> lEmail = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT EMAIL FROM PERSONNEL WHERE CATEGORIE = ?");
        ps.setInt(1,categorie);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            lEmail.add(rs.getString("email"));
        }
        return lEmail;
    }
}

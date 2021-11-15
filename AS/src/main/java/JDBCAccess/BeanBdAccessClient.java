package JDBCAccess;

import DTO.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanBdAccessClient {
    public BeanBdAccessClient()
    {}

    public synchronized Client getClient(Connection con, String nc) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM client WHERE nom = ?");
        ps.setString(1,nc);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new Client(rs.getInt("id"), rs.getString("nom"),rs.getInt("type"),rs.getString("pwd"));

        return null;
    }
}

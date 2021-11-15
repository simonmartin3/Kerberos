package MySqlAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlAccess {
    private static Connection con = null;
    private static String url,username,password;

    public synchronized static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        if(con==null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(url, username, password);
        }
        return con;
    }


    public static void initialize(String url, String username, String password)
    {
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public static boolean testConnection() throws SQLException, ClassNotFoundException
    {
        return getConnection()!=null;
    }
    private static void setUrl(String s)
    {
        url = s;
    }
    private static void setUsername(String s)
    {
        username = s;
    }
    private static void setPassword(String s)
    {
        password = s;
    }
}

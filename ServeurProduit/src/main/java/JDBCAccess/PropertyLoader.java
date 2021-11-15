package JDBCAccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    public static Properties load(String filename)
    {
        Properties properties = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try
        {
            try {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return properties;
        }
        finally{
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

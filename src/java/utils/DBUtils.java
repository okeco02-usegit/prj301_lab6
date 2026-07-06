package utils;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;

public class DBUtils {
    private static DataSource dataSource;

    public static DataSource getDataSource() throws Exception {
        if (dataSource == null) {
            Properties p = new Properties();
            try (InputStream in = DBUtils.class.getClassLoader()
                    .getResourceAsStream("db.properties")) {
                if (in == null) throw new Exception("Cannot find db.properties");
                p.load(in);
            }

            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setServerName(p.getProperty("db.server"));
            ds.setPortNumber(Integer.parseInt(p.getProperty("db.port", "1433")));
            ds.setDatabaseName(p.getProperty("db.name"));
            ds.setUser(p.getProperty("db.user"));
            ds.setPassword(p.getProperty("db.password"));
            ds.setEncrypt(true);
            ds.setTrustServerCertificate(true);
            dataSource = ds;
        }
        return dataSource;
    }
}

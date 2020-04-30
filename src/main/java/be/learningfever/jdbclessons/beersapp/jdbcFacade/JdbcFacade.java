package be.learningfever.jdbclessons.beersapp.jdbcFacade;

import javax.security.auth.login.CredentialException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcFacade implements AutoCloseable {
    public static final int TYPE_STANDARD = ResultSet.TYPE_FORWARD_ONLY;
    public static final int TYPE_SCROLL = ResultSet.TYPE_SCROLL_INSENSITIVE;
    public static final int TYPE_SCROLL_SYNCED = ResultSet.TYPE_SCROLL_SENSITIVE;
    public static final int CONCUR_READ_ONLY = ResultSet.CONCUR_READ_ONLY;
    public static final int CONCUR_UPDATEABLE = ResultSet.CONCUR_UPDATABLE;
    public static final String PROPERTIES_PATH = "src/main/resources/mainInfo.properties";

    private Properties properties;
    private Connection connection;

    public JdbcFacade() {
        readProperties(PROPERTIES_PATH);
        createConnection();
    }

    public JdbcFacade(String propertiesPath) {
        readProperties(propertiesPath);
    }

    private void readProperties(String propertiesPath) {
        try (FileInputStream in = new FileInputStream(propertiesPath)) {

            properties = new Properties();
            properties.load(in);


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void createConnection() {
        try {

            connection = DriverManager.getConnection(getUrl(), getUser(), getPsw());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void close() throws Exception {
        connection.close();
    }

    public Properties getProperties() {
        return properties;
    }

    private void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getUrl() {
        return properties.getProperty("jdbc.url");
    }

    public String getUser() {
        return properties.getProperty("jdbc.user");
    }

    public String getPsw() {
        return properties.getProperty("jdbc.psw");
    }

    public Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Statement getStatement(int restultSetType, int resultSetConcurent) {
        try {
            return connection.createStatement(restultSetType, resultSetConcurent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String sql) {
        try {

            return getStatement().executeQuery(sql);

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public ResultSet executeQueryNavigable(String sql) {
        try {

            return getStatement(TYPE_SCROLL, CONCUR_READ_ONLY).executeQuery(sql);

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public ResultSet executeQueryNavigableUpdatable(String sql) {
        try {

            return getStatement(TYPE_SCROLL, CONCUR_UPDATEABLE).executeQuery(sql);

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
}

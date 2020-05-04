package be.learningfever.jdbclessons.beersapp.jdbcFacade;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
    private ArrayList<Statement> statements = new ArrayList<>();
    private ArrayList<ResultSet> results = new ArrayList<>();

    public JdbcFacade() {
        this(PROPERTIES_PATH);
    }

    public JdbcFacade(String propertiesPath) {
        readProperties(propertiesPath);
        createConnection();
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
        for (ResultSet result : results) {
            result.close();
        }

        for (Statement state : statements) {
            state.close();
        }

        connection.close();
    }

    Properties getProperties() {
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
            Statement statement = connection.createStatement();
            statements.add(statement);
            return statement;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Statement getStatement(int restultSetType, int resultSetConcurent) {
        try {
            Statement statement = connection.createStatement(restultSetType, resultSetConcurent);
            statements.add(statement);
            return statement;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String sql) {
        try {

            ResultSet result = getStatement().executeQuery(sql);
            results.add(result);

            return result;

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public ResultSet executeQueryNavigable(String sql) {
        try {

            ResultSet result = getStatement(TYPE_SCROLL, CONCUR_READ_ONLY).executeQuery(sql);
            results.add(result);

            return result;

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public ResultSet executeQueryNavigableUpdatable(String sql) {
        try {

            ResultSet result = getStatement(TYPE_SCROLL, CONCUR_UPDATEABLE).executeQuery(sql);
            results.add(result);

            return result;

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public int executeUpdate(String sql) {
        try {
            return getStatement().executeUpdate(sql);
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public PreparedStatement prepareStatement(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statements.add(statement);
            return statement;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
}

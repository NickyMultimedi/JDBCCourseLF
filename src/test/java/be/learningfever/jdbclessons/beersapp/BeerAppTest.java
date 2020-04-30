package be.learningfever.jdbclessons.beersapp;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class BeerAppTest {

    @BeforeEach
    public void init() throws SQLException, Exception {
        try (
                Connection connect = DriverManager.getConnection(
                        "jdbc:hsqldb:mem:mymemdb",
                        "sa",
                        ""
                );
                Statement statement = connect.createStatement();
                Reader schemaReader = new BufferedReader(new FileReader("src/test/resources/schema.sql"));
                Reader datareader = new BufferedReader(new FileReader("src/test/resources/data.sql"));
                ) {

            ScriptRunner runner = new ScriptRunner(connect);
            runner.runScript(schemaReader);
            runner.runScript(datareader);

        }
    }

}

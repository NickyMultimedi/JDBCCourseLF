package be.learningfever.jdbclessons.beersapp;

import be.learningfever.jdbclessons.beersapp.jdbcFacade.JdbcFacade;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class BeerDataTest {
    public final static String TEST_DB_RESOURCES = "src/test/resources/mainInfo.properties";
    JdbcFacade facade;
    BeerData data;

    @BeforeEach
    void init() {
        facade = new JdbcFacade(TEST_DB_RESOURCES);
        data = new BeerData(facade);

        try (
            Reader schemaReader = new BufferedReader(new FileReader("src/test/resources/schema.sql"));
            Reader datareader = new BufferedReader(new FileReader("src/test/resources/data.sql"));
        ) {
            ScriptRunner runner = new ScriptRunner(facade.getConnection());
            runner.runScript(schemaReader);
            runner.runScript(datareader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void destroy() throws Exception {
        facade.close();
        facade = null;
        data = null;
    }

    @Test
    void testGetBeerById() {
        String expected = "TestBeer : 2.75 euro with 7.00 Alc.\n100 in stock\nBrewer: 1\nCategory: 1\n";
        String result = data.getBeerById(1);
        assertEquals(expected, result);
    }

//    public void getBeerByIdAndChangePrice(int id, double price) {}
    @Test
    void testGetBeerByIdAndChangePrice() {
        String expected = "TestBeer : 4.00 euro with 7.00 Alc.\n100 in stock\nBrewer: 1\nCategory: 1\n";
        data.getBeerByIdAndChangePrice(1,4);
        String result = data.getBeerById(1);
        assertEquals(expected, result);
    }

}

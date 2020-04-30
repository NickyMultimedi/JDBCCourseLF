package be.learningfever.jdbclessons.beersapp.jdbcFacade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbcFacadeTest {
    public final static String TEST_DB_RESOURCES = "src/test/resources/mainInfo.properties";
    JdbcFacade facade;

    @BeforeEach
    void init() {
        facade = new JdbcFacade(TEST_DB_RESOURCES);
    }

    @Test
    void urlRead() {
        String url = facade.getUrl();
        String expectedUrl = "jdbc:hsqldb:mem:mymemdb";
        assertEquals(expectedUrl, url);
    }

}

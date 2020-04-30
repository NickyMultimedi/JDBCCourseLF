package be.learningfever.jdbclessons.beersapp;

import be.learningfever.jdbclessons.beersapp.jdbcFacade.JdbcFacade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class BeerApp {

    public static void main(String[] args) {

        try (
                JdbcFacade helper = new JdbcFacade();
                ) {

            ResultSet result = helper.executeQuery("select * from Beers where Id = 4");

            while (result.next()) {
                System.out.println(result.getString("Name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

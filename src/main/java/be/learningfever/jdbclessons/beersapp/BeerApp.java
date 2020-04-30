package be.learningfever.jdbclessons.beersapp;

import java.sql.*;

public class BeerApp {
    private static final String URL ="jdbc:mariadb://javadev-training.be/javadevt_Lf001";
    private static final String USER = "javadevt_StudLf";
    private static final String PSW = "STUDENTlf2020";

    public static void main(String[] args) {

        //showBeers();
        //showBeersOrderedByAlcohol();
        //showBeersWithAlcohol(15);

        try (
                Connection connect = DriverManager.getConnection(URL, USER, PSW);
                ) {

            System.out.println(showBeerWithId(connect, 4));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showBeers() {
        String sql = "select * from Beers";

        try (
                Connection connect = DriverManager.getConnection(URL, USER, PSW);
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(sql);
                ) {

            while(result.next()) {
                String beerName = result.getString("Name");
                double price = result.getDouble("Price");
                double alcohol = result.getDouble("Alcohol");
                System.out.printf("%s %.2f %.2f euro%n", beerName, alcohol, price);
            }


        } catch (Exception e) {
            System.out.println("Oeps, sometehing went wrong");
            e.printStackTrace();
        }

    }

    public static void showBeersOrderedByAlcohol() {
        String sql = "select * from Beers ORDER BY Alcohol";

        try (
                Connection connect = DriverManager.getConnection(URL, USER, PSW);
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(sql);
        ) {

            while(result.next()) {
                String beerName = result.getString("Name");
                double price = result.getDouble("Price");
                double alcohol = result.getDouble("Alcohol");
                System.out.printf("%s %.2f %.2f euro%n", beerName, alcohol, price);
            }


        } catch (Exception e) {
            System.out.println("Oeps, sometehing went wrong");
            e.printStackTrace();
        }
    }

    public static void showBeersWithAlcohol(double alcoholLevel) {
        String sql = "select * from Beers where Alcohol = " + alcoholLevel;

        try (
                Connection connect = DriverManager.getConnection(URL, USER, PSW);
                Statement statement = connect.createStatement();
                ResultSet result = statement.executeQuery(sql);
        ) {

            while(result.next()) {
                String beerName = result.getString("Name");
                double price = result.getDouble("Price");
                double alcohol = result.getDouble("Alcohol");
                System.out.printf("%s %.2f %.2f euro%n", beerName, alcohol, price);
            }


        } catch (Exception e) {
            System.out.println("Oeps, sometehing went wrong");
            e.printStackTrace();
        }
    }

    public static String showBeerWithId(Connection connect, int id) throws SQLException {
        String sql = "select * from Beers where Id = " + id + ";";

        Statement statement = connect.createStatement();

        ResultSet result = statement.executeQuery(sql);

        while(result.next()) {
            return result.getString("Name");
        }

        return "FOUTMELDING: Geen resultaten";
    }

}

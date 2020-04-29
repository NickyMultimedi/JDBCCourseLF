package be.learningfever.jdbclessons;

import java.sql.*;

public class FirstConnection {

    public static void main(String[] args) {
        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:mariadb://javadev-training.be/javadevt_Lf001",
                        "javadevt_StudLf",
                        "STUDENTlf2020"
                )
                ) {

            System.out.println("Connection OK");

        } catch (Exception e) {

            System.out.println("Oeps, That wasn't supposed to happen!");
            e.printStackTrace();

        }
    }
}

package be.learningfever.jdbclessons.beersapp;

import be.learningfever.jdbclessons.beersapp.jdbcFacade.JdbcFacade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

public class BeerData {
    private JdbcFacade jdbcHelper;

    public BeerData() {
        this(new JdbcFacade());
    }

    public BeerData(JdbcFacade facade) {
        setJdbcHelper(facade);
    }

    public JdbcFacade getJdbcHelper() {
        return jdbcHelper;
    }

    public void setJdbcHelper(JdbcFacade jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
    }

    public String getBeerById(int id){
        String sql = "Select * From Beers Where Id=" + id + ";";
        String name = "";
        double price = 0;
        double alcohol = 0;
        int stock = 0;
        int brewerId = 0;
        int categoryId = 0;

        try {

            ResultSet result = jdbcHelper.executeQuery(sql);

            while (result.next()) {
                name =  result.getString("Name");
                price = result.getDouble("Price");
                alcohol = result.getDouble("Alcohol");
                stock = result.getInt("Stock");
                brewerId = result.getInt("BrewerId");
                categoryId = result.getInt("CategoryId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.format(
                "%s : %.2f euro with %.2f Alc.%n%d in stock%nBrewer: %d%nCategory: %d%n",
                name,
                price,
                alcohol,
                stock,
                brewerId,
                categoryId
        );
    }

    public void getBeerByIdAndChangePrice(int id, double price) {
        String sql = "Select * from Beers where Id = " + id + ";";

        ResultSet result = jdbcHelper.executeQueryNavigableUpdatable(sql);
        try {
            while (result.next()) {
                result.updateDouble("Price", price);
                result.updateRow();
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }

    }
}

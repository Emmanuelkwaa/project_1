package com.skillstorm.data.Repositories.implementations;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.ProductRepoAbs;
import com.skillstorm.models.Category;
import com.skillstorm.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Product DAO
 * This inherit from the {ProductRepoAbs} which inherit {Repository} DAO
 * This expose this DAO to the generic DAO methods - like get(...), getAll(...), and delete(...)
 */
public class ProductRepo extends ProductRepoAbs {
    InventoryManagementDB db;
    public ProductRepo(InventoryManagementDB db) {
        super(db);
        this.db = db;
    }

    /**
     * The Upsert method does two things:
     * 1) Post - if a product's ID field is zero, it performs a POST request
     * 2) Put - if a product's ID field is not zero, it performs a PUT request
     * @param product   A Product object that contains the information for either POST or PUT
     * @return Product  The product object that was updated or created
     */
    public Product upsert(Product product) {
        String sqlInsert = "INSERT INTO Product (id, name, price, details, available_quantity, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Product SET name = ?, price = ?, details = ?, available_quantity = ?, category_id = ? WHERE id = " + product.getId();
        try (Connection conn = this.db.getInstance().getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false); // Prevents each query from immediately altering the database
            PreparedStatement ps;

            if (product.getId() < 1) {
                ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, product.getId());
                ps.setString(2, product.getName());
                ps.setDouble(3, product.getPrice());
                ps.setString(4, product.getDetails());
                ps.setInt(5, product.getAvailableQuantity());
                ps.setInt(6, product.getCategory().getId());
            } else {
                ps = conn.prepareStatement(sqlUpdate);
                ps.setString(1, product.getName());
                ps.setDouble(2, product.getPrice());
                ps.setString(3, product.getDetails());
                ps.setInt(4, product.getAvailableQuantity());
                ps.setInt(5, product.getCategory().getId());
            }

            int rowsAffected = ps.executeUpdate(); // If 0 is returned, my data didn't save
            if (rowsAffected != 0) {
                conn.commit(); // Executes ALL queries in a given transaction. Green button
                return product;
            } else {
                conn.rollback(); // Undoes any of the queries. Database pretends those never happened
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method returns a list of products, each with their category
     * @return List<Product>    A list of Product objects
     */
    @Override
    public List<Product> getProductWithCategory() {
        String sql = "SELECT product.id, product.name, product.price, product.details, product.available_quantity, category.id AS category_id, category.name AS category_name\n" +
                "FROM product\n" +
                "INNER JOIN category\n" +
                "ON product.category_id = category.id;";

        // Connection will auto close in the event of a failure. Due to Autocloseable
        try (Connection conn = this.db.getInstance().getConnection()) {
            // ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
            // Create a Statement using the Connection object
            Statement stmt = conn.createStatement();

            // Executing the query returns a ResultSet which contains all of the values
            // returned
            ResultSet resultSet = stmt.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("details"),
                    resultSet.getInt("available_quantity"),
                    new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                    )
                );

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

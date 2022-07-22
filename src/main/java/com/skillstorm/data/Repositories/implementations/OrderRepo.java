package com.skillstorm.data.Repositories.implementations;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.OrderRepoAbs;
import com.skillstorm.models.Customer;
import com.skillstorm.models.Order;
import com.skillstorm.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Order DAO
 * This inherit from the {OrderRepoAbs} which inherit {Repository} DAO
 * This expose this DAO to the generic DAO methods - like get(...), getAll(...), and delete(...)
 */
public class OrderRepo extends OrderRepoAbs {
    InventoryManagementDB db;

    public OrderRepo(InventoryManagementDB db) {
        super(db);
        this.db = db;
    }

    /**
     * This method performs an update to the Order table
     * @param order     An Order object that contains the information for PUT request
     * @return Order    The Order object that was updated
     */
    @Override
    public Order update(Order order) {
        String sqlUpdate = "UPDATE Orders SET product_id = ?, quantity = ?, customer_id = ?, total_cost = ? WHERE id = ?";
        try (Connection conn = this.db.getInstance().getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false); // Prevents each query from immediately altering the database
            PreparedStatement ps;
            ps = conn.prepareStatement(sqlUpdate);
            ps.setInt(1, order.getProduct().getId());
            ps.setDouble(2, order.getQuantity());
            ps.setInt(3, order.getCustomer().getId());
            ps.setDouble(4, order.getTotalCost());
            ps.setInt(5, order.getId());

            int rowsAffected = ps.executeUpdate(); // If 0 is returned, my data didn't save
            if (rowsAffected != 0) {
                // If I want my keys do this code
                conn.commit(); // Executes ALL queries in a given transaction. Green button
                return order;
            } else {
                conn.rollback(); // Undoes any of the queries. Database pretends those never happened
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method returns a list of orders, each with their product and customer
     * @return List<Order>    A list of Order objects
     */
    @Override
    public List<Order> completeOrders() {
        String sql = "SELECT orders.id, orders.quantity, orders.total_cost, orders.order_date, product.id AS product_id, product.name AS product_name, customer.id AS customer_id, customer.first_name AS first_name, customer.last_name AS last_name, customer.phone, customer.email\n" +
                "FROM orders\n" +
                "INNER JOIN product\n" +
                "ON orders.product_id = product.id\n" +
                "INNER JOIN customer\n" +
                "ON orders.customer_id = customer.id;";

        // Connection will auto close in the event of a failure. Due to Autocloseable
        try (Connection conn = this.db.getInstance().getConnection()) {
            // ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
            // Create a Statement using the Connection object
            Statement stmt = conn.createStatement();

            // Executing the query returns a ResultSet which contains all of the values
            // returned
            ResultSet resultSet = stmt.executeQuery(sql);
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("total_cost"),
                    resultSet.getDate("order_date"),
                    new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name")
                    ),
                    new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email")
                    )
                );

                orders.add(order);
            }

            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

package com.skillstorm.data.Repositories.implementations;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.CategoryRepoAbs;
import com.skillstorm.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryRepo extends CategoryRepoAbs {
    InventoryManagementDB db;

    public CategoryRepo(InventoryManagementDB db) {
        super(db);
        this.db = db;
    }


    @Override
    public Category upsert(Category category) {
        String sqlInsert = "INSERT INTO Category (id, name) VALUES (?, ?)";
        String sqlUpdate = "UPDATE Product SET name = ? WHERE id = " + category.getId();
        try (Connection conn = this.db.getInstance().getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false);
            PreparedStatement ps;

            if (category.getId() < 1) {
                ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, category.getId());
                ps.setString(2, category.getName());
            } else {
                ps = conn.prepareStatement(sqlUpdate);
                ps.setString(1, category.getName());
            }

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                conn.commit();
                return category;
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

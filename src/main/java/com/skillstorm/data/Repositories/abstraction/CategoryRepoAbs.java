package com.skillstorm.data.Repositories.abstraction;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.implementations.Repository;
import com.skillstorm.models.Category;

public abstract class CategoryRepoAbs extends Repository<Category> implements CategoryRepoInterface{
    public CategoryRepoAbs(InventoryManagementDB db) {
        super(db);
    }
}

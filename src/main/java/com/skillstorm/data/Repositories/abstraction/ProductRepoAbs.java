package com.skillstorm.data.Repositories.abstraction;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.implementations.Repository;
import com.skillstorm.models.Product;

public abstract class ProductRepoAbs extends Repository<Product> implements ProductRepoInterface<Product>{
    InventoryManagementDB db;
    public ProductRepoAbs(InventoryManagementDB db) {
        super(db);
        this.db = db;
    }
}

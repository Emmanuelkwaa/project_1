package com.skillstorm.data.Repositories.implementations;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.CategoryRepoAbs;
import com.skillstorm.data.Repositories.abstraction.IUnitOfWork;
import com.skillstorm.data.Repositories.abstraction.OrderRepoAbs;
import com.skillstorm.data.Repositories.abstraction.ProductRepoAbs;

public class UnitOfWork implements IUnitOfWork {
    private InventoryManagementDB db;
    private ProductRepoAbs productRepo;
    private OrderRepoAbs orderRepo;
    private CategoryRepoAbs categoryRepo;

    public UnitOfWork(InventoryManagementDB db) {
        this.db = db;
        productRepo = new ProductRepo(this.db);
        orderRepo = new OrderRepo(this.db);
        categoryRepo = new CategoryRepo(this.db);
    }

    @Override
    public ProductRepoAbs product() {
        return productRepo;
    }

    @Override
    public OrderRepoAbs order() {
        return orderRepo;
    }

    @Override
    public CategoryRepoAbs category() {
        return categoryRepo;
    }
}

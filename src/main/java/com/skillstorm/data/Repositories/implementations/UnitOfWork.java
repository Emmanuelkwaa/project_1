package com.skillstorm.data.Repositories.implementations;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.CategoryRepoAbs;
import com.skillstorm.data.Repositories.abstraction.IUnitOfWork;
import com.skillstorm.data.Repositories.abstraction.OrderRepoAbs;
import com.skillstorm.data.Repositories.abstraction.ProductRepoAbs;

/**
 * This class is used to group all the DAO (repos) into a single transaction or “unit of work”
 * so that all operations either pass or fail as one unit
 * This makes it possible for the DAOs to share a single database context
 */
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

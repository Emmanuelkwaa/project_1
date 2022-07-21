package com.skillstorm.data.Repositories.abstraction;

import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.implementations.Repository;
import com.skillstorm.models.Order;

public abstract class OrderRepoAbs extends Repository<Order> implements OrderRepoInterface<Order>  {
    public OrderRepoAbs(InventoryManagementDB db) {
        super(db);
    }
}

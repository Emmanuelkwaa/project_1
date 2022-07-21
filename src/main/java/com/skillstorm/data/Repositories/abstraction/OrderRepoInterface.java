package com.skillstorm.data.Repositories.abstraction;

import com.skillstorm.models.Order;

import java.util.List;

public interface OrderRepoInterface<T> {
    Order update(T order);
    List<Order> completeOrders();
}

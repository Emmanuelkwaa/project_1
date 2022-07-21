package com.skillstorm.data.Repositories.abstraction;

import com.skillstorm.models.Product;

import java.util.List;

public interface ProductRepoInterface<T> {
    Product upsert(T product);
    List<Product> getProductWithCategory();
}

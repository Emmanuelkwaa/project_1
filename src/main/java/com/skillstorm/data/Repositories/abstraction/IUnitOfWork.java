package com.skillstorm.data.Repositories.abstraction;

public interface IUnitOfWork {
    ProductRepoAbs product();
    OrderRepoAbs order();
    CategoryRepoAbs category();
}

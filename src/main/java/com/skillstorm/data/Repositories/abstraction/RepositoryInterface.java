package com.skillstorm.data.Repositories.abstraction;

import java.util.Collection;

public interface RepositoryInterface<T> {
    T get(int id, String tableName);
    T getByName(String name, String tableName);
    Collection<T> getAll(String tableName);
    boolean delete(int id, String tableName);
}

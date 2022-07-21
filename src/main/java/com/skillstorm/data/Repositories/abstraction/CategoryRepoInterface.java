package com.skillstorm.data.Repositories.abstraction;

import com.skillstorm.models.Category;

public interface CategoryRepoInterface<T> {
    Category upsert(Category category);
}

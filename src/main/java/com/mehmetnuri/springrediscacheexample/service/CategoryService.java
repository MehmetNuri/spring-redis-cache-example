package com.mehmetnuri.springrediscacheexample.service;

import com.mehmetnuri.springrediscacheexample.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAll();

    public Category add(Category category);

    public Category update(Category category);

    public void delete(Long id);

    public Category getCategoryById(Long id);

    public Category getOne(Long id);
}

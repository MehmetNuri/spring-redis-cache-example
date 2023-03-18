package com.mehmetnuri.springrediscacheexample.service.impl;

import com.mehmetnuri.springrediscacheexample.entity.Category;
import com.mehmetnuri.springrediscacheexample.repository.CategoryRepository;
import com.mehmetnuri.springrediscacheexample.service.CategoryService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "categoryCache")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EntityManager entityManager;

    public CategoryServiceImpl(CategoryRepository categoryRepository, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
    }


    @Cacheable(cacheNames = "categories")
    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    @Override
    public Category add(Category category) {
        return this.categoryRepository.save(category);
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    @Override
    public Category update(Category category) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(category.getId());
        if (!optionalCategory.isPresent())
            return null;
        Category repCategory = optionalCategory.get();
        repCategory.setName(category.getName());
        return this.categoryRepository.save(repCategory);
    }

    @Caching(evict = {@CacheEvict(cacheNames = "category", key = "#id"),
            @CacheEvict(cacheNames = "categories", allEntries = true)})
    @Override
    public void delete(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "category", key = "#id", unless = "#result == null")
    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "category", key = "#id", unless = "#result == null")
    @Override
    public Category getOne(Long id) {
        String hql = "SELECT c FROM Category c WHERE c.id = :id";
        return entityManager.createQuery(hql, Category.class)
                .setParameter("id", id)
                .getSingleResult();

    }
}

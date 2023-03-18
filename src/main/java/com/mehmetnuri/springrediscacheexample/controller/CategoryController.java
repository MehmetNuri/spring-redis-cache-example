package com.mehmetnuri.springrediscacheexample.controller;


import com.mehmetnuri.springrediscacheexample.entity.Category;
import com.mehmetnuri.springrediscacheexample.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/categories",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<Category> findAll() {
        return this.categoryService.getAll();
    }

    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        return this.categoryService.add(category);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return this.categoryService.getCategoryById(id);
    }

    @PutMapping("/update")
    public Category update(@RequestBody Category category) {
        return this.categoryService.update(category);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        this.categoryService.delete(id);
    }

    @GetMapping("/getone/{id}")
    public Category getOne(@PathVariable Long id) {
        return this.categoryService.getOne(id);
    }
}

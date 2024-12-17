package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// Make this a REST controller
@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoriesController
{
    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao)
    {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // GET: Retrieve all categories
    @GetMapping
    public List<Category> getAllCategories()
    {
        return categoryDao.getAllCategories();
    }

    // GET: Retrieve a single category by ID
    @GetMapping("{id}")
    public Category getCategoryById(@PathVariable int id)
    {
        return categoryDao.getCategoryById(id);
    }

    // GET: Retrieve all products in a specific category
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsByCategory(@PathVariable int categoryId)
    {
        return productDao.getProductsByCategoryId(categoryId);
    }

    // POST: Add a new category
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category)
    {
        return categoryDao.addCategory(category);
    }

    // PUT: Update an existing category by ID
    @PutMapping("{id}")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        category.setCategoryId(id);
        categoryDao.updateCategory(category);
    }

    // DELETE: Remove a category by ID
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable int id)
    {
        categoryDao.deleteCategory(id);
    }
}

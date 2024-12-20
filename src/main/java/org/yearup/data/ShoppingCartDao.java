package org.yearup.data;

import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartDao {

    void update(int categoryId, Category category);

    List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color, String name);

    List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color);

    void delete(int categoryId);

    ShoppingCart getByUserId(int userId);

    ShoppingCart getCartByUserId(int userId);

    void addProductToCart(int userId, int productId);
    void updateProductQuantity(int userId, int productId, int quantity);
    void clearCart(int userId);
}

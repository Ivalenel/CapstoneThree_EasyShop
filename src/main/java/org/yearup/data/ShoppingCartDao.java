package org.yearup.data;

import org.springframework.stereotype.Repository;
import org.yearup.models.ShoppingCart;
@Repository
public interface ShoppingCartDao
{
    // Retrieve the shopping cart for a user
    ShoppingCart getCartByUserId(int userId);

    // Add a product to the user's shopping cart
    void addProductToCart(int userId, int productId);

    // Update the quantity of a product in the cart
    void updateProductQuantity(int userId, int productId, int quantity);

    // Clear all products from the user's shopping cart
    void clearCart(int userId);
}


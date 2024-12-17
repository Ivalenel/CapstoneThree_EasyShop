package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController
{
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // GET method to retrieve all items in the shopping cart
    @GetMapping
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // Fetch the user's cart
            return shoppingCartDao.getCartByUserId(userId);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch the cart.");
        }
    }

    // POST method to add a product to the shopping cart
    @PostMapping("/products/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable int productId, Principal principal)
    {
        try
        {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            shoppingCartDao.addProductToCart(userId, productId);

            return ResponseEntity.ok("Product added to cart successfully.");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add product to cart.");
        }
    }

    // PUT method to update quantity of an existing product in the cart
    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProductQuantity(@PathVariable int productId, @RequestBody ShoppingCartItem item, Principal principal)
    {
        try
        {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            shoppingCartDao.updateProductQuantity(userId, productId, item.getQuantity());

            return ResponseEntity.ok("Product quantity updated successfully.");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update product quantity.");
        }
    }

    // DELETE method to clear all products from the shopping cart
    @DeleteMapping
    public ResponseEntity<String> clearCart(Principal principal)
    {
        try
        {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            shoppingCartDao.clearCart(userId);

            return ResponseEntity.ok("Shopping cart cleared successfully.");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to clear the shopping cart.");
        }
    }
}

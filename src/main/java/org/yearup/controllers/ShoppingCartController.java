package org.yearup.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("cart")
public class ShoppingCartController {
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
    }

    private int getUserIdFromPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return user.getId();
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal) {
//        System.out.println("Principal: " + principal);
//        int userId = getUserIdFromPrincipal(principal);
        String userName = principal.getName();
        int userId = userDao.getIdByUsername(userName);
        return shoppingCartDao.getCartByUserId(userId);

    }

    @PostMapping("/{productId}")
    public void addToCart(@PathVariable int productId, Principal principal) {
        int userId = getUserIdFromPrincipal(principal);
        shoppingCartDao.addProductToCart(userId, productId);
    }

    @PutMapping("/{productId}/{quantity}")
    public void updateCartQuantity(@PathVariable int productId, @PathVariable int quantity, Principal principal) {
        int userId = getUserIdFromPrincipal(principal);
        shoppingCartDao.updateProductQuantity(userId, productId, quantity);
    }

    @DeleteMapping
    public void clearCart(Principal principal) {
        int userId = getUserIdFromPrincipal(principal);
        shoppingCartDao.clearCart(userId);
    }

}

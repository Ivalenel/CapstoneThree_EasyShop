package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void updateCategory(int categoryId, Category category) {

    }

    @Override
    public void deleteCategory(int categoryId) {

    }

    @Override
    public void update(int categoryId, Category category) {

    }

    @Override
    public List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color, String name) {
        return null;
    }

    @Override
    public List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color) {
        return null;
    }

    @Override
    public void delete(int categoryId) {

    }

    @Override
    public ShoppingCart getCartByUserId(int userId) {
        String sql = "SELECT product_id, quantity FROM shopping_cart WHERE user_id = ?";
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userId);
        List<ShoppingCartItem> items = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShoppingCartItem item = new ShoppingCartItem();
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
            cart.setItems((Map<Integer, ShoppingCartItem>) items);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve cart", e);
        }
        return cart;
    }

    @Override
    public void addProductToCart(int userId, int productId) {
        String sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) " +
                "VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE quantity = quantity + 1";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, productId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to add product to cart", e);
        }
    }

    @Override
    public void updateProductQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update product quantity", e);
        }
    }

    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to clear shopping cart", e);
        }
    }
}

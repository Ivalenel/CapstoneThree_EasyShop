package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{

    public MySqlShoppingCartDao(DataSource dataSource)
    {
        super(dataSource);
    }

    public Connection getConnection() throws SQLException {
        MySqlDaoBase dataSource = null;
        return dataSource.getConnection();
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
    public ShoppingCart getByUserId(int userId) {
        return null;
    }

    @Override
    public ShoppingCart getCartByUserId(int userId) {
        String query = "SELECT p.product_id, p.name, p.price, p.description, p.color, p.stock, p.image_url, p.featured, sc.quantity " +
                "FROM shopping_cart sc " +
                "JOIN products p ON sc.product_id = p.product_id " +
                "WHERE sc.user_id = ?;";

        ShoppingCart cart = new ShoppingCart();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("product_id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getBigDecimal("price"));
                    product.setDescription(resultSet.getString("description"));
                    product.setColor(resultSet.getString("color"));
                    product.setStock(resultSet.getInt("stock"));
                    product.setImageUrl(resultSet.getString("image_url"));
                    product.setFeatured(resultSet.getBoolean("featured"));

                    ShoppingCartItem item = new ShoppingCartItem();
                    item.setProduct(product);
                    item.setQuantity(resultSet.getInt("quantity"));

                    cart.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving shopping cart for userId: " + userId, e);
        }
        return cart;
    }

    @Override
    public void addProductToCart(int userId, int productId) {
        String query = "INSERT INTO shopping_cart (user_id, product_id, quantity) " +
                "VALUES (?, ?, 1) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + 1;";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding product " + productId + " to cart for userId: " + userId, e);
        }
    }

    @Override
    public void updateProductQuantity(int userId, int productId, int quantity) {
        String query = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?;";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating quantity for product " + productId + " in cart for userId: " + userId, e);
        }
    }

    @Override
    public void clearCart(int userId) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ?;";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error clearing cart for userId: " + userId, e);
        }

    }
}

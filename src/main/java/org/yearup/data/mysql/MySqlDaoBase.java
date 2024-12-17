package org.yearup.data.mysql;

import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class MySqlDaoBase
{
    private DataSource dataSource;

    public MySqlDaoBase(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public abstract void updateCategory(int categoryId, Category category);

    public abstract void deleteCategory(int categoryId);

    public abstract void update(int categoryId, Category category);

    public abstract List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color, String name);

    public abstract List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color);

    public abstract void delete(int categoryId);
}

package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MySqlProductDaoTest extends BaseDaoTestClass
{
    private MySqlProductDao dao;

    @BeforeEach
    public void setup()
    {
        dao = new MySqlProductDao(dataSource);
    }

    @Test
    public void getById_shouldReturn_theCorrectProduct()
    {
        // arrange
        int productId = 1;
        Product expected = new Product()
        {{
            setProductId(1);
            setName("Smartphone");
            setPrice(new BigDecimal("499.99"));
            setCategoryId(1);
            setDescription("A powerful and feature-rich smartphone for all your communication needs.");
            setColor("Black");
            setStock(50);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};

        // act
        var actual = dao.getById(productId);

        // assert
        assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
    }

    @Test
    void update() {
    }

    @Test
    void search() {
    }

    @Test
    void getProductsByCategoryId() {
    }

    @Test
    void getById() {
    }

    @Test
    void create() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void delete() {
    }

    @Test
    void testGetProductsByCategoryId() {
    }

    @Test
    void mapRow() {
    }

    @Test
    void testGetProductsByCategoryId1() {
    }

    @Test
    void testUpdate1() {
    }

    @Test
    void testSearch() {
    }

    @Test
    void testGetProductsByCategoryId2() {
    }

    @Test
    void testGetById() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void testUpdate2() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void testGetProductsByCategoryId3() {
    }

    @Test
    void testMapRow() {
    }
}
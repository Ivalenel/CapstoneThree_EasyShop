# Easy Shop Project

## Overview
The **Easy Shop** project is a web application designed to manage an online marketplace with features to add, update, delete, and display product categories, as well as search for products based on specific filters. This application uses a structured architecture for controllers, DAOs, and models to ensure efficient data management and functionality.

---

## Phase 1: CategoriesController
### Description
In this phase, the `CategoriesController` class was created. The goal was to implement the methods for managing product categories while ensuring proper annotations and role-based access control.

### Key Features
- Only administrators (users with the `ADMIN` role) can:
  - Insert a new category
  - Update an existing category
  - Delete a category
- Utilized the pre-defined `Category` model for consistent data representation.
- Integrated the `MySqlCategoriesDao` with methods to handle database operations for categories.

## Bug Fixes
### Bug 1: Product Search Functionality
**Issue**: The product search functionality was returning incorrect results due to logic errors in the query implementation.

**Solution**:
- Tested the search logic to ensure accurate filtering based on query string parameters:
  - `cat`: Category ID (integer)
  - `minPrice`: Lower price range (BigDecimal)
  - `maxPrice`: Upper price range (BigDecimal)
  - `color`: Product color (string)
- Updated the query to properly handle multiple filters and edge cases.

 Bug 2: Duplicate Products
**Issue**: Some products appeared multiple times in the search results with slight differences (e.g., description, price).

**Solution**:
- Identified and resolved the duplication issue by updating the database query to group and filter distinct products based on unique attributes such as `productId`.
- Implemented logic to merge slight differences when necessary or prioritize consistent entries for display.



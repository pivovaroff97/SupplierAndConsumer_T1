package ru.pivovarov.t1.SupplierService;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.pivovarov.t1.SupplierService.model.Category;
import ru.pivovarov.t1.SupplierService.model.Product;
import ru.pivovarov.t1.SupplierService.service.CategoryService;
import ru.pivovarov.t1.SupplierService.service.ProductService;

import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryProductIT {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port + "/api/v1";
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    void saveCategory_shouldReturnCreated() {
        Category snackCategory = Category.builder()
                .name("TEST")
                .build();
        int id = given().contentType("application/json")
                .body(snackCategory)
                .when()
                .post(uri + "/categories")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
        Assertions.assertNotEquals(0, id);
    }
    @Test
    @Order(2)
    void saveProduct_shouldReturnCreated() {
        Product lays = Product.builder()
                .price(8)
                .name("TEST")
                .description("Lays crab")
                .build();
        int id = given().contentType("application/json")
                .body(lays)
                .when()
                .post(uri + "/products")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
        Assertions.assertNotEquals(0, id);
    }

    @Test
    @Order(3)
    void getCategories_shouldReturnAllCategories() {
        Category[] categories = get(uri + "/categories").then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Category[].class);
        Assertions.assertTrue(categories.length > 0);
    }

    @Test
    @Order(4)
    void getProducts_shouldReturnAllProducts() {
        List<Product> products = get(uri + "/products").then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("content");
        Assertions.assertTrue(products.size() > 0);
    }

    @Test
    @Order(5)
    void getProducts_shouldReturnOneProduct() {
        List<Product> products = get(uri + "/products?size=1").then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("content");
        Assertions.assertEquals(1, products.size());
    }

    @Test
    @Order(6)
    void getCategoryById_shouldReturnCategory() {
        Category category = get(uri + "/categories/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Category.class);
        Assertions.assertEquals(1, category.getId());
    }

    @Test
    @Order(7)
    void getProductById_shouldReturnProduct() {
        Product product = get(uri + "/products/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Product.class);
        Assertions.assertEquals(1, product.getId());
    }

    @Test
    @Order(8)
    @Transactional
    void updateCategoryById_shouldUpdateCategory() {
        Category category = categoryService.getCategoryById(1L);
        category.setName("TEST_UPDATED");
        given().contentType("application/json")
                .body(category)
                .when()
                .put(uri + "/categories/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value());
        category = categoryService.getCategoryById(1L);
        Assertions.assertEquals("TEST_UPDATED", category.getName());
    }

    @Test
    @Order(9)
    void updateProductById_shouldUpdateProduct() {
        Product product = productService.getProductById(1L);
        product.setName("TEST_UPDATED");
        given().contentType("application/json")
                .body(product)
                .when()
                .put(uri + "/products/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value());
        product = productService.getProductById(1L);
        Assertions.assertEquals("TEST_UPDATED", product.getName());
    }

    @Test
    @Order(10)
    void deleteProductById_shouldDeleteProduct() {
        given().delete(uri + "/products/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value());
        Assertions.assertThrows(NoSuchElementException.class, () -> productService.getProductById(1L));
    }

    @Test
    @Order(11)
    void deleteCategoryById_shouldDeleteCategory() {
        given().delete(uri + "/categories/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value());
        Assertions.assertThrows(NoSuchElementException.class, () -> categoryService.getCategoryById(1L));
    }
}

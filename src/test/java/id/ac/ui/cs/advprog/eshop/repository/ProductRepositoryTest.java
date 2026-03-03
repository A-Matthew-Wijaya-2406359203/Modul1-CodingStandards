package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepositoryImpl productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateProductWithNullId() {
        Product product = new Product();
        product.setProductName("Sampo Cap Kuda");
        product.setProductQuantity(50);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId());
        assertEquals("Sampo Cap Kuda", savedProduct.getProductName());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("12345-abcde");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("12345-abcde");
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getProductName());
    }

    @Test
    void testFindByIdNotFound() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testEditProductSuccess() {
        Product originalProduct = new Product();
        originalProduct.setProductId("edit-id-123");
        originalProduct.setProductName("Original Name");
        originalProduct.setProductQuantity(10);
        productRepository.create(originalProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("edit-id-123");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());

        Product productInRepo = productRepository.findById("edit-id-123");
        assertEquals("Updated Name", productInRepo.getProductName());
        assertEquals(20, productInRepo.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product unregisteredProduct = new Product();
        unregisteredProduct.setProductId("ghost-id");
        unregisteredProduct.setProductName("Ghost Product");
        unregisteredProduct.setProductQuantity(0);

        Product result = productRepository.update(unregisteredProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductId("delete-id-123");
        product.setProductName("To Be Deleted");
        product.setProductQuantity(5);
        productRepository.create(product);

        assertNotNull(productRepository.findById("delete-id-123"));

        productRepository.delete("delete-id-123");

        assertNull(productRepository.findById("delete-id-123"));
    }

    @Test
    void testDeleteProductNotFound() {
        assertDoesNotThrow(() -> productRepository.delete("random-id"));
    }
}
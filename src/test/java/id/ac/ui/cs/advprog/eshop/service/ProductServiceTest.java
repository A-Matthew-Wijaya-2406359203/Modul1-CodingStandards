package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductId("test-id-1");
        product.setProductName("Sampo Cap Kuda");
        product.setProductQuantity(10);

        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertEquals(product.getProductId(), createdProduct.getProductId());
        assertEquals(product.getProductName(), createdProduct.getProductName());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        // Setup mock data
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("test-id-1");
        Product product2 = new Product();
        product2.setProductId("test-id-2");
        productList.add(product1);
        productList.add(product2);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        // Verify the results
        assertEquals(2, result.size());
        assertEquals(product1.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("test-id-1");
        product.setProductName("Sampo Cap Kuda");

        when(productRepository.findById("test-id-1")).thenReturn(product);

        Product foundProduct = productService.findById("test-id-1");

        assertNotNull(foundProduct);
        assertEquals("test-id-1", foundProduct.getProductId());
        assertEquals("Sampo Cap Kuda", foundProduct.getProductName());
        verify(productRepository, times(1)).findById("test-id-1");
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setProductId("test-id-1");
        product.setProductName("Updated Name");

        productService.edit(product);

        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDelete() {
        String productId = "test-id-1";

        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }
}
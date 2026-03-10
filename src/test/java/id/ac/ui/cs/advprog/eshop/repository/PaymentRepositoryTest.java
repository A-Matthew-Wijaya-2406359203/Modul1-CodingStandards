package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        // Buat dummy product agar list tidak kosong
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        // Gunakan list products tersebut untuk membuat order
        Order order = new Order("order-1", products, 1000L, "Bambang");
        payment = new Payment("pay-1", "VOUCHER_CODE", new HashMap<>(), order);
    }

    @Test
    void testSaveAndFindById() {
        paymentRepository.save(payment);
        Payment found = paymentRepository.findById("pay-1");
        assertNotNull(found);
        assertEquals("pay-1", found.getId());
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);
        List<Payment> payments = paymentRepository.findAll();
        assertFalse(payments.isEmpty());
    }
}
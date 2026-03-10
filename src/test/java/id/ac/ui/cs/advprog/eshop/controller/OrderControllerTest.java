package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product; // Import ditambahkan
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/createOrder"));
    }

    @Test
    void testOrderHistoryForm() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderHistoryForm"));
    }

    @Test
    void testShowOrderHistory() throws Exception {
        List<Order> orders = new ArrayList<>();
        when(orderService.findAllByAuthor("Bambang")).thenReturn(orders);

        mockMvc.perform(post("/order/history").param("author", "Bambang"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("order/orderHistory"));
    }

    @Test
    void testPayOrderForm() throws Exception {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        Order order = new Order("order-1", products, 1000L, "Bambang");
        when(orderService.findById("order-1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/order-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("order"))
                .andExpect(view().name("order/orderPay"));
    }

    @Test
    void testPayOrderPost() throws Exception {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        Order order = new Order("order-1", products, 1000L, "Bambang");
        Payment payment = new Payment("pay-1", "VOUCHER_CODE", new HashMap<>(), order);

        when(orderService.findById("order-1")).thenReturn(order);
        when(paymentService.addPayment(eq(order), eq("VOUCHER_CODE"), any())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/order-1")
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("paymentId"))
                .andExpect(view().name("order/orderPaySuccess"));
    }

    @Test
    void testCreateOrderPost() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(null);

        mockMvc.perform(post("/order/create").param("author", "Bambang"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));
    }
}
package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PaymentControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void testPaymentDetailForm() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentDetailForm"));
    }

    @Test
    void testPaymentDetailById() throws Exception {
        Payment payment = new Payment("pay-1", "VOUCHER_CODE", new HashMap<>(), new Order("1", new ArrayList<>(), 100L, "A"));
        when(paymentService.getPayment("pay-1")).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/pay-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"))
                .andExpect(view().name("payment/paymentDetail"));
    }

    @Test
    void testAdminList() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payments"))
                .andExpect(view().name("payment/paymentAdminList"));
    }

    @Test
    void testAdminDetail() throws Exception {
        Payment payment = new Payment("pay-1", "VOUCHER_CODE", new HashMap<>(), new Order("1", new ArrayList<>(), 100L, "A"));
        when(paymentService.getPayment("pay-1")).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/pay-1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"))
                .andExpect(view().name("payment/paymentAdminDetail"));
    }

    @Test
    void testAdminSetStatus() throws Exception {
        Payment payment = new Payment("pay-1", "VOUCHER_CODE", new HashMap<>(), new Order("1", new ArrayList<>(), 100L, "A"));
        when(paymentService.getPayment("pay-1")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/pay-1")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));
    }
}
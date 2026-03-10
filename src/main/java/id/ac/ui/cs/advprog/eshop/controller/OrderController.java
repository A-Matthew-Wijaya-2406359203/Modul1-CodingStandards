package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderForm() {
        return "order/createOrder";
    }

    @GetMapping("/history")
    public String historyForm() {
        return "order/orderHistoryForm";
    }

    @PostMapping("/history")
    public String showHistory(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders); // Change to addAttribute
        model.addAttribute("author", author); // Change to addAttribute
        return "order/orderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderForm(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/orderPay";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable String orderId,
                           @RequestParam String method,
                           @RequestParam Map<String, String> allParams,
                           Model model) {
        Order order = orderService.findById(orderId);
        Payment payment = paymentService.addPayment(order, method, allParams);
        model.addAttribute("paymentId", payment.getId());
        return "order/orderPaySuccess";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam String author) {
        // Kita membuat dummy product karena Order tidak boleh memiliki list produk kosong
        List<Product> products = new ArrayList<>();
        Product dummyProduct = new Product();
        dummyProduct.setProductId("prod-dummy");
        dummyProduct.setProductName("Dummy Item for Order");
        dummyProduct.setProductQuantity(1);
        products.add(dummyProduct);

        Order order = new Order(UUID.randomUUID().toString(), products, System.currentTimeMillis(), author);
        orderService.createOrder(order);

        return "redirect:/order/history";
    }
}
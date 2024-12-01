package com.turbil.ecommerce;

import com.turbil.ecommerce.entity.Cart;
import com.turbil.ecommerce.entity.Customer;
import com.turbil.ecommerce.entity.Order;
import com.turbil.ecommerce.entity.Product;
import com.turbil.ecommerce.service.CartService;
import com.turbil.ecommerce.service.CustomerService;
import com.turbil.ecommerce.service.OrderService;
import com.turbil.ecommerce.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class EcommerceApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);

        // CUSTOMER SERVICE
        //
        CustomerService customerService = context.getBean(CustomerService.class);
        Customer customer1 = customerService.AddCustomer("Timur", "123456");


        // PRODUCT SERVICE
        //
        ProductService productService = context.getBean(ProductService.class);
        // CREATE PRODUCT
        Product product1 = productService.CreateProduct("Iphone 12 Pro", 15, 49.99);
        Product product2 = productService.CreateProduct("Samsung Galaxy S21", 5, 39.99);
        Product product3 = productService.CreateProduct("Huawei P40 Pro", 3, 29.99);
        Product product4 = productService.CreateProduct("Xiaomi Mi 11", 10, 19.99);
        // GET PRODUCT
        Product product = productService.GetProduct(product1.getId());
        // UPDATE PRODUCT
        productService.UpdateProduct(product.getId(), "Iphone 12 Pro", 10, 49.99);


        // CART SERVICE
        //
        CartService cartService = context.getBean(CartService.class);
        // ADD PRODUCT TO CART
        // (Add 2 Iphone 12 Pro to cart)
        cartService.AddProductToCart(customer1.getId(), product1.getId(), 2);
        // (Add 1 Samsung Galaxy S21 to cart)
        cartService.AddProductToCart(customer1.getId(), product2.getId(), 1);
        // (Add 3 Huawei P40 Pro to cart)
        cartService.AddProductToCart(customer1.getId(), product3.getId(), 3);
        // GET CART
        Cart cart = cartService.GetCart(customer1.getId());
        // UPDATE PRODUCT IN CART
        //
        // (Update quantity of Iphone 12 Pro to 1)
        cartService.UpdateCart(customer1.getId(), product1.getId(), 1);
        // (Update quantity of Samsung Galaxy S21 to 2)
        cartService.UpdateCart(customer1.getId(), product2.getId(), 2);
        // REMOVE PRODUCT FROM CART
        //
        // (Remove Iphone 12 Pro from cart)
        cartService.RemoveProductFromCart(customer1.getId(), product1.getId());
        // EMPTY CART
        //cartService.EmptyCart(customerId);

        // ORDER SERVICE
        //
        OrderService orderService = context.getBean(OrderService.class);
        // PLACE ORDER
        Order order1 = orderService.PlaceOrder(customer1.getId());
        // GET ORDER FOR CODE
        Order orderForCode = orderService.GetOrderForCode(order1.getCode());
        // BUY NEW PRODUCT
        // (Add 4 Xiaomi Mi 11 to cart)
        cartService.AddProductToCart(customer1.getId(), product4.getId(), 4);
        // !!!Quantity is more than stock so it will not be added to cart!!!
        cartService.AddProductToCart(customer1.getId(), product4.getId(), 10);
        // CHECKOUT CART
        Order order2 = orderService.PlaceOrder(customer1.getId());
        // GET ALL ORDERS FOR CUSTOMER
        List<Order> allOrdersForCustomer = orderService.GetAllOrdersForCustomer(customer1.getId());


        // ADD NEW CUSTOMER, ADD PRODUCT TO CART AND PLACE ORDER
        //
        Customer customer2 = customerService.AddCustomer("Feyza", "123456");
        // ADD PRODUCT TO CART
        cartService.AddProductToCart(customer2.getId(), product1.getId(), 1);
        // PLACE ORDER
        Order order3 = orderService.PlaceOrder(customer2.getId());
    }
}

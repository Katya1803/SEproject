package com.shoppink.ecommerce.service;

import com.shoppink.ecommerce.dao.CustomerRepository;
import com.shoppink.ecommerce.dto.PaymentInfo;
import com.shoppink.ecommerce.dto.Purchase;
import com.shoppink.ecommerce.dto.PurchaseResponse;
import com.shoppink.ecommerce.entity.Customer;
import com.shoppink.ecommerce.entity.Order;
import com.shoppink.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService
{
    private final CustomerRepository customerRepository;
    public CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}")String secretKey){
        this.customerRepository = customerRepository;
        // khai bao Stripe API voi secret key
        Stripe.apiKey = secretKey;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase)
    {
        // nhan thong tin order tu dto
        Order order = purchase.getOrder();
        // tao tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        // dien du lieu order voi orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);
        // dien du lieu order voi billing va shipping address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());
        // dien du lieu customer voi order
        Customer customer = purchase.getCustomer();
        // kiem tra neu ton tai customer mail
        String theEmail = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(theEmail);
        if (customerFromDB != null){
            customer = customerFromDB;
        }
        customer.add(order);
        // save to the db
        customerRepository.save(customer);
        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Shopink purchase");
        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber(){
        return UUID.randomUUID().toString();
    }

}

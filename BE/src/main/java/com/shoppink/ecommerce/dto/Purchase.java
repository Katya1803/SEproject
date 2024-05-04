package com.shoppink.ecommerce.dto;

import com.shoppink.ecommerce.entity.Address;
import com.shoppink.ecommerce.entity.Customer;
import com.shoppink.ecommerce.entity.Order;
import com.shoppink.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase
{
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}

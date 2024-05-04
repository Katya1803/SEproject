package com.shoppink.ecommerce.service;

import com.shoppink.ecommerce.dto.PaymentInfo;
import com.shoppink.ecommerce.dto.Purchase;
import com.shoppink.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService
{
    PurchaseResponse placeOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo)
        throws StripeException;
}

package shop.service;

import java.util.List; 
import shop.model.Product;
import shop.payment.PaymentMethod; 
import shop.payment.RefundablePayment; // <--- Jangan lupa import ini!
import shop.shipping.Shippable; 
import shop.shipping.ShippingService; 

public class CheckoutService {
    public long subtotal(List<Product> items) { 
        long total = 0; 
        for (Product product : items) { 
            total += product.getFinalPrice(); 
        } 
        return total; 
    } 
 
    public int totalWeight(List<Product> items) { 
        int grams = 0; 
        for (Product product : items) { 
            if (product instanceof Shippable shippable) { // a capability, not a class 
                grams += shippable.getWeightGrams(); 
            } 
        } 
        return grams; 
    } 

    public long total(List<Product> items, ShippingService shipping) {
        return subtotal(items) + shipping.calculateCost(totalWeight(items));
    } 

    public boolean checkout(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        long finalAmount = grandTotal(items, shipping, payment);
        return payment.pay(finalAmount);
    } 

    public boolean refund(RefundablePayment payment, long amount) {
        return payment.refund(amount);
    }

    public long grandTotal(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        long baseTotal = total(items, shipping);
        return baseTotal + payment.adminFee(baseTotal);
    }

}
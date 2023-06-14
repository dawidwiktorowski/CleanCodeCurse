package pl.praktycznajava.module3.valueobjects.challenge2;

import pl.praktycznajava.module3.valueobjects.challenge2.model.Currency;
import pl.praktycznajava.module3.valueobjects.challenge2.model.Order;

import java.math.BigDecimal;

public class OrderService {
    OrderRepository orderRepository;
    CurrencyConverter currencyConverter;

    public boolean hasFreeShipping(String orderId) {
        Order order = orderRepository.findBy(orderId);
        return order.hasFreeShipping(currencyConverter);
    }

    public void addDiscount(String orderId, BigDecimal discount, Currency discountCurrency) {
        Order order = orderRepository.findBy(orderId);
        Order updatedOrder = order.applyDiscount(discount, discountCurrency, currencyConverter);
        orderRepository.save(updatedOrder);
    }

    public void addPercentageDiscount(String orderId, int percentageDiscount) {
        Order order = orderRepository.findBy(orderId);
        Order updatedOrder = order.applyPercentageDiscount(percentageDiscount);
        orderRepository.save(updatedOrder);
    }
}
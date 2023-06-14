package pl.praktycznajava.module3.valueobjects.challenge2;

import pl.praktycznajava.module3.valueobjects.challenge2.model.Currency;
import pl.praktycznajava.module3.valueobjects.challenge2.model.Order;

import java.math.BigDecimal;

public class OrderService {
    public static final Currency FREE_SHIPPING_THRESHOLD_CURRENCY = Currency.of("USD");
    public static final BigDecimal FREE_SHIPPING_THRESHOLD_AMOUNT = BigDecimal.valueOf(100);
    public static final int MAX_PERCENT = 100;
    OrderRepository orderRepository;
    CurrencyConverter currencyConverter;

    public boolean hasFreeShipping(String orderId) {
        Order order = orderRepository.findBy(orderId);
        BigDecimal thresholdAmount = null;
        if (!order.getCurrency().isSameCurrency(FREE_SHIPPING_THRESHOLD_CURRENCY)) {
            thresholdAmount = currencyConverter.convertTo(FREE_SHIPPING_THRESHOLD_AMOUNT, FREE_SHIPPING_THRESHOLD_CURRENCY, order.getCurrency());
        }
        return order.hasFreeShipping(thresholdAmount, currencyConverter);
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
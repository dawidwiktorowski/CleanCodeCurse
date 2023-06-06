package pl.praktycznajava.module2.encapsulation.service;

import pl.praktycznajava.module2.encapsulation.model.Order;

import java.math.BigDecimal;

import static pl.praktycznajava.module2.encapsulation.constant.DeliveryType.EXPRESS;

public class DeliveryCostCalculator {

    private static final String POLAND = "Polska";
    private static final int HIGH_DELIVERY_COST = 30;
    private static final int LOW_DELIVERY_COST = 15;
    private static final int SHIPPING_COST_FOR_COUNTRY = 80;
    private static final int MAX_CARGO_WEIGHT = 80;
    private static final double VALUE_FOR_OVERWEIGHT = 1.1;
    private static final double TAX = 1.1;

    public BigDecimal calculateDeliveryCost(Order order) {
        double totalWeight = order.getItems().stream()
                .mapToDouble(item -> item.getWeight() * item.getQuantity())
                .sum();

        double deliveryTypeCost = order.getDeliveryType() == EXPRESS ? HIGH_DELIVERY_COST : LOW_DELIVERY_COST;
        double shippingCost = totalWeight * TAX + deliveryTypeCost;

        if (!order.getDeliveryCountry().equals(POLAND)) {
            shippingCost += SHIPPING_COST_FOR_COUNTRY;
        }
        if (totalWeight > MAX_CARGO_WEIGHT) {
            shippingCost *= VALUE_FOR_OVERWEIGHT;
        }
        return BigDecimal.valueOf(shippingCost);
    }
}

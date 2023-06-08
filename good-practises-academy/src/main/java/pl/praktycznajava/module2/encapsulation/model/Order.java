package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pl.praktycznajava.module2.encapsulation.constant.DeliveryType;
import pl.praktycznajava.module2.encapsulation.constant.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static pl.praktycznajava.module2.encapsulation.constant.DeliveryType.EXPRESS;
import static pl.praktycznajava.module2.encapsulation.constant.OrderStatus.COMPLETED;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Order {

    private static final String POLAND = "Polska";
    private static final int HIGH_DELIVERY_COST = 30;
    private static final int LOW_DELIVERY_COST = 15;
    private static final int SHIPPING_COST_FOR_COUNTRY = 80;
    private static final int MAX_CARGO_WEIGHT = 80;
    private static final double VALUE_FOR_OVERWEIGHT = 1.1;
    private static final double TAX = 1.1;

    List<OrderItem> items;
    DeliveryType deliveryType;
    OrderStatus status;
    Address deliveryAddress;
    BigDecimal totalAmount;
    BigDecimal discountAmount;
    BigDecimal deliveryCost;

    public String getDeliveryCountry() {
        return deliveryAddress.getCountry();
    }

    public void changeAddress(Address deliveryAddress) {

        if (deliveryAddress != null) {
            this.deliveryAddress = deliveryAddress;
        }
        this.deliveryCost = calculateDeliveryCost();
    }

    public void changeDeliveryType(DeliveryType deliveryType) {
        if (deliveryType != null) {
            this.deliveryType = deliveryType;
        }
        this.deliveryCost = calculateDeliveryCost();
    }

    public void addProduct(Product product, int quantity) {

        OrderItem newItem = OrderItem.of(product, quantity);
        this.items.add(newItem);

        BigDecimal totalAmount = this.totalAmount.add(newItem.calculateItemValue(quantity));

        BigDecimal discount = calculateDiscount();
        calculateDeliveryCost();

        this.totalAmount = totalAmount;

        if (discount != null) {
            this.discountAmount = discount;
        }
    }

    public void completeOrder() {
        validateAndUpdateQuantities();
        this.status = COMPLETED;
    }

    private BigDecimal calculateDeliveryCost() {
        double totalWeight = this.items.stream()
                .mapToDouble(OrderItem::calculateTotalWeight)
                .sum();

        double deliveryTypeCost = this.deliveryType == EXPRESS ? HIGH_DELIVERY_COST : LOW_DELIVERY_COST;
        double shippingCost = totalWeight * TAX + deliveryTypeCost;

        if (!this.getDeliveryCountry().equals(POLAND)) {
            shippingCost += SHIPPING_COST_FOR_COUNTRY;
        }
        if (totalWeight > MAX_CARGO_WEIGHT) {
            shippingCost *= VALUE_FOR_OVERWEIGHT;
        }
        return BigDecimal.valueOf(shippingCost);
    }

    private BigDecimal calculateDiscount() {
        BigDecimal discount = ZERO;
        if (this.totalAmount.compareTo(BigDecimal.valueOf(500)) > 0) {
            discount = this.totalAmount.multiply(BigDecimal.valueOf(0.2));
        } else if (this.totalAmount.compareTo(BigDecimal.valueOf(50)) > 0) {
            discount = this.totalAmount.multiply(BigDecimal.valueOf(0.05));
        }
        return discount;
    }

    private void validateAndUpdateQuantities() {
        for (OrderItem item : this.items) {
            item.updateProductStockQuantity();
        }
    }
}
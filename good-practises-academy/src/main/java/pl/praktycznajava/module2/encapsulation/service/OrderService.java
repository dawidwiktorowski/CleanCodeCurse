package pl.praktycznajava.module2.encapsulation.service;

import pl.praktycznajava.module2.encapsulation.constant.DeliveryType;
import pl.praktycznajava.module2.encapsulation.exception.InsufficientStockException;
import pl.praktycznajava.module2.encapsulation.exception.OrderNotFoundException;
import pl.praktycznajava.module2.encapsulation.model.*;
import pl.praktycznajava.module2.encapsulation.repository.OrderRepository;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static pl.praktycznajava.module2.encapsulation.constant.OrderStatus.COMPLETED;

public class OrderService {

    private OrderRepository orderRepository;
    private DeliveryCostCalculator deliveryCostCalculator;

    public void changeAddressTo(String orderId, Address deliveryAddress) {
        Order order = findOrderById(orderId);

        if (deliveryAddress != null) {
            order.setDeliveryAddress(deliveryAddress);
        }

        calculateAndSetDeliveryCost(order);
        orderRepository.save(order);
    }

    public void changeDeliveryType(String orderId, DeliveryType deliveryType) {
        Order order = findOrderById(orderId);
        if (deliveryType != null) {
            order.setDeliveryType(deliveryType);
        }
        calculateAndSetDeliveryCost(order);
        orderRepository.save(order);
    }

    public void addProduct(String orderId, Product product, int quantity) {
        Order order = findOrderById(orderId);

        OrderItem newItem = OrderItem.of(product, quantity);
        order.getItems().add(newItem);

        BigDecimal itemAmount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal totalAmount = order.getTotalAmount().add(itemAmount);

        BigDecimal discount = calculateDiscount(totalAmount);
        calculateAndSetDeliveryCost(order);

        order.setTotalAmount(totalAmount);

        if (discount != null) {
            order.setDiscountAmount(discount);
        }

        orderRepository.save(order);
    }

    public void completeOrder(String orderId) {
        Order order = findOrderById(orderId);
        validateAndUpdateQuantities(order);
        order.setStatus(COMPLETED);
        orderRepository.save(order);
    }

    private BigDecimal calculateDiscount(BigDecimal totalAmount) {
        BigDecimal discount = ZERO;
        if (totalAmount.compareTo(BigDecimal.valueOf(500)) > 0) {
            discount = totalAmount.multiply(BigDecimal.valueOf(0.2));
        } else if (totalAmount.compareTo(BigDecimal.valueOf(50)) > 0) {
            discount = totalAmount.multiply(BigDecimal.valueOf(0.05));
        }
        return discount;
    }

    private Order findOrderById(String orderId) {
        Order order = orderRepository.findBy(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found");
        }
        return order;
    }

    private void calculateAndSetDeliveryCost(Order order) {
        BigDecimal deliveryCost = deliveryCostCalculator.calculateDeliveryCost(order);
        if (deliveryCost != null) {
            order.setDeliveryCost(deliveryCost);
        }
    }

    private static void validateAndUpdateQuantities(Order order) {
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            if (product.getStockQuantity() < quantity) {
                throw new InsufficientStockException(product, quantity);
            }
            product.setStockQuantity(product.getStockQuantity() - quantity);
        }
    }
}

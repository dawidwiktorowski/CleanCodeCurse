package pl.praktycznajava.module2.encapsulation.service;

import pl.praktycznajava.module2.encapsulation.constant.DeliveryType;
import pl.praktycznajava.module2.encapsulation.exception.OrderNotFoundException;
import pl.praktycznajava.module2.encapsulation.model.Address;
import pl.praktycznajava.module2.encapsulation.model.Order;
import pl.praktycznajava.module2.encapsulation.model.Product;
import pl.praktycznajava.module2.encapsulation.repository.OrderRepository;

public class OrderService {

    private OrderRepository orderRepository;

    public void changeAddressTo(String orderId, Address deliveryAddress) {
        Order order = findOrderById(orderId);
        order.changeAddress(deliveryAddress);
        orderRepository.save(order);
    }

    public void changeDeliveryType(String orderId, DeliveryType deliveryType) {
        Order order = findOrderById(orderId);
        order.changeDeliveryType(deliveryType);
        orderRepository.save(order);
    }

    public void addProduct(String orderId, Product product, int quantity) {
        Order order = findOrderById(orderId);
        order.addProduct(product, quantity);
        orderRepository.save(order);
    }

    public void completeOrder(String orderId) {
        Order order = findOrderById(orderId);
        order.completeOrder();
        orderRepository.save(order);
    }

    private Order findOrderById(String orderId) {
        Order order = orderRepository.findBy(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found for id: " + orderId);
        }
        return order;
    }
}

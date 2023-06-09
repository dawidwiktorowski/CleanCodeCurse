package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pl.praktycznajava.module2.encapsulation.exception.InsufficientStockException;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Product {

    private String name;
    private BigDecimal price;
    private int stockQuantity;
    private double weight;

    public BigDecimal calculateAmount(int quantity) {
        return this.price.multiply(BigDecimal.valueOf(quantity));
    }

    public void updateStockQuantity(int quantity) {
        if (stockQuantity < quantity) {
            throw new InsufficientStockException(this, quantity);
        }
        stockQuantity -= quantity;
    }
}

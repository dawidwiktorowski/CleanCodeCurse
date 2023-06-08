package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class OrderItem {

    private Product product;
    private int quantity;

    public BigDecimal calculateItemValue(int quantity) {
        return product.calculateAmount(quantity);
    }

    public void updateProductStockQuantity() {
        product.updateStockQuantity(quantity);
    }

    public double calculateTotalWeight(){
        return product.getWeight() * quantity;
    }

}

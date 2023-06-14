package pl.praktycznajava.module3.valueobjects.challenge2.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pl.praktycznajava.module3.valueobjects.challenge2.CurrencyConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public final class Order {

    public static final Currency FREE_SHIPPING_THRESHOLD_CURRENCY = Currency.of("USD");
    public static final BigDecimal FREE_SHIPPING_THRESHOLD_AMOUNT = BigDecimal.valueOf(100);
    public static final int MAX_PERCENT = 100;

    List<OrderItem> items;
    BigDecimal totalAmount;
    Currency currency;

    public boolean hasFreeShipping(CurrencyConverter currencyConverter) {
        if(!currency.isSameCurrency(FREE_SHIPPING_THRESHOLD_CURRENCY)) {
            BigDecimal convertedAmount = currencyConverter.convertTo(totalAmount, currency, FREE_SHIPPING_THRESHOLD_CURRENCY);
            return convertedAmount.compareTo(FREE_SHIPPING_THRESHOLD_AMOUNT) > 0;
        } else {
            return totalAmount.compareTo(FREE_SHIPPING_THRESHOLD_AMOUNT) > 0;
        }
    }

    public Order applyDiscount(BigDecimal discount, Currency discountCurrency, CurrencyConverter currencyConverter) {
        BigDecimal convertedAmount = currencyConverter.convertTo(discount, discountCurrency, currency);
        BigDecimal discountedAmount = totalAmount.subtract(convertedAmount);
        return new Order(items, discountedAmount, currency);
    }

    public Order applyPercentageDiscount(int percentageDiscount) {
        BigDecimal discountAmount = totalAmount.multiply(BigDecimal.valueOf(percentageDiscount))
                .divide(BigDecimal.valueOf(MAX_PERCENT), RoundingMode.HALF_UP);
        BigDecimal discountedAmount = totalAmount.subtract(discountAmount);
        return new Order(items, discountedAmount, currency);
    }
}
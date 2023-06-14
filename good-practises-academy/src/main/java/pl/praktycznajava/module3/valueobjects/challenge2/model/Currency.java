package pl.praktycznajava.module3.valueobjects.challenge2.model;

import lombok.Value;
@Value
public class Currency {
    String code;

    private Currency(String code) {
        if (code == null){
            throw new IllegalArgumentException("Invalid code");
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Currency of(String code) {
        return new Currency(code);
    }

    public boolean isSameCurrency(Currency otherCurrency) {
        return code.equals(otherCurrency.getCode());
    }
}
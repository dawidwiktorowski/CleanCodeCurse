package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Value(staticConstructor = "of")
public class Address {
    String street;
    String postalCode;
    String city;
    String country;
}

package pl.praktycznajava.module3.valueobjects.challenge1;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
public class User {

    public static final int ADULT_AGE = 18;

    String id;
    String name;
    String surname;
    LocalDate dateOfBirth;
    UserAddress userAddress;

    LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();

    public boolean isTodayBirthday(){
        return currentDate.getMonth() == dateOfBirth.getMonth() && currentDate.getDayOfMonth() == dateOfBirth.getDayOfMonth();
    }
    public String getFormattedAddress() {
        return userAddress.getFormattedAddress();
    }

    public boolean isAdult(){
        return currentDate.getYear() - dateOfBirth.getYear() > ADULT_AGE;

    }
}
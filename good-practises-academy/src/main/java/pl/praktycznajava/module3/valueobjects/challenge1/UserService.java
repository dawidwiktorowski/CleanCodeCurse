package pl.praktycznajava.module3.valueobjects.challenge1;

public class UserService {

    UserRepository userRepository;

    public boolean isBirthdayToday(String userId) {
        User user = userRepository.findBy(userId);
        return user.isTodayBirthday();
    }

    public boolean isAdult(String userId) {
        User user = userRepository.findBy(userId);
        return user.isAdult();
    }

    public String getFormattedAddress(String userId) {
        User user = userRepository.findBy(userId);
        return user.getFormattedAddress();
    }

}
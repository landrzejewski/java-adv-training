package pl.training.processor;

import pl.training.processor.UserJsonMapper;

@Log
@Configurable
public class TestService {

    public static void main(String[] args) {
        var user = new User("Jan", "Kowalski", "test@training.pl", 23);
        var mapper = new UserJsonMapper();
        System.out.println(mapper.toJson(user));
    }

}

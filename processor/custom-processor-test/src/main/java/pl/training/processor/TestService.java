package pl.training.processor;

import static pl.training.processor.UserJsonMapper.toJson;

@Log
@Configurable
public class TestService {

    public static void main(String[] args) {
        var user = new User("Jan", "Kowalski", "test@training.pl", 23);
        System.out.println(toJson(user));
    }

}

package pl.training.processor;

@JsonType
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private int age;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

}

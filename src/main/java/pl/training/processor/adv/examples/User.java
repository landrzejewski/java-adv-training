package pl.training.processor.adv.examples;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {

    private String firstName;
    @NotBlank
    private String lastName;
    @Pattern(regexp = ".*@.*")
    private String email;
    @Min(18)
    private int age;

}

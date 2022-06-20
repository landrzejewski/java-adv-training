package pl.training.processor.adv.examples;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Order {

    private Long id;
    private List<Long> products;

}

/*
@Value
public class Order {

    Long id;
    List<Long> products;

}*/

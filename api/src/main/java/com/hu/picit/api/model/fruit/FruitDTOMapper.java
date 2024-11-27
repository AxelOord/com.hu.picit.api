package main.java.com.hu.picit.api.model.fruit;

import java.util.function.Function;

public class FruitDTOMapper implements Function<Fruit, FruitDTO> {
    @Override
    public FruitDTO apply(Fruit fruit) {
        return new FruitDTO(
            fruit.getId(), 
            fruit.getName(), 
            fruit.getQuantity(), 
            fruit.getPrice(), 
            fruit.getImg(), 
            fruit.getCountryOfOrigin(),
            fruit.getCategory().getName()
            );
    }
}

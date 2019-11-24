package ru.otus.annotations;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor(force = true)

public class Drink {
    private String sort;
    private int sugar;
    private String origin;
    private int price;

}








package ru.otus.annotations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

public class DrinkTest {
    private static Drink drink;

    @Before
    public void setUp() {
        this.drink = Drink.builder()
                .sort("red")
                .price(200)
                .sugar(3)
                .origin("USA")
                .build();
        System.out.println("Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " has been worked");
    }

    @Test
    @DisplayName("GetOrigin() work correctly")
    public void GetOrigin(){
        this.drink = Drink.builder()
                .sort("red")
                .price(200)
                .sugar(3)
                .origin("USA")
                .build();
        assertThat(drink.getOrigin()).isEqualTo("USA");
        System.out.println("Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " has been worked");
    }

    @Test
    @DisplayName("SetOrigin() work correctly")
    public void SetOriginTest(){
        drink.setOrigin("Urugwai");
        assertThat(drink.getOrigin())
                .isEqualTo("Urugwai");
        System.out.println("Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " has been worked");
    }



    @Test
    @DisplayName("GetSort() work correctly")
    public void GetSortTest(){
        this.drink = Drink.builder()
                .sort("red")
                .price(200)
                .sugar(3)
                .origin("USA")
                .build();
        assertThat(drink.getSort())
                .isEqualTo("red");
        System.out.println("Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " has been worked");
    }

    @Test
    @DisplayName("GetSugar() work correctly")
    public void GetSugarTest(){
        this.drink = Drink.builder()
                .sort("white")
                .price(200)
                .sugar(0)
                .origin("USA")
                .build();
        assertThat(drink.getSugar())
                .isEqualTo(3);
        System.out.println("Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " has been worked");
    }

    @Test
    @DisplayName("GetPrice() work correctly")
    public void GetPrice(){
        this.drink = Drink.builder()
                .sort("red")
                .price(200)
                .sugar(3)
                .origin("USA")
                .build();
        assertThat(drink.getPrice())
                .isEqualTo(200);
        System.out.println("Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " has been worked");
    }

    @After
    public void tearDown() {
        System.out.println("Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " has been worked");
    }
}
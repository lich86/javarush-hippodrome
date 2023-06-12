import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    public void constructor_Should_ThrowException_When_NameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void constructor_ReturnsCorrectMessageInException_When_NameIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void constructor_Should_ThrowException_When_NameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void constructor_ReturnsCorrectMessageInException_When_NameIsEmpty() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses_Should_ReturnCorrectListOfHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("name" + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move_Should_BeCalledForAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        new Hippodrome(horses).move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    public void getWinner_Should_ReturnTheHorseWithBiggestDistance() {
        Horse horse1 = new Horse("Эгоист", 12, 200);
        Horse horse2 = new Horse("Чайка", 10, 300);
        Horse horse3 = new Horse("Либерал", 13, 500);
        Horse horse4 = new Horse("Эльбрус", 11, 350);
        Horse horse5 = new Horse("Лидер", 12, 250);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4, horse5));

        assertEquals(horse3, hippodrome.getWinner());
    }

}
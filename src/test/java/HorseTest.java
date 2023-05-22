import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {
    @Test
    public void Constructor_Should_ThrowException_When_NameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 12, 200));
    }

    @Test
    public void Constructor_ReturnsCorrectMessageInException_When_NameIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 12, 200));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings ={"", " ", "\t", "\r", "\n"})
    public void Constructor_Should_ThrowException_When_NameIsEmpty(String argument) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 12, 200));
    }

    @ParameterizedTest
    @ValueSource(strings ={"", " ", "\t", "\r", "\n"})
    public void Constructor_ReturnsCorrectMessageInException_When_NameIsEmpty(String argument) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(argument, 12, 200));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void Constructor_Should_ThrowException_When_SpeedIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Эгоист", -12, 200));
    }

    @Test
    public void Constructor_ReturnsCorrectMessageInException_When_SpeedIsNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Эгоист", -12, 200));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void Constructor_Should_ThrowException_When_DistanceIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Эгоист", 12, -200));
    }

    @Test
    public void Constructor_ReturnsCorrectMessageInException_When_DistanceIsNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Эгоист", 12, -200));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName_Should_ReturnFirstParameterFromConstructor() {
        String name = "Эгоист";
        assertEquals(name, new Horse(name, 12, 200).getName());
    }

    @Test
    public void getSpeed_Should_ReturnSecondParameterFromConstructor() {
        int speed = 12;
        assertEquals(speed, new Horse("Эгоист", speed, 200).getSpeed());
    }

    @Test
    public void getSpeed_Should_ReturnThirdParameterFromConstructor() {
        int distance = 12;
        assertEquals(distance, new Horse("Эгоист", 12, distance).getDistance());
    }

    @Test
    public void getSpeed_Should_ReturnZero_When_ConstructorHasTwoParameters() {
        assertEquals(0, new Horse("Эгоист", 12).getDistance());
    }

    @Test
    public void move_Should_Call_getRandomDouble_WithCorrectParameters() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Эгоист", 12).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }

    }

    @ParameterizedTest
    @ValueSource(doubles ={2.3, 1.7, 777.6, 12.555})
    public void move_Should_UseCorrectFormula(double random) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse horse = new Horse("Эгоист", 12, 200);
            horse.move();
            double expectedResult = 200 + 12 * random;
            double actualResult = horse.getDistance();
            assertEquals(expectedResult, actualResult);
        }
    }


}
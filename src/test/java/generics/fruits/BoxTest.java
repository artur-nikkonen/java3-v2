package generics.fruits;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void getWeightAppleBox() {
        Box<Apple> box = new Box<>();

        box.addFruit(new Apple());
        box.addFruit(new Apple());
        box.addFruit(new Apple());

        assertEquals(box.getWeight(), 3.0, 0.001);
    }

    @Test
    void getWeightOrangeBox() {
        Box<Orange> box = new Box<>();

        box.addFruit(new Orange());
        box.addFruit(new Orange());
        box.addFruit(new Orange());

        assertEquals(box.getWeight(), 4.5, 0.001);
    }

    @Test
    void compareAppleWeight() {
        Box<Apple> box = new Box<>();

        box.addFruit(new Apple());
        box.addFruit(new Apple());
        box.addFruit(new Apple());

        assertTrue(box.compare(3.0));
    }
    @Test
    void compareOrangeWeight() {
        Box<Orange> box = new Box<>();

        box.addFruit(new Orange());
        box.addFruit(new Orange());
        box.addFruit(new Orange());

        assertTrue(box.compare(4.5));
    }

    @Test
    void compareAppleWithOrange() {
        Box<Apple> box1 = new Box<>();

        box1.addFruit(new Apple());
        box1.addFruit(new Apple());
        box1.addFruit(new Apple());

        Box<Orange> box2 = new Box<>();

        box2.addFruit(new Orange());
        box2.addFruit(new Orange());

        assertTrue(box1.compare(box2));
        assertTrue(box2.compare(box1));

    }

    @Test
    void takeAwayFruits() {
        Box<Apple> box = new Box<>();

        box.addFruit(new Apple());
        box.addFruit(new Apple());
        box.addFruit(new Apple());

        List<Apple> list = box.takeAwayFruits();

        assertEquals(list.size(),3);
        assertEquals(box.getFruitsCount(),0);
    }

    @Test
    void pourAllFruits() {

        Box<Apple> box1 = new Box<>();

        box1.addFruit(new Apple());
        box1.addFruit(new Apple());
        box1.addFruit(new Apple());

        Box<Apple> box2 = new Box<>();

        box2.addFruit(new Apple());
        box2.addFruit(new Apple());

        box1.pourAllFruits(box2);

        assertEquals(box1.getFruitsCount(),5);
        assertEquals(box2.getFruitsCount(),0);

    }

    @Test
    void pourAllFruitsFromNull() {

        Box<Apple> box1 = new Box<>();

        box1.addFruit(new Apple());
        box1.addFruit(new Apple());
        box1.addFruit(new Apple());

        box1.pourAllFruits(null);

        assertEquals(box1.getFruitsCount(),3);
    }
}
package generics.fruits;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit & IPackable> {

    final double WEIGHT_BOUND = 0.0001;

    List<T> list = new ArrayList<>();

    public double getWeight() {
        return list.stream().mapToDouble(x -> x.getWeight()).sum();
    }

    public int getFruitsCount() {
        return list.size();
    }

    //compare box weight with some number
    public boolean compare(double weight) {
        return Math.abs(getWeight() - weight) < WEIGHT_BOUND;
    }

    //compare box weight with other box weight
    public boolean compare(Box<?> box) {
        if (box == null) return false;
        return compare(box.getWeight());
    }

    public void addFruit(T fruit) {
        list.add(fruit);
    }

    public List<T> takeAwayFruits() {
        List<T> tmpList = list;
        list = new ArrayList<>();
        return tmpList;
    }

    public void addAllFruits(List<T> fruits) {
        list.addAll(fruits);
    }

    public void pourAllFruits(Box<T> box) {
        if(box == null) return;
        addAllFruits(box.takeAwayFruits());
    }
}

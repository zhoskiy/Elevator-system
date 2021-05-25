package domain;

import lombok.Getter;

@Getter
public class Human {
    private final int weight;
    private final int actualFloor;

    public Human(int weight, int actualFloor) {
        this.weight = weight;
        this.actualFloor = actualFloor;
    }

    @Override
    public String toString() {
        return "Human: w" + weight +", f" + actualFloor;
    }
}

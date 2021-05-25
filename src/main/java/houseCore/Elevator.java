package houseCore;

import domain.Action;
import domain.Human;
import information.Statistics;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Log
public class Elevator extends Thread {
    private final int maxWeight;
    private final int maxFloor;
    private final int doorOpenOrCloseSpeed;
    private final int speed;
    private final List<Human> humans = new ArrayList<>();
    private final HashMap<Integer, List<Integer>> whereFromFloorMap = new HashMap<>();
    private int currentFloor = 1;
    private Action action = Action.NOTHING;
    private final Statistics statistics;

    public Elevator(int maxWeight, int maxFloor, int doorOpenAndCloseSpeed, int speed, Statistics statistics) {
        this.maxWeight = maxWeight;
        this.maxFloor = maxFloor;
        this.doorOpenOrCloseSpeed = doorOpenAndCloseSpeed;
        this.speed = speed;
        this.statistics = statistics;
        configWhereFromFloorMaP();
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            if (action != Action.NOTHING) {
                log.info(this.getName() + " have  " + humans + " on floor " + currentFloor);
                move();
                waitingToOpenDoors();
                dropPeopleOff();
                waitingToCloseDoors();
            } else {
                sleep(100);
                log.info(this.getName() + " action = " + action);
            }

        }
    }

    private void waitingToOpenDoors() {
        waiting();
    }

    private void waitingToCloseDoors() {
        waiting();
    }

    @SneakyThrows
    private void waiting() {
        sleep(doorOpenOrCloseSpeed * 50L);
    }

    @SneakyThrows
    private void move() {
        sleep(speed * 100L);
        if (action == Action.UP && currentFloor < maxFloor) {
            currentFloor += 1;
        } else if (action == Action.DOWN && currentFloor > 1) {
            currentFloor -= 1;
        }
    }

    public boolean addHuman(Human human) {
        int currentWeight = humans.stream().mapToInt(Human::getWeight).sum();
        if (maxWeight - currentWeight >= human.getWeight()) {
            humans.add(human);
            return true;
        } else {
            return false;
        }
    }

    public void dropPeopleOff() {
        if (humans.removeIf(x -> x.getActualFloor() == currentFloor)) {
            updateStatistics();
        }
    }

    private void updateStatistics() {
        log.info(getName()+" transport humans on floor №" + currentFloor + " from " + whereFromFloorMap.get(currentFloor) + " floors");
        whereFromFloorMap.get(currentFloor).forEach(f -> statistics.update(getName(), f, currentFloor));
        whereFromFloorMap.get(currentFloor).clear();
    }

    private void configWhereFromFloorMaP() {
        for (int i = 1; i <= maxFloor; i++) {
            whereFromFloorMap.put(i, new ArrayList<>());
        }
    }

    public void addFloorForStatistics(int floor, int floorNumber) {
        whereFromFloorMap.get(floorNumber).add(floor);
    }

    public boolean isEmpty() {
        return humans.isEmpty();
    }
}

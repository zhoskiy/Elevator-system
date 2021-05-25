import houseCore.Elevator;
import houseCore.Floor;
import domain.Human;
import information.Statistics;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Setter
@Getter
@Log
public class House {
    private final List<Floor> floors = new ArrayList<>();
    private final List<Elevator> elevators = new ArrayList<>();
    private final int floorsCount;
    private final int intensityOfPeopleGeneration;
    private final Statistics statistics;
    private boolean start = true;

    public House(int floorsCount, int countElevators, int intensityOfPeopleGeneration) {
        this.floorsCount = floorsCount;
        this.intensityOfPeopleGeneration = intensityOfPeopleGeneration;
        statistics = new Statistics(floorsCount);
        createElevators(countElevators);
        createFloors();
    }


    private void createFloors() {
        for (int i = 1; i <= floorsCount; i++) {
            Floor floor = new Floor(i, floorsCount, elevators);
            floor.setDaemon(true);
            floor.start();
            floors.add(floor);
        }
        log.info("Floors created");
    }

    private void createElevators(int count) {
        for (int i = 0; i < count; i++) {
            int randomMaxWeight = (int) (Math.random() * 5) + 10;
            int doorOpenCloseSpeed = (int) (Math.random() * 5) + 10;
            int speed = (int) (Math.random() * 50) + 1;
            statistics.createNewStatisticsForElevator("Elevator-" + i);
            Elevator elevator = new Elevator(randomMaxWeight, floorsCount, doorOpenCloseSpeed, speed, statistics);
            elevator.setName("Elevator-" + i);
            elevator.setDaemon(true);
            elevator.start();
            elevators.add(elevator);
        }
        log.info("Elevators created");
    }

    @SneakyThrows
    public void createRandomHuman() {
        int i =0;
        while (i<50) {

            sleep(intensityOfPeopleGeneration);
            int randomWeight = (int) (Math.random() * 20) + 10;
            int randomCurrentFloor = (int) (Math.random() * floorsCount);
            int randomFloor = (int) (Math.random() * floorsCount) + 1;
            while (randomFloor - 1 == randomCurrentFloor) {
                randomFloor = (int) (Math.random() * floorsCount) + 1;
            }
            Human human = new Human(randomWeight, randomFloor);
            floors.get(randomCurrentFloor).addHumanInQueue(human);
            log.info(human.toString()+" created on floor №" + (randomCurrentFloor + 1));
            i++;
        }
    }
}

package domain;

import houseCore.Elevator;
import houseCore.Floor;
import information.Statistics;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {
    private static Floor floor;

    @BeforeAll
    static void beforeAll() {
        Statistics statistics = new Statistics(3);
        statistics.createNewStatisticsForElevator("Elevator-0");
        statistics.createNewStatisticsForElevator("Elevator-1");
        Elevator elevator1 = new Elevator(1, 3, 1, 1, statistics);
        Elevator elevator2 = new Elevator(2, 3, 1, 2, statistics);
        floor = new Floor(1, 3, List.of(elevator1, elevator2));

        floor.addHumanInQueue(new Human(1, 2));
        floor.addHumanInQueue(new Human(1, 2));
    }

    @Test
    void setElevatorAction() {

        floor.setElevatorAction();
        long actualValue = floor.getElevators().stream().filter(e -> e.getAction() == Action.NOTHING).count();
        assertEquals(1, actualValue);
        floor.setElevatorAction();
        actualValue = floor.getElevators().stream().filter(e -> e.getAction() == Action.NOTHING).count();
        assertEquals(0, actualValue);
    }

    @SneakyThrows
    @Test
    void elevatorCallCheck() {
        floor.start();
        Thread.sleep(5000);
        assertFalse(floor.isButtonDown());
        assertTrue(floor.isButtonUp());
    }

    @Test
    void addHumanInQueue() {
        Floor floor = new Floor(2, 3, null);
        Human human1 = new Human(1, 1);
        Human human2 = new Human(1, 3);
        floor.addHumanInQueue(human1);
        floor.addHumanInQueue(human2);
        assertEquals(1, floor.getQueueDown().size());
        assertEquals(1, floor.getQueueUp().size());
        assertEquals(human2, floor.getQueueUp().getFirst());
        assertEquals(human1, floor.getQueueDown().getFirst());
    }
}
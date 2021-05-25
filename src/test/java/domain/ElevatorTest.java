package domain;

import houseCore.Elevator;
import information.Statistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElevatorTest {

    @org.junit.jupiter.api.Test
    void dropPeopleOff() {
        Statistics statistics = new Statistics(3);
        statistics.createNewStatisticsForElevator("Elevator-0");
        Elevator elevator = new Elevator(10, 10, 10, 10, statistics);
        elevator.setName("Elevator-0");
        elevator.addHuman(new Human(1, 1));
        elevator.addHuman(new Human(1, 1));
        elevator.addHuman(new Human(1, 1));
        elevator.addFloorForStatistics(2, 1);
        elevator.addFloorForStatistics(2, 1);
        elevator.addFloorForStatistics(3, 1);
        elevator.dropPeopleOff();
        System.out.println(statistics.toString());
        assertTrue(elevator.isEmpty());
    }

    @Test
    void addHuman() {
        Elevator elevator = new Elevator(10, 10, 10, 10, null);
        elevator.addHuman(new Human(5, 1));
        elevator.addHuman(new Human(5, 2));
        elevator.addHuman(new Human(4, 2));
        assertEquals(2, elevator.getHumans().size());
    }
}
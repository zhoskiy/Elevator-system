import domain.Human;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class HouseTest {

    @SneakyThrows
    @Test
    void fullSystemTest() {
        House house = new House(5, 1, 300);
        Human human1 = new Human(1, 4);
        Human human2 = new Human(1, 2);
        Human human3 = new Human(2, 2);
        house.getFloors().get(0).addHumanInQueue(human1);
        house.getFloors().get(3).addHumanInQueue(human2);
        house.getFloors().get(3).addHumanInQueue(human3);
        sleep(20000);
        int actual1 = house.getStatistics().getPeopleTransportationStatistics().get("Elevator-0").get(1).get(4);
        int actual2 = house.getStatistics().getPeopleTransportationStatistics().get("Elevator-0").get(4).get(2);
        assertEquals(1, actual1);
        assertEquals(2, actual2);
    }
}
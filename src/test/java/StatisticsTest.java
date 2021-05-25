import org.junit.jupiter.api.Test;
import information.Statistics;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {

    @Test
    void update() {
        Statistics statistics = new Statistics(8);
        statistics.createNewStatisticsForElevator("Elevator-0");
        statistics.update("Elevator-0", 3, 5);
        statistics.update("Elevator-0", 3, 5);
        int actual = statistics.getPeopleTransportationStatistics().get("Elevator-0").get(3).get(5);
        System.out.println(statistics);
        assertEquals(2, actual);
    }

    @Test
    void testToString() {
        Statistics statistics = new Statistics(8);
        for (int i = 0; i < 5; i++) {
            statistics.createNewStatisticsForElevator("Elevator-" + i);
        }
        System.out.println(statistics.toString());
    }
}
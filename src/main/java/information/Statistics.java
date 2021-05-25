package information;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private final int countFloor;
    private final Map<String, Map<Integer, Map<Integer, Integer>>> peopleTransportationStatistics = new HashMap<>();

    public Statistics(int countFloor) {
        this.countFloor = countFloor;
    }

    public void createNewStatisticsForElevator(String elevatorName) {
        Map<Integer, Map<Integer, Integer>> elevatorStatistics = new HashMap<>();
        for (int i = 1; i <= countFloor; i++) {
            Map<Integer, Integer> floorStatistics = new HashMap<>();
            for (int j = 1; j <= countFloor; j++) {
                if (i != j) {
                    floorStatistics.put(j, 0);
                }
                elevatorStatistics.put(i, floorStatistics);
            }
        }
        peopleTransportationStatistics.put(elevatorName, elevatorStatistics);
    }

    public void update(String elevatorName, int currentFloor, int actualFloor) {
        int oldValue = peopleTransportationStatistics.get(elevatorName).get(currentFloor).get(actualFloor);
        oldValue = ++oldValue;
        peopleTransportationStatistics.get(elevatorName).get(currentFloor).replace(actualFloor, oldValue);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%50s", "Transportation statistics\n\n"));
        Set<String> elevatorSet = peopleTransportationStatistics.keySet();
        for (String elevator : elevatorSet) {
            stringBuilder.append((elevator + " :\n").toUpperCase());
            Set<Integer> floorSet = peopleTransportationStatistics.get(elevator).keySet();
            for (Integer currentFloor : floorSet) {
                stringBuilder.append(String.format("%25s №%1d :\n", "From the floor", currentFloor));
                Set<Integer> actualFloorSet = peopleTransportationStatistics.get(elevator).get(currentFloor).keySet();
                for (Integer actualFloor : actualFloorSet) {
                    int value = peopleTransportationStatistics.get(elevator).get(currentFloor).get(actualFloor);
                    stringBuilder.append(String.format("%40s №%d count people is %d\n", "on the floor", actualFloor, value));
                }
            }
            stringBuilder.append("\n\n");
        }
        return stringBuilder.toString();
    }

    public Map<String, Map<Integer, Map<Integer, Integer>>> getPeopleTransportationStatistics() {
        return peopleTransportationStatistics;
    }
}

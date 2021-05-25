package houseCore;

import domain.Action;
import domain.Human;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Getter
@Log
public class Floor extends Thread {
    private final int number;
    private final int maxFloor;
    private final LinkedList<Human> queueUp = new LinkedList<>();
    private final LinkedList<Human> queueDown = new LinkedList<>();
    private final List<Elevator> elevators;
    private boolean buttonUp = false;
    private boolean buttonDown = false;


    public Floor(int number, int maxFloor, List<Elevator> elevators) {
        super("Floor " + number);
        this.number = number;
        this.maxFloor = maxFloor;
        this.elevators = elevators;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            sleep(250);
            log.info(String.format("%s, count people up - %2d, dawn - %2d\n", getName(), queueUp.size(), queueDown.size()));

            elevatorCallCheck();
            elevatorCall();
            addPeopleInElevator();
        }
    }

    private void elevatorCallCheck() {
        log.info(getName() + " check buttons");
        buttonUp = !queueUp.isEmpty();
        buttonDown = !queueDown.isEmpty();
    }

    private void elevatorCall() {
        if (buttonUp) {
            setElevatorAction();
        }
        if (buttonDown) {
            setElevatorAction();
        }
    }

    public void setElevatorAction() {
        Optional<Elevator> elevator = elevators.stream().filter(e -> e.getAction() == Action.NOTHING).findFirst();
        if (elevator.isPresent()) {
            log.info(getName()+" call elevator");
            if (elevator.get().getCurrentFloor() < number) {
                elevator.get().setAction(Action.UP);
            } else {
                elevator.get().setAction(Action.DOWN);
            }
        }
    }

    private void addPeopleInElevator() {
        Elevator elevator = getElevatorWhichArrived();
        if (elevator != null) {
            if (buttonUp && (elevator.isEmpty() || (elevator.getAction() == Action.NOTHING || elevator.getAction() == Action.UP))) {
                while (!queueUp.isEmpty() && elevator.addHuman(getFirstHumanUp())) {
                    elevator.addFloorForStatistics(number, getFirstHumanUp().getActualFloor());
                    deleteHumanUp();
                    elevator.setAction(Action.UP);
                }
                log.info(elevator.getName() + " got humans queue up on " + getName());
            }
            if (buttonDown && (elevator.isEmpty() || (elevator.getAction() == Action.NOTHING || elevator.getAction() == Action.DOWN))) {
                while (!queueDown.isEmpty() && elevator.addHuman(getFirstHumanDown())) {
                    elevator.addFloorForStatistics(number, getFirstHumanDown().getActualFloor());
                    deleteHumanDown();
                    elevator.setAction(Action.DOWN);
                }
                log.info(elevator.getName() + " got humans queue down on " + getName());
            }
        }
    }

    private Elevator getElevatorWhichArrived() {
        return elevators.stream().filter(e -> e.getCurrentFloor() == number).findFirst().orElse(null);
    }

    private synchronized Human getFirstHumanUp() {
        return queueUp.getFirst();
    }

    private synchronized Human getFirstHumanDown() {
        return queueDown.getFirst();
    }

    private void deleteHumanUp() {
        queueUp.removeFirst();
        elevatorCallCheck();
    }

    private void deleteHumanDown() {
        queueDown.removeFirst();
        elevatorCallCheck();
    }

    public void addHumanInQueue(Human human) {
        if (human.getActualFloor() < number) {
            queueDown.add(human);
        } else {
            queueUp.add(human);
        }
    }
}

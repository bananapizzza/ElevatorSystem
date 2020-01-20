import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Elevator extends Thread {
    boolean[] destinationFloors;
    Integer currentFloor = 0;
    Direction direction = Direction.NONE;
    final public int MOVING_TIME = 1000;
    final public int DOOR_OPEN_CLOSE_TIME = 2000;
    final public int DESTINATION_CHECK_TIME = 1000;
    List<Integer> possibleFloors;
    final ElevatorState idleState = new IdleState(this);
    final ElevatorState movingState = new MovingState(this);
    final ElevatorState outOfServiceState = new OutOfServiceState(this);
    Thread movingElevatorThread = new Thread(this::moveElevator);
    ElevatorState currentState = idleState;

    Elevator(List<Integer> possibleFloors) {
        this.possibleFloors = possibleFloors;
        this.destinationFloors = new boolean[possibleFloors.size()];
    }

    public void run(){
        movingElevatorThread.start();
    }

    boolean isTheFloorPossibleToGo(int floor){
        for(int i : possibleFloors){
            if(i == floor){
                return true;
            }
        }
        return false;
    }

    boolean selectFloor(int floor) {
        try {
            return currentState.selectFloor(floor);
        } catch (InvalidCaseException e) {
            e.printStackTrace();
        } catch (NoDestinationException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean openDoor() {
        return currentState.openDoor();
    }

    boolean closeDoor() {
        return currentState.closeDoor();
    }

    boolean callEmergency() {
        System.out.println("Calling to emergency service...");
        return true;
    }

    void moveElevator() {
        while(true) {
            waiting(DESTINATION_CHECK_TIME);
            while (destinationExist()) {
                try {
                    int destinationFloor = getClosestDestinationFloor();
                    this.currentState = movingState;
                    if (destinationFloor > currentFloor) {
                        setDirection(Direction.GOING_UP);
                    } else if (destinationFloor < currentFloor) {
                        setDirection(Direction.GOING_DOWN);
                    }

                    if (this.direction.equals(Direction.GOING_UP)) {
                        while (currentFloor < destinationFloor) {
                            waiting(MOVING_TIME);
                            currentFloor++;
                            System.out.println("Current floor is " + currentFloor);
                            if (destinationFloors[getIndex(currentFloor)]) {
                                break;
                            }
                        }
                    } else if (this.direction.equals(Direction.GOING_DOWN)) {
                        while (currentFloor > destinationFloor) {
                            waiting(MOVING_TIME);
                            currentFloor--;
                            System.out.println("Current floor is " + currentFloor);
                            if (destinationFloors[getIndex(currentFloor)]) {
                                break;
                            }
                        }
                    } else {
                        throw new InvalidCaseException("The direction cannot be NONE while moving");
                    }

                    System.out.println("Arrived to " + currentFloor + " floor.");
                    stopElevator();
                } catch (NoDestinationException e) {
                    e.printStackTrace();
                } catch (InvalidCaseException e) {
                    e.printStackTrace();
                }
            }
            this.direction = Direction.NONE;
        }
    }

    void waiting(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    boolean destinationExist() {
        for (int i = 0; i < destinationFloors.length; i++) {
            if (destinationFloors[i]) {
                return true;
            }
        }
        return false;
    }

    int getIndex(int floor) throws InvalidCaseException {
        for(int i = 0; i<possibleFloors.size(); i++){
            if(possibleFloors.get(i) == floor){
                return i;
            }
        }
        throw new InvalidCaseException("The floor is not in the possibleFloors list.");
    }

    int getClosestDestinationFloor() throws NoDestinationException, InvalidCaseException {
        if (this.direction.equals(Direction.GOING_UP)) {
            for (int i = getIndex(currentFloor); i < destinationFloors.length; i++) {
                if (destinationFloors[i]) {
                    return possibleFloors.get(i);
                }
            }
        } else if (this.direction.equals(Direction.GOING_DOWN)) {
            for (int i = getIndex(currentFloor); i >= 0; i--) {
                if (destinationFloors[i]) {
                    return possibleFloors.get(i);
                }
            }
        } else {
            for (int i = this.currentFloor, j = this.currentFloor; i < destinationFloors.length || j >= 0; i++, j--) {
                if (i < destinationFloors.length) {
                    if (destinationFloors[getIndex(i)]) {
                        return i;
                    }
                }
                if (j >= 0) {
                    if (destinationFloors[getIndex(j)]) {
                        return j;
                    }
                }
            }
        }
        throw new NoDestinationException("There is no destination.");
    }

    boolean stopElevator() throws InvalidCaseException {
        this.currentState = idleState;
        destinationFloors[getIndex(currentFloor)] = false;
        openDoor();
        return true;
    }

    //TODO
//    boolean callElevator(int calledFloor, Direction direction) {
//        this.direction = direction;
//        destinationFloors.add(calledFloor);
//        return move();
//    }

    void addToDestination(int floor) throws InvalidCaseException {
       destinationFloors[getIndex(floor)] = true;
    }
}

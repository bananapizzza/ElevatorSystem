import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Elevator {
    boolean[] destinationFloors;
    Integer currentFloor = 0;
    Direction direction = Direction.NONE;
    List<Integer> possibleFloors;
    final ElevatorState idleState = new IdleState();
    final ElevatorState movingState = new MovingState();
    final ElevatorState outOfServiceState = new OutOfServiceState();
    ElevatorState currentState = idleState;

    Elevator(List<Integer> possibleFloors) {
        this.possibleFloors = possibleFloors;
        this.destinationFloors = new boolean[possibleFloors.size()];
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
            return currentState.selectFloor(this, floor);
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

    void move() throws InvalidCaseException, NoDestinationException {
        if (!destinationExist()) {
            throw new NoDestinationException("There is no destination.");
        }
        while (destinationExist()) {
            int destinationFloor = getClosestDestinationFloor();
            this.currentState = movingState;
            if(destinationFloor > currentFloor){
                setDirection(Direction.GOING_UP);
            } else if(destinationFloor < currentFloor){
                setDirection(Direction.GOING_DOWN);
            }

            if (this.direction.equals(Direction.GOING_UP)) {
                while (currentFloor < destinationFloor) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentFloor++;
                    System.out.println("Current floor is " + currentFloor);
                    if (destinationFloors[getIndex(currentFloor)]) {
                        break;
                    }
                }
            } else if (this.direction.equals(Direction.GOING_DOWN)) {
                while (currentFloor > destinationFloor) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
            stop();
        }
        this.direction = Direction.NONE;
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

    boolean stop() throws InvalidCaseException {
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

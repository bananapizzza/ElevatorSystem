import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.IntStream;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorMovementInfo {
    int currentFloor = 0;
    int currentDestinationFloor = 0;
    boolean[] destinationFloors;
    Direction direction = Direction.NONE;
    final ElevatorState idleState = new IdleState();
    final ElevatorState movingState = new MovingState();
    final ElevatorState outOfServiceState = new OutOfServiceState();
    ElevatorState currentState = idleState;
    List<Integer> totalFloors;
    List<Integer> possibleFloors;

    public ElevatorMovementInfo(List<Integer> totalFloors) {
        destinationFloors = new boolean[totalFloors.size()];
        this.totalFloors = totalFloors;
        this.possibleFloors = List.copyOf(totalFloors);
    }

    public boolean isPossibleToSelect(int floor) {
        if (!currentState.isPossibleToSelect(floor)) {
            return false;
        }
        if (!possibleFloors.contains(floor)) {
            return false;
        }
        try {
            switch (direction) {
                case GOING_UP:
                    if (floor <= currentFloor) {
                        return false;
                    }
                    break;
                case GOING_DOWN:
                    if (floor >= currentFloor) {
                        return false;
                    }
                    break;
                case NONE:
                    if (floor > currentFloor) {
                        direction = Direction.GOING_UP;
                    } else if (floor < currentFloor) {
                        direction = Direction.GOING_DOWN;
                    }
                    break;
                default:
                    throw new InvalidCaseException("Invalid direction.");
            }
        } catch (InvalidCaseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addToDestination(int floor) {
        try {
            destinationFloors[getIndex(floor)] = true;
        } catch (InvalidCaseException e) {
            e.printStackTrace();
        }
    }

    public boolean isPossibleToOpenDoor() {
        return currentState.isPossibleToOpenDoor();
    }

    public boolean isPossibleToCloseDoor() {
        return currentState.isPossibleToCloseDoor();
    }

    private int getIndex(int floor) throws InvalidCaseException {
        for (int i = 0; i < totalFloors.size(); i++) {
            if (totalFloors.get(i) == floor) {
                return i;
            }
        }
        throw new InvalidCaseException("The floor is not in the possibleFloors list.");
    }

    public boolean destinationExist() {
        for (int i = 0; i < destinationFloors.length; i++) {
            if (destinationFloors[i]) {
                return true;
            }
        }
        return false;
    }

    public int getClosestDestinationFloor() throws NoDestinationException, InvalidCaseException {
        int destinationFloorIndex;
        if (this.direction.equals(Direction.GOING_UP)) {
            //If this is going up, get the closest upper floor from the current floor.
            destinationFloorIndex =  IntStream.range(getIndex(currentFloor), destinationFloors.length)
                    .filter(floor -> destinationFloors[floor])
                    .findFirst().getAsInt();
            return totalFloors.get(destinationFloorIndex);
        } else if (this.direction.equals(Direction.GOING_DOWN)) {
            //If this is going down, get the closest lower floor from the current floor.
            destinationFloorIndex = IntStream.rangeClosed(getIndex(currentFloor), 0)
                    .filter(floor -> destinationFloors[floor])
                    .findFirst().getAsInt();
            return totalFloors.get(destinationFloorIndex);
        } else {
            //If the direction is none, get the closest floor from the current floor.
            //It doesn't matter if it's upper or lower.
            for (int i = getIndex(currentFloor), j = getIndex(currentFloor); i < destinationFloors.length || j >= 0; i++, j--) {
                if (i < destinationFloors.length) {
                    if (destinationFloors[i]) {
                        return totalFloors.get(i);
                    }
                }
                if (j >= 0) {
                    if (destinationFloors[j]) {
                        return totalFloors.get(j);
                    }
                }
            }
        }
        throw new NoDestinationException("There is no destination.");
    }

    public boolean isTheFloorDestination(int floor) {
        boolean isDestination = false;
        try {
            isDestination = destinationFloors[getIndex(currentFloor)];
        } catch (InvalidCaseException e) {
            e.printStackTrace();
        }
        return isDestination;
    }

    public void goToDestination(int destinationFloor, Direction direction){
        this.currentState = movingState;
        this.direction = direction;
        this.currentDestinationFloor = destinationFloor;
    }

    public void arrivedToDestination(){
        this.currentState = idleState;
        try {
            destinationFloors[getIndex(currentFloor)] = false;
        } catch (InvalidCaseException e) {
            e.printStackTrace();
        }
    }
}

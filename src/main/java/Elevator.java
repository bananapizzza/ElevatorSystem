import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Elevator {
    @NonNull
    String id;
    int height;
    int width;
    int length;
    int possibleWeight;
    int currentWeight = 0;
    @NonNull
    ElevatorMovementInfo elevatorMovementInfo;
    boolean isDoorOpened = false;
    final public int DOOR_OPEN_CLOSE_TIME = 2000;
    final public int WEIGHT_EXCEEDED_WAIT_TIME = 1000;

    public Elevator(String id, int height, int width, int length, int possibleWeight, ElevatorMovementInfo elevatorMovementInfo) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.length = length;
        this.possibleWeight = possibleWeight;
        this.elevatorMovementInfo = elevatorMovementInfo;
    }

    public boolean selectFloor(int floor) {
        if (!elevatorMovementInfo.isPossibleToSelect(floor)) {
            System.out.println("Cannot add the floor to the destination.");
            return false;
        }
        elevatorMovementInfo.addToDestination(floor);
        return true;
    }

    public boolean openDoor() {
        if (!isPossibleToOpenDoor()) {
            System.out.println("Cannot open the door now.");
            return false;
        }
        System.out.println("Opening the door...");
        isDoorOpened = true;
        waitingFor(DOOR_OPEN_CLOSE_TIME);
        while (isTooManyPeople()) {
            System.out.println("There are too many people in here.");
            waitingFor(WEIGHT_EXCEEDED_WAIT_TIME);
        }
        closeDoor();
        return true;
    }

    public boolean closeDoor() {
        if (!isDoorOpened) {
            System.out.println("The door is already closed.");
            return false;
        }
        if (!isPossibleToCloseDoor()) {
            System.out.println("Cannot close the door now.");
            return false;
        }
        System.out.println("Closing the door...");
        isDoorOpened = false;
        return true;
    }

    private boolean isPossibleToOpenDoor() {
        if (!elevatorMovementInfo.isPossibleToOpenDoor()) {
            return false;
        }
        return true;
    }

    private boolean isPossibleToCloseDoor() {
        if (!elevatorMovementInfo.isPossibleToCloseDoor()) {
            return false;
        }
        if (isTooManyPeople()) {
            return false;
        }
        return true;
    }

    private boolean isTooManyPeople() {
        return currentWeight > possibleWeight;
    }

    public boolean callEmergency() {
        System.out.println("Calling to emergency service...");
        return true;
    }

    public void arrivedToDestination(){
        System.out.println("Arrived to "+elevatorMovementInfo.getCurrentFloor()+" floor.");
        openDoor();
    }

    public int getCurrentFloor(){
        return elevatorMovementInfo.getCurrentFloor();
    }

    private void waitingFor(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

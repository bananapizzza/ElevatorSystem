import lombok.Getter;

import java.util.Set;

@Getter
public class Elevator {
    Set<Integer> possibleFloors;
    int currentFloor;
    int destinationFloor;

    public void selectFloor(int floor){}
    public void openDoor(){}
    public void closeDoor(){}
    public void callEmergency(){}
}

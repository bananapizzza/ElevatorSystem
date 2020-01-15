import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Elevator {
    @NonNull
    Set<Integer> possibleFloors;
    Integer currentFloor = 0;
    Integer destinationFloor = null;

    void selectFloor(int floor){}
    void openDoor(){}
    void closeDoor(){}
    void callEmergency(){}

    Elevator(Set<Integer> possibleFloors){
        this.possibleFloors = possibleFloors;
    }
}

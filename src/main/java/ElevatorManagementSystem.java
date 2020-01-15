import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorManagementSystem {
    @NonNull
    int numOfFloor;

    final List<Elevator> elevatorList = new ArrayList<>();

    @Builder
    ElevatorManagementSystem(int numOfFloor){
        this.numOfFloor = numOfFloor;
        addElevators(numOfFloor);
    }

    void addElevators(int numOfFloor){

    }

    public Elevator callElevator(int currentFloor, int direction){
        return null;
    }
}

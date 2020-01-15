import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorManagementSystem {
    @NonNull
    int numOfFloor;
    @NonNull
    int numOfBasementFloor;
    @NonNull
    int numOfElevators;

    public final int DIRECTION_GOING_UP = 1;
    public final int DIRECTION_GOING_DOWN = 2;
    public final List<Elevator> elevatorList = new ArrayList<>();

    @Builder
    ElevatorManagementSystem(int numOfFloor, int numOfBasementFloor, int numOfElevators){
        this.numOfFloor = numOfFloor;
        this.numOfBasementFloor = numOfBasementFloor;
        this.numOfElevators = numOfElevators;
        addElevators(numOfElevators);
    }

    void addElevators(int numOfFloor){

    }

    public Elevator callElevator(int currentFloor, int direction){
        return null;
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorManagementSystemTest {
    ElevatorManagementSystem elevatorManagementSystem;

    @BeforeEach
    void initialize(){
        elevatorManagementSystem = ElevatorManagementSystem.builder()
                .numOfFloor(10)
                .numOfBasementFloor(2)
                .numOfElevators(3)
                .build();
    }

    @Test
    void addElevators() {
        elevatorManagementSystem.addElevators(2);
        assertEquals(12, elevatorManagementSystem.getElevatorList().size());
    }

    @Test
    void callElevator() {
        Elevator elevator = elevatorManagementSystem.callElevator(0, elevatorManagementSystem.DIRECTION_GOING_UP);
        assertNotNull(elevator);
    }
}
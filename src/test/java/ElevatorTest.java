import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ElevatorTest {
    Elevator elevator;
    ElevatorMovementInfo elevatorMovementInfo;
    ElevatorMover elevatorMover;
    @BeforeEach
    void initialize(){
        Integer[] floors = {-2, -1, 0, 1, 2, 3, 4, 5};
        elevatorMovementInfo = new ElevatorMovementInfo(Arrays.asList(floors));
        elevator = new Elevator("ev1", 10, 10, 10, 100, elevatorMovementInfo);
        elevatorMover = new ElevatorMover(elevator, elevatorMovementInfo);
        elevatorMover.start();
    }

    @Test
    void test_selectFloor_IdleCase() throws InterruptedException {
        elevatorMovementInfo.setCurrentState(elevatorMovementInfo.getIdleState());
        assertTrue(elevator.selectFloor(5));
        assertEquals(0, elevator.getCurrentFloor());
        Thread.sleep(7000);
        assertEquals(5, elevator.getCurrentFloor());

        assertFalse(elevator.selectFloor(5));
        assertFalse(elevator.selectFloor(6));
    }

    @Test
    void test_openDoor_IdleCase() {
        elevatorMovementInfo.setCurrentState(elevatorMovementInfo.getIdleState());
        assertTrue(elevator.openDoor());
    }

    @Test
    void test_closeDoor_IdleCase() {
        elevatorMovementInfo.setCurrentState(elevatorMovementInfo.getIdleState());
        elevator.setDoorOpened(true);
        assertTrue(elevator.closeDoor());
    }

    @Test
    void test_selectFloor_MovingState() throws InterruptedException {
        elevatorMovementInfo.setCurrentState(elevatorMovementInfo.getMovingState());
        elevatorMovementInfo.setDirection(Direction.GOING_UP);

        assertTrue(elevator.selectFloor(3));
        Thread.sleep(3000);
        assertFalse(elevator.selectFloor(1));
        assertTrue(elevator.selectFloor(5));
        Thread.sleep(7000);

        elevatorMovementInfo.setCurrentState(elevatorMovementInfo.getMovingState());
        elevatorMovementInfo.setDirection(Direction.GOING_UP);
        assertFalse(elevator.selectFloor(4));
    }

    @Test
    void test_openDoor_MovingState() {
        elevatorMovementInfo.setCurrentState(elevatorMovementInfo.getMovingState());
        assertFalse(elevator.openDoor());
    }

    @Test
    void test_closeDoor_MovingState() {
        elevatorMovementInfo.setCurrentState(elevatorMovementInfo.getMovingState());
        assertFalse(elevator.closeDoor());
    }

    @Test
    void test_moveElevator() throws InterruptedException {
        elevatorMovementInfo.addToDestination(3);
        elevatorMovementInfo.addToDestination(4);
        elevatorMovementInfo.addToDestination(5);

        Thread.sleep(5000);
        assertEquals(3, elevator.getCurrentFloor());
        Thread.sleep(5000);
        assertEquals(4, elevator.getCurrentFloor());
        Thread.sleep(5000);
        assertEquals(5, elevator.getCurrentFloor());
    }

    @Test
    void test_callElevator() {

    }
}
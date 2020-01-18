import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class ElevatorTest {
    Elevator elevator;
    @BeforeEach
    void initialize(){
        Integer[] floors = {-2, -1, 0, 1, 2, 3, 4, 5};
        elevator = new Elevator(Arrays.asList(floors));
    }

    @Test
    void test_selectFloor_IdleCase() {
        elevator.setCurrentState(elevator.getIdleState());
        assertTrue(elevator.selectFloor(5));
        assertEquals(5, elevator.getCurrentFloor());

        assertFalse(elevator.selectFloor(5));
        assertFalse(elevator.selectFloor(6));
    }

    @Test
    void test_openDoor_IdleCase() {
        elevator.setCurrentState(elevator.getIdleState());
        assertTrue(elevator.openDoor());
    }

    @Test
    void test_closeDoor_IdleCase() {
        elevator.setCurrentState(elevator.getIdleState());
        assertTrue(elevator.closeDoor());
    }

    @Test
    void test_selectFloor_MovingState() {
        elevator.setCurrentState(elevator.getMovingState());
        elevator.setDirection(Direction.GOING_UP);
        assertTrue(elevator.selectFloor(5));
        assertFalse(elevator.selectFloor(6));

        elevator.setCurrentState(elevator.getMovingState());
        elevator.setDirection(Direction.GOING_UP);
        elevator.setCurrentFloor(5);
        assertFalse(elevator.selectFloor(4));
    }

    @Test
    void test_openDoor_MovingState() {
        elevator.setCurrentState(elevator.getMovingState());
        assertFalse(elevator.openDoor());
    }

    @Test
    void test_closeDoor_MovingState() {
        elevator.setCurrentState(elevator.getMovingState());
        assertFalse(elevator.closeDoor());
    }

    @Test
    void test_move() throws NoDestinationException, InvalidCaseException {
        assertThrows(NoDestinationException.class, ()->elevator.move());

        elevator.addToDestination(3);
        elevator.addToDestination(4);
        elevator.addToDestination(5);

        elevator.move();
    }

    @Test
    void test_callElevator() {

    }
}
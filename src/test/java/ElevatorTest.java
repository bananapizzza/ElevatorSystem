import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ElevatorTest {
    Elevator elevator;
    @BeforeEach
    void initialize(){
        Integer[] floors = {-2, -1, 0, 1, 2, 3, 4, 5};
        elevator = new Elevator(Arrays.asList(floors));
    }

    @Test
    void test_selectFloor_IdleCase() throws InterruptedException {
        elevator.setCurrentState(elevator.getIdleState());
        elevator.start();
        assertTrue(elevator.selectFloor(5));
        assertEquals(0, elevator.getCurrentFloor());
        Thread.sleep(7000);
        assertEquals(5, elevator.getCurrentFloor());

        assertFalse(elevator.selectFloor(5));
        assertFalse(elevator.selectFloor(6));
    }

    @Test
    void test_openDoor_IdleCase() {
        elevator.setCurrentState(elevator.getIdleState());
        elevator.start();
        assertTrue(elevator.openDoor());
    }

    @Test
    void test_closeDoor_IdleCase() {
        elevator.setCurrentState(elevator.getIdleState());
        elevator.start();
        assertTrue(elevator.closeDoor());
    }

    @Test
    void test_selectFloor_MovingState() throws InterruptedException {
        elevator.setCurrentState(elevator.getMovingState());
        elevator.setDirection(Direction.GOING_UP);
        elevator.start();

        assertTrue(elevator.selectFloor(3));
        Thread.sleep(3000);
        assertFalse(elevator.selectFloor(1));
        assertTrue(elevator.selectFloor(5));
        assertFalse(elevator.selectFloor(6));
        Thread.sleep(10000);

        elevator.setCurrentState(elevator.getMovingState());
        elevator.setDirection(Direction.GOING_UP);
        assertFalse(elevator.selectFloor(4));
    }

    @Test
    void test_openDoor_MovingState() {
        elevator.setCurrentState(elevator.getMovingState());
        elevator.start();
        assertFalse(elevator.openDoor());
    }

    @Test
    void test_closeDoor_MovingState() {
        elevator.setCurrentState(elevator.getMovingState());
        elevator.start();
        assertFalse(elevator.closeDoor());
    }

    @Test
    void test_moveElevator() throws InvalidCaseException, InterruptedException {
        assertThrows(InvalidCaseException.class, () -> elevator.addToDestination(6));
        elevator.addToDestination(3);
        elevator.addToDestination(4);
        elevator.addToDestination(5);
        elevator.start();
        Thread.sleep(5000);
        assertEquals(3, elevator.getCurrentFloor());
        Thread.sleep(6000);
        assertEquals(4, elevator.getCurrentFloor());
        Thread.sleep(7000);
        assertEquals(5, elevator.getCurrentFloor());
    }

    @Test
    void test_callElevator() {

    }
}
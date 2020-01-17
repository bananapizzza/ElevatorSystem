public interface ElevatorState {
    boolean selectFloor(Elevator elevator, int floor);
    boolean openDoor();
    boolean closeDoor();
}

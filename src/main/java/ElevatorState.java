public interface ElevatorState {
    boolean selectFloor(Elevator elevator, int floor) throws InvalidCaseException, NoDestinationException;
    boolean openDoor();
    boolean closeDoor();
}

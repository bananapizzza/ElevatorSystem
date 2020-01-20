public interface ElevatorState {
    boolean selectFloor(int floor) throws InvalidCaseException, NoDestinationException;
    boolean openDoor();
    boolean closeDoor();
}

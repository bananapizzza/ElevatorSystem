public interface ElevatorState {
    boolean isPossibleToSelect(int floor);
    boolean isPossibleToOpenDoor();
    boolean isPossibleToCloseDoor();
}

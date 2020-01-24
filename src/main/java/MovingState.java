public class MovingState implements ElevatorState {
    @Override
    public boolean isPossibleToSelect(int floor) {
        return true;
    }

    @Override
    public boolean isPossibleToOpenDoor() {
        return false;
    }

    @Override
    public boolean isPossibleToCloseDoor() {
        return false;
    }
}

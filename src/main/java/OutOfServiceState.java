public class OutOfServiceState implements ElevatorState {
    @Override
    public boolean isPossibleToSelect(int floor) {
        return false;
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

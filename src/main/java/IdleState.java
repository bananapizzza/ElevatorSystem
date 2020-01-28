public class IdleState implements ElevatorState {
    @Override
    public boolean isPossibleToSelect(int floor) {
        return true;
    }

    @Override
    public boolean isPossibleToOpenDoor() {
        return true;
    }

    @Override
    public boolean isPossibleToCloseDoor() {
        return true;
    }
}

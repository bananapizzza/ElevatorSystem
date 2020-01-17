public class OutOfServiceState implements ElevatorState {
    @Override
    public boolean selectFloor(Elevator elevator, int floor) {
        System.out.println("This elevator is currently out of service.");
        return false;
    }

    @Override
    public boolean openDoor() {
        System.out.println("This elevator is currently out of service.");
        return false;
    }

    @Override
    public boolean closeDoor() {
        System.out.println("This elevator is currently out of service.");
        return false;
    }
}

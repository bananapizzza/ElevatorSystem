public class OutOfServiceState implements ElevatorState {
    Elevator elevator;

    public OutOfServiceState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public boolean selectFloor(int floor) {
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

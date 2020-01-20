public class MovingState implements ElevatorState {
    Elevator elevator;

    public MovingState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public boolean selectFloor(int floor) throws InvalidCaseException {
        if (!elevator.isTheFloorPossibleToGo(floor)) {
            System.out.println("This elevator can't go to the " + floor + " floor.");
            return false;
        }
        Direction currentDirection = elevator.getDirection();
        if(floor > elevator.getCurrentFloor() && currentDirection.equals(Direction.GOING_UP)){
            elevator.addToDestination(floor);
        } else if(floor < elevator.getCurrentFloor() && currentDirection.equals(Direction.GOING_DOWN)){
            elevator.addToDestination(floor);
        } else {
            System.out.println("Cannot add "+floor+" floor to the destination.");
            return false;
        }
        return true;
    }

    @Override
    public boolean openDoor() {
        System.out.println("Cannot open the door right now.");
        return false;
    }

    @Override
    public boolean closeDoor() {
        System.out.println("Cannot close the door right now.");
        return false;
    }
}

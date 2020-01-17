public class MovingState implements ElevatorState {
    @Override
    public boolean selectFloor(Elevator elevator, int floor) {
        if (!elevator.getPossibleFloors().contains(floor)) {
            System.out.println("This elevator can't go to the " + floor + " floor.");
            return false;
        }
        Direction currentDirection = elevator.getDirection();
        if(floor > elevator.getCurrentFloor() && currentDirection.equals(Direction.GOING_UP)){
            elevator.getDestinationFloors().add(floor);
        } else if(floor < elevator.getCurrentFloor() && currentDirection.equals(Direction.GOING_DOWN)){
            elevator.getDestinationFloors().add(floor);
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
        System.out.println("Cannot open the door right now.");
        return false;
    }
}

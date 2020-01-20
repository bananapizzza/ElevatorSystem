public class IdleState implements ElevatorState {
    Elevator elevator;

    public IdleState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public boolean selectFloor(int floor) throws InvalidCaseException, NoDestinationException {
        if (!elevator.isTheFloorPossibleToGo(floor)) {
            System.out.println("This elevator can't go to the " + floor + " floor.");
            return false;
        }
        switch(elevator.getDirection()){
            case GOING_UP:
                if(floor <= elevator.getCurrentFloor()) {
                    System.out.println("This elevator is going up. Select upper floor.");
                    return false;
                }
                break;
            case GOING_DOWN:
                if(floor >= elevator.getCurrentFloor()){
                    System.out.println("This elevator is going down. Select lower floor.");
                    return false;
                }
                break;
            case NONE:
                if(floor > elevator.getCurrentFloor()){
                    elevator.setDirection(Direction.GOING_UP);
                } else if (floor < elevator.getCurrentFloor()){
                    elevator.setDirection(Direction.GOING_DOWN);
                } else {
                    System.out.println("Selected floor is the current floor.");
                    openDoor();
                    return false;
                }
                break;
            default:
                throw new InvalidCaseException("Invalid direction.");
        }
        elevator.setCurrentState(elevator.getMovingState());
        elevator.addToDestination(floor);
        //elevator.moveElevator();
        return true;
    }

    @Override
    public boolean openDoor() {
        System.out.println("Opening the door...");
        elevator.waiting(elevator.DOOR_OPEN_CLOSE_TIME);
        closeDoor();
        return true;
    }

    @Override
    public boolean closeDoor() {
        System.out.println("Closing the door...");
        elevator.waiting(elevator.DOOR_OPEN_CLOSE_TIME);
        return true;
    }
}

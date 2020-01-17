public class IdleState implements ElevatorState {
    @Override
    public boolean selectFloor(Elevator elevator, int floor) {
        if (!elevator.getPossibleFloors().contains(floor)) {
            System.out.println("This elevator can't go to the " + floor + " floor.");
            return false;
        }
        switch(elevator.getDirection()){
            case GOING_UP:
                if(floor > elevator.getCurrentFloor()){
                    elevator.setCurrentState(elevator.getMovingState());
                    elevator.getDestinationFloors().add(floor);
                    elevator.move();
                } else {
                    System.out.println("This elevator is going up. Select upper floor.");
                    return false;
                }
                break;
            case GOING_DOWN:
                if(floor < elevator.getCurrentFloor()){
                    elevator.setCurrentState(elevator.getMovingState());
                    elevator.getDestinationFloors().add(floor);
                    elevator.move();
                } else {
                    System.out.println("This elevator is going down. Select lower floor.");
                    return false;
                }
                break;
            case NONE:
                if(floor > elevator.getCurrentFloor()){
                    elevator.setCurrentState(elevator.getMovingState());
                    elevator.setDirection(Direction.GOING_UP);
                    elevator.getDestinationFloors().add(floor);
                    elevator.move();
                } else if (floor < elevator.getCurrentFloor()){
                    elevator.setCurrentState(elevator.getMovingState());
                    elevator.setDirection(Direction.GOING_DOWN);
                    elevator.getDestinationFloors().add(floor);
                    elevator.move();
                } else {
                    System.out.println("Selected floor is the current floor.");
                    openDoor();
                    return false;
                }
                break;
            default:
                System.out.println("Invalid direction.");
                return false;
        }
        return true;
    }

    @Override
    public boolean openDoor() {
        System.out.println("Opening the door...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeDoor();
        return true;
    }

    @Override
    public boolean closeDoor() {
        System.out.println("Closing the door...");
        return true;
    }
}

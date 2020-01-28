public class ElevatorMover extends Thread {
    Elevator elevator;
    ElevatorMovementInfo elevatorMovementInfo;
    int destinationFloor;
    final int DESTINATION_CHECK_TIME = 1000;
    final public int MOVING_TIME = 1000;

    ElevatorMover(Elevator elevator, ElevatorMovementInfo elevatorMovementInfo) {
        this.elevator = elevator;
        this.elevatorMovementInfo = elevatorMovementInfo;
    }

    public void run() {
        while (true) {
            waitingFor(DESTINATION_CHECK_TIME);
            while (elevatorMovementInfo.destinationExist()) {
                try {
                    destinationFloor = elevatorMovementInfo.getClosestDestinationFloor();
                    Direction direction = null;
                    if (destinationFloor > elevatorMovementInfo.getCurrentFloor()) {
                        direction = Direction.GOING_UP;
                    } else if (destinationFloor < elevatorMovementInfo.getCurrentFloor()) {
                        direction = Direction.GOING_DOWN;
                    }
                    elevatorMovementInfo.goToDestination(destinationFloor, direction);

                    switch (elevatorMovementInfo.getDirection()) {
                        case GOING_UP:
                        case GOING_DOWN:
                            moveToDestinationFloor();
                            break;
                        case NONE:
                            throw new InvalidCaseException("The direction cannot be NONE while moving");
                        default:
                            throw new InvalidCaseException("Invalid direction.");
                    }

                    elevatorMovementInfo.arrivedToDestination();
                    elevator.arrivedToDestination();
                } catch (NoDestinationException | InvalidCaseException e) {
                    e.printStackTrace();
                }
            }
            elevatorMovementInfo.setDirection(Direction.NONE);
        }
    }

    private void moveToDestinationFloor() {
        if (elevatorMovementInfo.getDirection().equals(Direction.GOING_UP)) {
            while (elevatorMovementInfo.getCurrentFloor() < destinationFloor) {
                waitingFor(MOVING_TIME);
                elevatorMovementInfo.setCurrentFloor(elevatorMovementInfo.getCurrentFloor()+1);
                System.out.println("Current floor is " + elevatorMovementInfo.getCurrentFloor());
                if (elevatorMovementInfo.isTheFloorDestination(elevatorMovementInfo.getCurrentFloor())) {
                    break;
                }
            }
        } else if (elevatorMovementInfo.getDirection().equals(Direction.GOING_DOWN)) {
            while (elevatorMovementInfo.getCurrentFloor() > destinationFloor) {
                waitingFor(MOVING_TIME);
                elevatorMovementInfo.setCurrentFloor(elevatorMovementInfo.getCurrentFloor()-1);
                System.out.println("Current floor is " + elevatorMovementInfo.getCurrentFloor());
                if (elevatorMovementInfo.isTheFloorDestination(elevatorMovementInfo.getCurrentFloor())) {
                    break;
                }
            }
        }
    }

    private void waitingFor(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

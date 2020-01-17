import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Elevator {
    @NonNull
    Set<Integer> possibleFloors;
    Integer currentFloor = 0;
    //TODO: make boolean array for floors
    PriorityQueue<Integer> destinationFloors = new PriorityQueue<>();

    Direction direction = Direction.NONE;

    final ElevatorState idleState = new IdleState();
    final ElevatorState movingState = new MovingState();
    final ElevatorState outOfServiceState = new OutOfServiceState();
    ElevatorState currentState = idleState;

    Elevator(Set<Integer> possibleFloors) {
        this.possibleFloors = possibleFloors;
    }

    Elevator(List<Integer> possibleFloors){
        this.possibleFloors = new HashSet<>(possibleFloors);
    }

    //TODO: Should the return type boolean? or Should I use exceptions?
    boolean selectFloor(int floor) {
        return currentState.selectFloor(this, floor);
    }

    boolean openDoor() {
        return currentState.openDoor();
    }

    boolean closeDoor() {
        return currentState.closeDoor();
    }

    boolean callEmergency() {
        System.out.println("Calling to emergency service...");
        return true;
    }

    boolean move() {
        if(destinationFloors.isEmpty()){
            System.out.println("There is no destination floors.");
            return false;
        }
        while(!destinationFloors.isEmpty()) {
            int destinationFloor = destinationFloors.poll();
            if (destinationFloor > currentFloor) {
                System.out.println("Going up...");
                this.direction = Direction.GOING_UP;
            } else if (destinationFloor < currentFloor) {
                System.out.println("Going down...");
                this.direction = Direction.GOING_DOWN;
            }
            this.currentState = movingState;
            if(this.direction.equals(Direction.GOING_UP)){
                while(currentFloor < destinationFloor){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    currentFloor++;
                    System.out.println("Current floor is "+currentFloor);
                }
            }

            System.out.println("Arrived to " + currentFloor + " floor.");
            this.currentState = idleState;
            openDoor();
        }
        this.direction = Direction.NONE;
        return true;
    }

    boolean callElevator(int calledFloor, Direction direction){
        this.direction = direction;
        destinationFloors.add(calledFloor);
        return move();
    }

    void addToDestination(int floor){
        int key;
        switch(this.direction){
            case GOING_UP:
                key = floor - this.currentFloor;
                if(key <= 0){
                    key += 100;
                }
                break;
            case GOING_DOWN:
                key = this.currentFloor - floor;
                if(key <= 0){
                    key += 100;
                }
                break;
            case NONE:
                if(floor > this.currentFloor){
                    key = floor - this.currentFloor;
                } else {
                    key = this.currentFloor - floor;
                }
                break;
            default:
                System.out.println("Invalid direction.");
        }
    }
}

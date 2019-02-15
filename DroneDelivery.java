/**
 * 1 drone
 * All deliveries originate at origin
 * This means that drone has to go back after each delivery
 * Speed is one gridblock/min
 * 0-6 detractor
 * 7-8 neutral
 * 9-10 promoter
 * (10 - score) is number of hours to earn that score
 */
public class DroneDelivery {
    public static void main (String [] args) {
        Drone d1 = new Drone(1);
        d1.printCoords();
        d1.moveEast();
        d1.moveSouth();
        d1.printCoords();
    }
}
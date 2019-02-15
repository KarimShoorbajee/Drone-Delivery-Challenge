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
import java.io.*;

public class DroneDelivery {
    public static void main (String [] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Invalid input");
            System.exit(-1);
        }

        File input = null;
        BufferedReader reader = null;
        try {
            input = new File(args[0]);
            reader = new BufferedReader(new FileReader(input));
        }
        catch (Exception e){
            System.out.println("File Not Found.");
            System.exit(-1);
        }
        

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        Drone d1 = new Drone(1);
        d1.printCoords();
        d1.moveEast();
        d1.moveSouth();
        d1.printCoords();
    }
}
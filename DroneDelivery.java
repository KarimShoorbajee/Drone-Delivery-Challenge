/**
 * 
 * 
 * ASSUMPTIONS
 * 
 * There's no obstacles the drone can't fly through
 * 
 * The drone can fly diagonally. Use the distance formula to calculate the block distances traversed, cast to an int and round up to calculate number of seconds
 * 
 * All deliveries originate at the warehouse means that to fulfill a customer order the drone has to start at the origin and end at the customer location.
 * Time of the delivery is from when the order comes in to when the drone arrives at the customer location.
 * The drone has to go back to the warehouse after every delivery.
 * 
 * We have access to all the orders at the start of the program but the drone can't leave for an order that hasn't come in yet in the simulation's timer
 * 
 * All deliveries must be fulfilled and they must be fuliffled within 10 hours
 * 
 */


import java.io.*;
import java.util.*;

public class DroneDelivery {

    public static long startTime=6*60*60;
    public static long droneUpTime = 16*60*60;
    public static float promoters = 0;
    public static float detractors = 0;

    public static void main (String [] args) throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        
        if (args.length < 1) {
            System.out.println("Invalid input.");
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
        LinkedList<Delivery> deliveriesInOrder = new LinkedList<Delivery>();

        while ((line = reader.readLine()) != null) {
            String[] delivery = line.split("\\s+");
            deliveriesInOrder.add(new Delivery(delivery[0],delivery[1],delivery[2]));
        }

        int numDeliveriesToFulfill = deliveriesInOrder.size();
        long time = 0;
        Drone d1 = new Drone(60);
        PriorityQueue<Delivery> pQueue = new PriorityQueue<Delivery>();
        LinkedList<Delivery> certifiedTardy = new LinkedList<Delivery>();
        boolean droneUp = true;
        while (droneUp && deliveriesInOrder.size() >= 1) {
            if (startTime + time >= deliveriesInOrder.getFirst().getTimestamp() ) {
                while (
                    deliveriesInOrder.size() >= 1 && 
                    startTime + time >= deliveriesInOrder.getFirst().getTimestamp()
                    ) {
                    pQueue.add(deliveriesInOrder.removeFirst());
                }
            }
            else time=deliveriesInOrder.getFirst().getTimestamp() - startTime;

    
            while (pQueue.size() >= 1 && droneUp) {
                if (estimateTravelTime(d1, pQueue.peek()) + time > droneUpTime) {
                    droneUp = false;
                    break;
                }
                if (pQueue.peek().getHypotheticalScore(d1, startTime) < 7)
                    certifiedTardy.add(pQueue.poll());
                Delivery deliv = pQueue.poll();
                String departTime = timeToString(time);
                time = deliv.fulfill(d1,time,startTime);
                System.out.println(deliv.getOrder() + " " + departTime);
                if (deliv.getScore()>8) promoters++;
                else if (deliv.getScore()<7) detractors++;
            }

            while (certifiedTardy.size() >= 1  && droneUp) {
                if (estimateTravelTime(d1, pQueue.peek()) + time > droneUpTime) {
                    droneUp = false;
                    break;
                }
                Delivery deliv = certifiedTardy.pop();
                String departTime = timeToString(time);
                time = deliv.fulfill(d1,time,startTime);
                System.out.println(deliv.getOrder() + " " + departTime);
                if (deliv.getScore()>8) promoters++;
                else if (deliv.getScore()<7) detractors++;
            }
            if (!droneUp) {
                int ordersLeft = pQueue.size() + deliveriesInOrder.size();
                for (int i = 0; i < ordersLeft; i++)
                    detractors++;
            }
        }
        int nps = calculateNPS(numDeliveriesToFulfill);
        System.out.println("NPS "+ nps);
    }

    public static String timeToString(long time) {
        String hours = Long.toString(startTime/60/60+(time/60/60));
        String minutes = Long.toString(time/60 - (time/60/60)*60);
        String seconds = Long.toString(time%60);
        if (hours.length() < 2)
            hours = "0" + hours;
        if (minutes.length() < 2)
            minutes = "0" + minutes;
        if (seconds.length() < 2)
            seconds = "0" + seconds;
        return hours + ":" + minutes + ":" + seconds;
    }

    public static int calculateNPS(int numDeliveriesToFulfill) {
        float detractorRat = detractors/numDeliveriesToFulfill;
        float promotersRat = promoters/numDeliveriesToFulfill;
        return Math.round(100*(promotersRat-detractorRat));
    }

    public static long estimateTravelTime(Drone x,Delivery d) {
        return 2*(x.calculateTravelTime(d.getX(), d.getY()));
    }
}
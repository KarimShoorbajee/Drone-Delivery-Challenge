public class Delivery implements Comparable<Delivery> {
    
    private String orderID;
    private long timestamp;
    private int x;
    private int y;
    private int score;
    private boolean fulfilled;

    public Delivery(String id, String coords, String ts) {

        score = 0;

        coords = coords.toUpperCase();
        String [] deltaXY = coords.split("[A-Z]");

        String [] dirs = coords.split("[0-9]+");
        x = Integer.parseInt(deltaXY[2]);
        y = Integer.parseInt(deltaXY[1]);
        if (dirs[0].compareTo("S") == 0) {
            y*=-1;
        }
        if (dirs[1].compareTo("W") == 0) {
            x*=-1;
        }

        String [] hourMinSec = ts.split(":");
        int hr = Integer.parseInt(hourMinSec[0]);
        int min = Integer.parseInt(hourMinSec[1]);
        int sec = Integer.parseInt(hourMinSec[2]);
        timestamp = hr*60*60+min*60+sec;

        fulfilled = false;
        orderID = id.toUpperCase();

    }

    public String getOrder() {return this.orderID;}

    public long getTimestamp() {return this.timestamp;}

    public String getOrderID() {return this.orderID;}

    public int getX() {return this.x;}

    public int getY() {return this.y;}

    public int getScore() {return score;}

    public long getDistanceFromOrigin() {
        return (long)Math.ceil(Math.sqrt(x*x+y*y));
    }

    public void setScore(long deliveryTime, long startTime) {
        long deliverdIn = startTime +deliveryTime - timestamp;
        System.out.println(deliverdIn);
        this.score = (int)(10 - deliverdIn/60/60);
    }

    public int getHypotheticalScore(Drone x, long startTime) {
        long deliverTime = startTime + x.calculateTravelTime(this.x, this.y);
        long deliveredIn = deliverTime - timestamp;
        return (int)(10 - deliveredIn/60/60);
    }

    public void fulfil() {
        if (this.fulfilled == true) {
            System.out.println("Order " + this.orderID + " is already fulfilled");
        }
        else fulfilled = true;
    }

    public boolean getFulfilled() {
        return fulfilled;
    }

    public int compareTo(Delivery d) {
            return (int)(this.getDistanceFromOrigin() - d.getDistanceFromOrigin());
    }
}
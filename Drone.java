public class Drone {

    //private fields
    private int s;
    private int x;
    private int y;

    //constructor sets position to 0,0 by default
    public Drone(int speed) {
        s = speed;
        x = 0;
        y = 0;
    }

    //geters

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSpeed() {
        return this.s;
    }

    //returns reference to an array with the drone's coordinates
    public int[] getCoords() {
        int[] coords = new int[3];
        coords[0] = this.x;
        coords[1] = this.y;
        return coords;
    }

    public long calculateTravelTime(int destX, int destY) {
        double deltaX = Math.abs(destX-this.x);
        double deltaY = Math.abs(destY-this.y);
        return (long)Math.ceil(s*(Math.sqrt(deltaX*deltaX+deltaY*deltaY)));
    }

    public long travel(int destX, int destY) {
        long tTime = this.calculateTravelTime(destX, destY);
        this.x = destX;
        this.y = destY;
        return tTime;
    }

    public void printCoords() {
        System.out.println("(" +this.x + "," + this.y + ")");
    }
}
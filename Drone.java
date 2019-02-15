public class Drone {

    //private fields
    private int s;
    private int x;
    private int y;

    //constructoe sets position to 0,0 by default
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

    //Movement methods
    //Asumes that north corresponds to positive Y axis
    public void moveNorth() {
        this.y++;
    }

    public void moveSouth() {
        this.y--;
    }

    public void moveEast() {
        this.x++;
    }

    public void moveWest() {
        this.x--;
    }

    public void printCoords() {
        System.out.println("(" +this.x + "," + this.y + ")");
    }
}
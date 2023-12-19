public class Dithrow {

    public enum Side{
        LEFT,
        RIGHT
    }

    final Side source;
    final Side dest;
    final int swap; // how high you are swapping

    public Dithrow(Side source, Side dest, int swap){
        this.source = source;
        this.dest = dest;
        this.swap = swap;
    }

    public Dithrow mirror(){
        Side nsrc = source == Side.LEFT? Side.RIGHT : Side.LEFT;
        Side ndest = source == Side.LEFT? Side.RIGHT : Side.LEFT;

        return new Dithrow(nsrc, ndest, swap);
    }

    public boolean equal(Object other){
        if(other instanceof Dithrow){
            Dithrow friend = (Dithrow)other;

            return this.source == friend.source && this.dest == friend.dest && this.swap == friend.swap;
        }
        return false;
    }

    public String toString(){
        String side = source == Side.LEFT? "L":"R";
        String cross = source == dest?"":"x";
        char num = swap < 10? (char)(swap + '0'):(char)(swap + 'a' - 10);

        return Character.toString(num) + cross + side;
    }
}

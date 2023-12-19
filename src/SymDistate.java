import java.util.Arrays;

public class SymDistate extends Distate {
    public SymDistate(int[] l, int[] r) {
        super(l, r);
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Distate){
            Distate friend = (Distate)other;
            return (Arrays.equals(this.left, friend.left) && Arrays.equals(this.right, friend.right)) ||
                    (Arrays.equals(this.left, friend.right) && Arrays.equals(this.right, friend.left));
        }
        return false;
    }
}

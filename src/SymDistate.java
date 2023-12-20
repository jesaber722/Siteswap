import java.util.ArrayList;
import java.util.Arrays;

public class SymDistate extends Distate {
    public SymDistate(int[] l, int[] r) {
        super(l, r);
    }


    public static class SymSuccRet{
        SymDistate distate;
        Dithrow dithrow;

        public SymSuccRet(SymDistate distate, Dithrow dithrow){
            this.distate = distate;
            this.dithrow = dithrow;

        }
    }

    /**
     * Get all successors to this position, ignoring swaps of 0 and 1
     * @return
     */
    public ArrayList<SymDistate.SymSuccRet> getSymSuccessors() {
        ArrayList<SymSuccRet> ret = new ArrayList<>();

        //left-to-left
        for(int swap = 2; swap < left.length + 1; swap ++){
            int [] newLeft = new int[left.length];
            int [] newRight = new int[right.length];

            for(int i = 0; i < left.length; i++){
                if(i < swap - 1){
                    newLeft[i] = left[i + 1];
                } else if(i == swap - 1){
                    newLeft[i] = left[0];
                } else {
                    newLeft[i] = left[i];
                }
            }

            for(int i = 0; i < right.length; i++){
                newRight[i] = right[i];
            }

            ret.add(new SymSuccRet(new SymDistate(newLeft, newRight), new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.LEFT, swap)));
        }

        //left-to-right
        if(left.length > 0) {
            for (int swap = 0; swap < right.length + 1; swap++) {
                int[] newLeft = new int[left.length - 1];
                int[] newRight = new int[right.length + 1];

                for (int i = 0; i < newLeft.length; i++) {
                    newLeft[i] = left[i + 1];
                }

                for (int i = 0; i < newRight.length; i++) {
                    if (i < swap) {
                        newRight[i] = right[i];
                    } else if (i == swap) {
                        newRight[i] = left[0];
                    } else {
                        newRight[i] = right[i - 1];
                    }
                }

                ret.add(new SymSuccRet(new SymDistate(newLeft, newRight), new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, swap)));
            }
        }

        //right-to-right
        for(int swap = 2; swap < right.length + 1; swap ++){
            int [] newRight = new int[right.length];
            int [] newLeft = new int[left.length];

            for(int i = 0; i < right.length; i++){
                if(i < swap - 1){
                    newRight[i] = right[i + 1];
                } else if(i == swap - 1){
                    newRight[i] = right[0];
                } else {
                    newRight[i] = right[i];
                }
            }

            for(int i = 0; i < left.length; i++){
                newLeft[i] = left[i];
            }

            ret.add(new SymSuccRet(new SymDistate(newLeft, newRight), new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, swap)));
        }

        //right-to-left
        if(right.length > 0) {
            for (int swap = 0; swap < left.length + 1; swap++) {
                int[] newRight = new int[right.length - 1];
                int[] newLeft = new int[left.length + 1];

                for (int i = 0; i < newRight.length; i++) {
                    newRight[i] = right[i + 1];
                }

                for (int i = 0; i < newLeft.length; i++) {
                    if (i < swap) {
                        newLeft[i] = left[i];
                    } else if (i == swap) {
                        newLeft[i] = right[0];
                    } else {
                        newLeft[i] = left[i - 1];
                    }
                }

                ret.add(new SymSuccRet(new SymDistate(newLeft, newRight), new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, swap)));
            }
        }
        return ret;
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

    public boolean strictEquals(Object other){
        if(other instanceof Distate){
            Distate friend = (Distate)other;
            return (Arrays.equals(this.left, friend.left) && Arrays.equals(this.right, friend.right));
        }
        return false;
    }
}

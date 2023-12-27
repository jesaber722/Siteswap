
import java.util.ArrayList;
import java.util.Arrays;

public class Distate {

    int [] left;
    int [] right;
    int balls;

    public Distate(int [] l, int [] r){
        left = l;
        right = r;
        balls = l.length + r.length;

    }


    public static class SuccRet{
        Distate distate;
        Dithrow dithrow;

        public SuccRet(Distate distate, Dithrow dithrow){
            this.distate = distate;
            this.dithrow = dithrow;

        }
    }

    /**
     * Get all successors to this position, ignoring swaps of 0 and 1
     * @return
     */
    public ArrayList<SuccRet> getSuccessors(){
        ArrayList<SuccRet> ret = new ArrayList<>();

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

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.LEFT, swap)));
        }

        //left-to-right
        for(int swap = 0; swap < right.length + 1; swap++){
            int [] newLeft = new int[left.length - 1];
            int [] newRight = new int[right.length + 1];

            for(int i = 0; i < newLeft.length; i++){
                newLeft[i] = left[i+1];
            }

            for(int i = 0; i < newRight.length; i++){
                if(i < swap) {
                    newRight[i] = right[i];
                } else if(i == swap){
                    newRight[i] = left[0];
                } else {
                    newRight[i] = right[i - 1];
                }
            }

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, swap + 1)));
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

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, swap)));
        }

        //right-to-left
        for(int swap = 0; swap < left.length + 1; swap++){
            int [] newRight = new int[right.length - 1];
            int [] newLeft = new int[left.length + 1];

            for(int i = 0; i < newRight.length; i++){
                newRight[i] = right[i+1];
            }

            for(int i = 0; i < newLeft.length; i++){
                if(i < swap) {
                    newLeft[i] = left[i];
                } else if(i == swap){
                    newLeft[i] = right[0];
                } else {
                    newLeft[i] = left[i - 1];
                }
            }

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, swap + 1)));
        }
        return ret;
    }


    /**
     * Get all successors to this position, ignoring swaps of 0 and 1
     * @return
     */
    public ArrayList<SuccRet> getGroundableSuccessors(){
        ArrayList<SuccRet> ret = new ArrayList<>();

        //left-to-left
        for(int swap = left.length; swap < left.length + 1; swap ++){
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

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.LEFT, swap)));
        }

        //left-to-right
        for(int swap = right.length; swap < right.length + 1; swap++){
            int [] newLeft = new int[left.length - 1];
            int [] newRight = new int[right.length + 1];

            for(int i = 0; i < newLeft.length; i++){
                newLeft[i] = left[i+1];
            }

            for(int i = 0; i < newRight.length; i++){
                if(i < swap) {
                    newRight[i] = right[i];
                } else if(i == swap){
                    newRight[i] = left[0];
                } else {
                    newRight[i] = right[i - 1];
                }
            }

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, swap + 1)));
        }

        //right-to-right
        for(int swap = right.length; swap < right.length + 1; swap ++){
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

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, swap)));
        }

        //right-to-left
        for(int swap = left.length; swap < left.length + 1; swap++){
            int [] newRight = new int[right.length - 1];
            int [] newLeft = new int[left.length + 1];

            for(int i = 0; i < newRight.length; i++){
                newRight[i] = right[i+1];
            }

            for(int i = 0; i < newLeft.length; i++){
                if(i < swap) {
                    newLeft[i] = left[i];
                } else if(i == swap){
                    newLeft[i] = right[0];
                } else {
                    newLeft[i] = left[i - 1];
                }
            }

            ret.add(new SuccRet(new Distate(newLeft, newRight), new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, swap + 1)));
        }
        return ret;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Distate){
            Distate friend = (Distate)other;
            if(Arrays.equals(this.left, friend.left) && Arrays.equals(this.right, friend.right)){
                return true;
            }
        }
        return false;
    }

    public int hashCode(){
        return Arrays.hashCode(left) ^ Arrays.hashCode(right);
    }

    public String toString(){
        StringBuilder str = new StringBuilder("{");
        for(int i = 0; i < left.length; i++){
            if(left[i] < 10){
                str.append((char) (left[i] + '0'));
            } else {
                str.append((char) (left[i] + 'a' - 10));
            }
        }
        str.append(',');

        for(int i = 0; i < right.length; i++){
            if(right[i] < 10){
                str.append((char) (right[i] + '0'));
            } else {
                str.append((char) (right[i] + 'a' - 10));
            }
        }
        str.append('}');

        return str.toString();
    }

    public static void main(String [] args){
        Distate ds1 = new Distate(new int[]{0, 1}, new int[]{2});

        ArrayList<SuccRet> rets = ds1.getSuccessors();

        for(int i = 0; i < rets.size(); i++){
            System.out.println(rets.get(i).distate);
        }
    }
}

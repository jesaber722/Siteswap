import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.IntStream;

public class Siteswap {

    public static class SiteswapException extends RuntimeException{

    }

    public final int [] numbers;
    public final int length;
    public final int balls;

    private static boolean isValid(int [] nums){
        int [] count = new int[nums.length];

        for(int i = 0; i < nums.length; i++){
            count[(i + nums[i]) % nums.length] ++;
        }

        for(int i = 0; i < count.length; i++){
            if(count[i] != 1){
                return false;
            }
        }
        return true;
    }

    public Siteswap(int [] nums){
        if (!isValid(nums)) {
            throw new SiteswapException();
        } else {
            numbers = nums;
            length = numbers.length;
            balls = IntStream.of(numbers).sum() / length;
        }
    }

    public StateChain toStateChain(){
        return new StateChain(this);
    }

    public StackSwap toStackSwap(){
        return new StateChain(this).toStackSwap();
    }

    public boolean isSimple(){
        StateChain chain = this.toStateChain();
        HashSet<State> seen = new HashSet<>();
        boolean [][] states = chain.states;

        for(int i = 0; i < states.length; i++){
            State state = new State(states[i]);

            if(seen.contains(state)){
                return false;
            } else {
                seen.add(state);
            }
        }

        return true;
    }

    public boolean looseEquals(Siteswap other){
        int [] these = numbers;
        int [] those = other.numbers;

        if(these.length != other.length){
            return false;
        }

        for(int offset = 0; offset < these.length; offset ++){
            boolean success = true;
            for(int i = 0; i < these.length; i++){
                if(these[i] != those[(i + offset) % these.length]){
                    success = false;
                    break;
                }
            }
            if(success){
                return true;
            }
        }
        return false;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < numbers.length; i++){


            char c;
            if(numbers[i] < 10){
                c = (char)(numbers[i] + '0');
            } else {
                c = (char)(numbers[i] + 'a' - 10);
            }

            str.append(c);
        }
        return str.toString();
    }

    public static void main(String [] args){
        /*
        System.out.println(isValid(new int[]{5, 3, 1}));
        System.out.println(isValid(new int[]{1, 2, 3, 4, 5}));
        System.out.println(isValid(new int[]{1, 2, 3, 4, 5, 6}));
        System.out.println(isValid(new int[]{9, 1, 5, 5}));
        //System.out.println(new Siteswap(new int[]{1, 2, 3, 4, 6}));
        System.out.println(new Siteswap(new int[]{10, 4, 1, 5}).toString());
        System.out.println(new StateChain(new Siteswap(new int[]{4, 2, 3})).toStackSwap().toString());
        System.out.println(new StateChain(new Siteswap(new int[]{8, 0, 1})).toStackSwap().toString());
         */
        //System.out.println(new StateGraph(ne))


        Siteswap ss = new Siteswap(new int[]{10, 4, 1, 5});
        StackSwap sw = new Siteswap(new int[]{10, 4, 1, 5}).toStackSwap();
        System.out.print(ss.toStateChain().toString() + "\n\n");
        System.out.println(sw);
        System.out.println(sw.toStackChain());

        System.out.println("MORE TESTS");
        System.out.println(new Siteswap(new int[]{5, 3, 1}).isSimple());
        System.out.println(new Siteswap(new int[]{4, 2, 3}).isSimple());
        System.out.println(new Siteswap(new int[]{7, 4, 1}).isSimple());
        System.out.println(new Siteswap(new int[]{5,6,4,5,0,5,3}).isSimple());
        System.out.println(new Siteswap(new int[]{5,6,4,5,0}).isSimple());

        /*
        // success on all
        System.out.println("LOOSEEQ T "+ new Siteswap(new int[]{5,6,4,5,0}).looseEquals(new Siteswap(new int[]{0,5,6,4,5})));
        System.out.println("LOOSEEQ T "+ new Siteswap(new int[]{5,6,4,5,0}).looseEquals(new Siteswap(new int[]{5,0,5,6,4})));
        System.out.println("LOOSEEQ F "+ new Siteswap(new int[]{5,6,4,5,0}).looseEquals(new Siteswap(new int[]{5,0,5,11,4})));
        System.out.println("LOOSEEQ F "+ new Siteswap(new int[]{5,6,4,5,0}).looseEquals(new Siteswap(new int[]{5, 3, 1})));
        System.out.println("LOOSEEQ T "+ new Siteswap(new int[]{5, 3, 1}).looseEquals(new Siteswap(new int[]{5, 3, 1})));
        System.out.println("LOOSEEQ T "+ new Siteswap(new int[]{5, 3, 1}).looseEquals(new Siteswap(new int[]{3, 1, 5})));
        System.out.println("LOOSEEQ F "+ new Siteswap(new int[]{5, 3, 1}).looseEquals(new Siteswap(new int[]{4, 4, 1})));

         */

        System.out.println(new Siteswap(new int[]{4, 5, 5, 0, 1}).toStateChain());
    }
}

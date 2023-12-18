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

    public StackSwap tooStackSwap(){
        return new StateChain(this).toStackSwap();
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
        StackSwap sw = new Siteswap(new int[]{10, 4, 1, 5}).tooStackSwap();
        System.out.print(ss.toStateChain().toString() + "\n\n");
        System.out.println(sw);
        System.out.println(sw.toStackChain());
    }
}

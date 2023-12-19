import java.util.Arrays;

public class StackSwap {


    public final int [] swaps;
    public final int length;
    public final int balls;

    public StackSwap(int [] sw){
        length = sw.length;
        for(int i = 0; i < sw.length; i++){
            if (sw[i] < 0)
                throw new Siteswap.SiteswapException();
        }
        swaps = sw;
        balls = Arrays.stream(sw).max().orElse(0);
    }

    /**
     * Removes zeros from stack notation and returns the new StackSwap
     * @return the new StackSwap
     */
    public StackSwap regularize(){
        int count = 0;

        for(int i = 0; i < swaps.length; i++){
            if(swaps[i] > 0){
                count ++;
            }
        }

        int [] newSwaps = new int[count];
        int index = 0;

        for(int i = 0; i < swaps.length; i++){
            if(swaps[i] > 0){
                newSwaps[index] = swaps[i];
                index ++;
            }
        }

        return new StackSwap(newSwaps);

    }


    /**
     * Removes ones and zeros from stack notation and returns the new StackSwap
     * @return the new StackSwap
     */
    public StackSwap hardRegularize(){
        int count = 0;

        for(int i = 0; i < swaps.length; i++){
            if(swaps[i] > 1){
                count ++;
            }
        }

        int [] newSwaps = new int[count];
        int index = 0;

        for(int i = 0; i < swaps.length; i++){
            if(swaps[i] > 1){
                newSwaps[index] = swaps[i];
                index ++;
            }
        }

        return new StackSwap(newSwaps);

    }

    public StackChain toStackChain(){
        return new StackChain(this);
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < swaps.length; i++){
            str.append(Integer.toString(swaps[i]));
        }
        return str.toString();
    }
}

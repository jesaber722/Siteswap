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

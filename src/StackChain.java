import java.util.ArrayList;
import java.util.stream.IntStream;

public class StackChain {

    public int [][] states;
    public int length;
    public int balls;

    public StackChain(StackSwap stackSwap){
        //ArrayList<int []> states = new ArrayList<>();
        this.length = stackSwap.length;
        this.balls = stackSwap.balls;
        states = new int[length][balls];

        /*
        int [] start = new int[balls];
        for(int i = 0; i < start.length; i++){
            start[i] = i;
        }
         */

        for(int j = 0; j < balls; j++) {
            if (j < stackSwap.swaps[0] - 1) {
                states[0][j] = j + 1;
            } else if (j == stackSwap.swaps[0] - 1) {
                states[0][j] = 0;
            } else {
                states[0][j] = j;
            }
        }

        for(int i = 1; i < length ; i++){
            for(int j = 0; j < balls; j++){
                if(j < stackSwap.swaps[i] - 1){
                    states[i][j] = states[i-1][j+1];
                } else if(j == stackSwap.swaps[i] - 1){
                    states[i][j] = states[i-1][0];
                } else {
                    states[i][j] = states[i-1][j];
                }
            }
        }

    }

    private String stackString(int [] state){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < state.length; i++){
            char c;
            if(state[i] < 10){
                c = (char)(state[i] + '0');
            } else {
                c = (char)(state[i] + 'a' - 10);
            }
            str.append(c);
        }
        return str.toString();
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i < states.length; i++){
            str += stackString(states[i]);
            if (i != states.length - 1){
                str += "|";
            }
        }
        return str;
    }
}

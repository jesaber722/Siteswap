import java.util.ArrayList;
import java.util.Arrays;

public class StateChain {

    public boolean [][] states;
    public int length;
    public int balls;

    private String stateString(boolean [] state){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < state.length; i++){
            if(state[i]){
                str.append("X");
            } else {
                str.append("-");
            }
        }
        return str.toString();
    }


    public StateChain(int [] nums){
        Siteswap siteswap = new Siteswap(nums);
        this.length = siteswap.length;
        this.balls = siteswap.balls;
        boolean loop = false;
        int index = 0;
        ArrayList<boolean[]> states = new ArrayList<>();
        boolean [] first = {true};
        int round = 1;
        states.add(first);
        while(!loop || index != siteswap.length - 1){
            int thro = siteswap.numbers[index];

            index++; index %= siteswap.length;

            boolean [] previous = states.get(round - 1);
            boolean [] state = new boolean[Math.max(previous.length - 1, thro)];

            for(int i = 1; i < previous.length; i++){
                state[i-1] = previous[i];
            }

            if(thro > 0)
                state[thro - 1] = true;

            for(int i = 0; i < states.size(); i++){
                if (Arrays.equals(states.get(i), state) && (i % length) == index){
                    loop = true;
                }
            }

            states.add(state);
            round ++;
        }
        this.states = new boolean[siteswap.length][0];
        for(int i = 0; i < siteswap.length; i++){
            this.states[i] = states.get(states.size() - siteswap.length + i);
        }

    }


    public StateChain(Siteswap siteswap){
        this.length = siteswap.length;
        this.balls = siteswap.balls;
        boolean loop = false;
        int index = 0;
        ArrayList<boolean[]> states = new ArrayList<>();
        boolean [] first = {true};
        int round = 1;
        states.add(first);
        while(!loop || index != siteswap.length - 1){
            int thro = siteswap.numbers[index];

            index++; index %= siteswap.length;

            boolean [] previous = states.get(round - 1);
            boolean [] state = new boolean[Math.max(previous.length - 1, thro)];

            for(int i = 1; i < previous.length; i++){
                state[i-1] = previous[i];
            }

            if(thro > 0)
                state[thro - 1] = true;

            for(int i = 0; i < states.size(); i++){
                if (Arrays.equals(states.get(i), state) && (i % length) == index){
                    loop = true;
                }
            }

            states.add(state);
            round ++;
        }
        this.states = new boolean[siteswap.length][0];
        for(int i = 0; i < siteswap.length; i++){
            this.states[i] = states.get(states.size() - siteswap.length + i);
        }

    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < states.length; i++){
            str.append(stateString(states[i]));
            if (i != states.length - 1)
                    str.append("\n");
        }
        return str.toString();
    }

    public Siteswap toSiteSwap(){
        int [] nums = new int[length];

        for(int i = 0; i < length; i++){
            boolean [] beforeState = states[i];
            boolean [] afterState = states[(i+1) % length];
            for(int j = afterState.length - 1; j >= 0; j--){
                if(afterState[j] && (beforeState.length <= j + 1 || !beforeState[j+1])){
                    nums[i] = j + 1;
                }
            }
            //System.out.println("bef: "+stateString(beforeState));
            //System.out.println("aft: "+stateString(afterState));
        }
        //System.out.print("num: ");
        /*
        for(int i = 0; i < length; i++){
            System.out.print(nums[i]);
        }
        System.out.println();
         */
        return new Siteswap(nums);
    }

    public static void main(String [] args){
        StateChain sg1 = new StateChain(new Siteswap(new int[]{5, 3, 1}));
        StateChain sg2 = new StateChain(new Siteswap(new int[]{7, 1}));
        StateChain sg3 = new StateChain(new Siteswap(new int[]{4, 2, 3}));
        StateChain sg4 = new StateChain(new Siteswap(new int[]{7, 4, 1}));
        StateChain sg5 = new StateChain(new Siteswap(new int[]{5}));
        System.out.println("done");
        //System.out.println(sg1.toString());
        //System.out.println();
        //System.out.println(sg2.toString());
        //System.out.println();
        System.out.println(sg3.balls);
        System.out.println(sg3.toString());
        System.out.println();
        System.out.println(sg4.balls);
        System.out.println(sg4.toString());
        System.out.println();
        System.out.println(sg5.balls);
        System.out.println(sg5.toString());
        System.out.println();
        System.out.println(new StateChain(new Siteswap(new int[]{1, 2, 3, 4, 5})).toString());
        System.out.println();
        System.out.println(sg4.toSiteSwap().toString());
        System.out.println(new StateChain(new Siteswap(new int[]{1, 2, 3, 4, 5})).toSiteSwap().toString());
    }

    public StackSwap toStackSwap(){
        int [] nums = new int[length];

        for(int i = 0; i < length; i++){
            boolean [] beforeState = states[i];
            boolean [] afterState = states[(i+1) % length];
            for(int j = afterState.length - 1; j >= 0; j--){
                if(afterState[j] && (beforeState.length <= j + 1 || !beforeState[j+1])){
                    nums[i] = j + 1;
                    int count = 0;
                    for(int k = 0; k < j; k++){
                        if(afterState[k]){
                            count ++;
                        }
                    }
                    nums[i] = count + 1;
                }
            }
            //System.out.println("bef: "+stateString(beforeState));
            //System.out.println("aft: "+stateString(afterState));
        }
        return new StackSwap(nums);
    }

    public Dichain toDichain(){
        return null;
    }
}

import java.util.Arrays;

public class State {
    final boolean [] state;

    public State(boolean [] state){
        this.state = state;
    }

    public int hashCode(){
        return Arrays.hashCode(state);
    }

    public boolean equals(Object other){
        if (other instanceof State){
            State friend = (State)other;
            return Arrays.equals(this.state, friend.state);
        }
        return false;
    }
}

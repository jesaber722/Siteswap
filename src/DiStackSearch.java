import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class DiStackSearch {


    public static ArrayList<Siteswap> found = new ArrayList<>();
    static boolean fileToggle = false;

    public static int search(HashSet<SymDistate> seen, ArrayList<Dithrow> thros, SymDistate start, SymDistate current){
        /*
        if(thros.size() < 40){
            System.out.print(thros.size());
        }

         */
        int count = 0;
        if(Math.random() < thros.size()*0.00){
            return 0;
        }
        if(Math.random() < 0.000002){
            System.out.println("size of found: " + found.size());
            try {
                BufferedWriter bufferedWriter;
                if(fileToggle) {
                    bufferedWriter = new BufferedWriter(new FileWriter(new File("temp")));
                } else {
                    bufferedWriter = new BufferedWriter(new FileWriter(new File("tomp")));
                }
                fileToggle = !fileToggle;
                //bufferedWriter.write("hello");
                bufferedWriter.write("SIZE: "+found.size());
                for(int i = 0; i < found.size(); i++)
                    bufferedWriter.write(found.get(i).toString() + "\n");
                bufferedWriter.close();
            } catch (IOException e){

            }
        }
        System.out.println("CURRENT: " + current);
        ArrayList<SymDistate.SymSuccRet> next = current.getSymSuccessors();
        Collections.shuffle(next);
        int badBranches = 0;
        boolean skipping = false;
        for(SymDistate.SymSuccRet successor: next){
            if(current.equals(start)){
                //System.out.println("RESUMING START");
            }
            if(successor.distate.equals(start)){
                thros.add(successor.dithrow);
                //System.out.print("win throw: " + successor.dithrow + "| ");
                Dithrow [] goodThrows = new Dithrow[thros.size()];

                for(int i = 0; i < goodThrows.length; i++){
                    goodThrows[i] = thros.get(i);
                }

                Dichain chain;

                if(successor.distate.strictEquals(start)){
                    chain = new Dichain(goodThrows, false);
                } else {
                    chain = new Dichain(goodThrows, true);
                }

                Siteswap ss = chain.toDisiteswap().smoothify().reduce().toSiteswap();
                //System.out.println("pre");
                //System.out.print("ss: " + ss + "| ");

                // simple of "prime" siteswap check
                if(ss.isSimple()){
                    boolean duplicate = false;

                    /*
                    //duplicate check
                    for(Siteswap already: found){
                        if(already.looseEquals(ss)){
                            duplicate = true;
                            break;
                        }
                    }
                     */


                    if(!duplicate) {
                        count ++;
                        System.out.println("ADDING " + ss);
                        found.add(ss);
                    }
                }
                thros.remove(thros.size() - 1);
            } else if(seen.contains(successor.distate)) {
                // pass
            } else {


                /*
                bad branch heuristic code
                I will be commenting this in and out a lot
                 */
                if(badBranches == 1){
                    if(Math.random() < 0.0){
                        continue;
                    }
                }

                if(badBranches >= 2999999){
                    // if the other branches yielded nothing, this probably won't either
                    //System.out.println("SKIPPING");
                    continue;
                }
                //System.out.println("New throw: " + successor.dithrow);
                thros.add(successor.dithrow);
                seen.add(successor.distate);
                int more = search(seen, thros, start, successor.distate);
                count += more;
                if(more == 0){

                    badBranches ++;
                }
                thros.remove(thros.size() - 1);
                seen.remove(successor.distate);
            }
        }
        return count;
        //System.out.println("DONE WITH: " + current);
    }

    public static void main(String [] args){
        SymDistate start = new SymDistate(new int[]{0,1,}, new int[]{2, });
        HashSet<SymDistate> seen = new HashSet<>();
        ArrayList<Dithrow> thros = new ArrayList<>();
        search(seen, thros, start, start);


        start = new SymDistate(new int[]{0, 1, 2}, new int[]{});
        seen = new HashSet<>();
        thros = new ArrayList<>();
        search(seen, thros, start, start);
        System.out.println("DONE - OUTPUTTING");
        /*
        start = new SymDistate(new int[]{0, 1, 2, 3}, new int[]{});
        seen = new HashSet<>();
        thros = new ArrayList<>();
        search(seen, thros, start, start);
        */

        System.out.print("DUPLICATE REMOVAL...");

        for(int i = 0; i < found.size(); i++){
            boolean duplicate = false;
            for(int j = 0; j < found.size(); j++){
                if(i == j){
                    continue;
                }
                if(found.get(j).looseEquals(found.get(i))){
                    duplicate = true;
                    break;
                }
            }
            if(duplicate){
                found.remove(i);
                i --;
            }
        }
        System.out.println(" DONE");

        for(int i = 0; i < found.size(); i++){
            if(found.get(i).balls < 3){
                found.remove(i);
                i --;
            } else if(found.get(i).balls > 3){
                throw new Siteswap.SiteswapException();
            }
        }
        System.out.println("\nFINAL:");
        System.out.println("SIZE: " + found.size());
        for(int i  = 0; i < found.size(); i++){
            System.out.println(found.get(i));
        }

        //System.out.println("DONE - OUTPUTTING");
        File file = new File("output");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            //bufferedWriter.write("hello");
            for(int i = 0; i < found.size(); i++)
                bufferedWriter.write(found.get(i).toString() + "\n");
            bufferedWriter.close();
        } catch (IOException e){

        }
    }
}

public class Disiteswap {

    Dithrow [] thros;
    boolean flip;

    public Disiteswap(Dithrow [] thros){
        int flux = 0;

        for(int i = 0; i < thros.length; i++){
            //System.out.println("flux: " + flux);
            if(thros[i].source == Dithrow.Side.LEFT && thros[i].dest == Dithrow.Side.RIGHT){
                flux ++;
            } else if(thros[i].source == Dithrow.Side.RIGHT && thros[i].dest == Dithrow.Side.LEFT){
                flux --;
            }
        }
        if(flux != 0){
            System.out.println(thros.length);
            throw new Siteswap.SiteswapException();
        }

        this.thros = thros;
        flip = false;
    }

    public Disiteswap(Dithrow [] thros, boolean flip){
        if(!flip){
            int flux = 0;

            for(int i = 0; i < thros.length; i++){
                if(thros[i].source == Dithrow.Side.LEFT && thros[i].dest == Dithrow.Side.RIGHT){
                    flux ++;
                } else if(thros[i].source == Dithrow.Side.RIGHT && thros[i].dest == Dithrow.Side.LEFT){
                    flux --;
                }
            }
            if(flux != 0){
                throw new Siteswap.SiteswapException();
            }
        }
        this.thros = thros;
        this.flip = flip;

    }

    public Disiteswap expand(){
        if(flip){
            Dithrow [] newThros = new Dithrow[thros.length * 2];
            for(int i = 0; i < thros.length * 2; i++){
                if (i < thros.length){
                    newThros[i] = thros[i];
                } else {
                    newThros[i] = thros[i - thros.length].mirror();
                }
            }
            return new Disiteswap(newThros);
        } else {
            return this;
        }
    }

    public Disiteswap compress(){
        if(!flip && thros.length % 2 == 0){
            for(int i = 0; i < thros.length / 2; i++){
                if(!thros[i].equals( thros[i + thros.length / 2].mirror())){
                    return this;
                }
            }
            // otherwise, it passed the test
            Dithrow [] newThros = new Dithrow[thros.length / 2];
            for(int i = 0; i < thros.length / 2; i++){
                newThros[i] = thros[i];
            }
            return new Disiteswap(newThros, true);
        } else {
            return this;
        }
    }

    public Disiteswap reduce(){
        Disiteswap chain = this.expand();


        for(int divisor = 1; divisor <= chain.thros.length / 2; divisor ++){
            if(chain.thros.length % divisor != 0){
                continue;
            }
            boolean success = true;
            breaklev:
            for(int residue = 0; residue < divisor; residue ++){
                for(int equiv = 0; residue + equiv*divisor < chain.thros.length; equiv ++){
                    if(!chain.thros[residue].equals( chain.thros[residue + equiv*divisor])){

                        success = false;
                        break breaklev;
                    }
                }
            }
            if(success){
                Dithrow [] newThrows = new Dithrow[divisor];
                for(int i = 0; i < newThrows.length; i++){
                    newThrows[i] = chain.thros[i];
                }
                //System.out.println("I am here");
                return new Disiteswap(newThrows).compress();
            }
        }
        return this.compress();
    }

    public boolean isPeriodic(){
        Disiteswap chain = this.expand();


        for(int divisor = 1; divisor <= chain.thros.length / 2; divisor ++){
            if(chain.thros.length % divisor != 0){
                continue;
            }
            boolean success = true;
            breaklev:
            for(int residue = 0; residue < divisor; residue ++){
                for(int equiv = 0; residue + equiv*divisor < chain.thros.length; equiv ++){
                    if(!chain.thros[residue].equals( chain.thros[residue + equiv*divisor])){
                        success = false;
                        break breaklev;
                    }
                }
            }
            if(success){
                return true;
            }
        }
        return false;
    }

    public boolean isSmooth(){
        for(int i = 0; i < thros.length - 1; i++){
            if(thros[i].source == thros[(i+1) % thros.length].source){
                return false;
            }
        }
        if(flip){
            return thros[0].source == thros[thros.length - 1].source;
        } else {
            return thros[0].source != thros[thros.length - 1].source;
        }
    }

    public Disiteswap smoothify(){
        if(flip){
            Disiteswap sw = this.expand();
            return sw.ensmoothen().compress();
        } else {
            return this.ensmoothen();
        }
    }

    private Disiteswap ensmoothen(){
        if(flip){
            throw new Siteswap.SiteswapException();
        }
        int problemIndex = -1;
        for(int i = 0; i < thros.length; i++){
            if(thros[i].source == thros[(i+1) % thros.length].source){
                problemIndex = i;
                break;
            }
        }

        if(problemIndex == -1){
            return this;
        }
        Dithrow [] newThros = new Dithrow[thros.length + 1];
        int [] crossingCount = new int[thros.length];

        for(int i = 0; i < crossingCount.length; i++){
            if(i <= problemIndex){
                crossingCount[i] = (thros[i].swap + i - problemIndex + thros.length - 1) / thros.length;
            } else {
                crossingCount[i] = (thros[i].swap + i - problemIndex - 1) / thros.length;
            }
        }

        for(int i = 0; i < thros.length; i++){
            if(i <= problemIndex){
                newThros[i] = new Dithrow(thros[i].source, thros[i].dest, thros[i].swap + crossingCount[i]);
            }
            else{
                newThros[i + 1] = new Dithrow(thros[i].source, thros[i].dest, thros[i].swap + crossingCount[i]);
            }
        }

        Dithrow.Side opposite = thros[problemIndex].source == Dithrow.Side.LEFT? Dithrow.Side.RIGHT: Dithrow.Side.LEFT;
        newThros[problemIndex + 1] = new Dithrow(opposite, opposite, 0);
        return new Disiteswap(newThros).ensmoothen();
    }

    public Siteswap toSiteswap(){
        if (!isSmooth()){
            throw new Siteswap.SiteswapException();
        } else {
            int [] nums = new int[thros.length];
            for(int i = 0; i < nums.length; i++){
                nums[i] = thros[i].swap;
            }
            return new Siteswap(nums);
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < thros.length; i++){
            str.append(thros[i].toString());
        }
        if(flip){
            str.append("*");
        }
        return str.toString();
    }

    public static void main(String [] args){
        Disiteswap ds1 = new Disiteswap(new Dithrow[]{
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 4),
                new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 0),
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 5),
                new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 0),
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 1),
                new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 4),
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.LEFT, 0),
                new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 10),
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.LEFT, 0),
                new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 4),
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 4),
                new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 4),
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 4),
                new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 4),
                new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 4),
        });
    }
}

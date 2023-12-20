public class Dichain {
    public boolean flip;
    public final Dithrow [] thros;

    public Dichain(Dithrow [] thros){
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

    public Dichain(Dithrow [] thros, boolean flip){
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

    public Dichain expand(){
        if(flip){
            Dithrow [] newThros = new Dithrow[thros.length * 2];
            for(int i = 0; i < thros.length * 2; i++){
                if (i < thros.length){
                    newThros[i] = thros[i];
                } else {
                    newThros[i] = thros[i - thros.length].mirror();
                }
            }
            return new Dichain(newThros);
        } else {
            return this;
        }
    }

    public Dichain compress(){
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
            return new Dichain(newThros, true);
        } else {
            return this;
        }
    }

    public Dichain reduce(){
        Dichain chain = this.expand();


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
                return new Dichain(newThrows).compress();
            }
        }
        return this.compress();
    }

    public boolean isPeriodic(){
        Dichain chain = this.expand();


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

    public Disiteswap toDisiteswap(){
        Dichain r = this.expand();
        //System.out.print("DEGUG r= " + r);
        Dithrow [] thros = r.thros;
        Dithrow [] newThros = new Dithrow[thros.length];
        for(int throIndex = 0; throIndex < thros.length; throIndex++){
            int height;
            if(thros[throIndex].source == thros[throIndex].dest) {
                height = thros[throIndex].swap;
            } else {
                height = thros[throIndex].swap + 1;
            }
            int index = (throIndex + 1) % thros.length;
            int score = 1;
            Dithrow.Side side = thros[throIndex].dest;
            while(true){
                //System.out.println("height " + height);
                if(thros[index].source == side && thros[index].dest == side){
                    if(height == 1){
                        if(thros[index].swap > 0)
                            break;
                    } else {
                        if(thros[index].swap >= height){
                            height --;
                        }
                    }
                } else if(thros[index].source == side && thros[index].dest != side){
                    if(height == 1){
                        break;
                    } else {
                        height --;
                    }
                } else if(thros[index].source != side && thros[index].dest == side){
                    if(thros[index].swap < height){
                        height ++;
                    }
                }
                score ++;
                index = (index + 1) % thros.length;
            }
            //System.out.println("SPACE");
            newThros[throIndex] = new Dithrow(thros[throIndex].source, thros[throIndex].dest, score);
        }
        if(flip) {
            return new Disiteswap(newThros).compress();
        } else {
            return new Disiteswap(newThros);
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("{");
        for(int i = 0; i < thros.length; i++){
            str.append(thros[i].toString());
            if(i != thros.length - 1){
                str.append(",");
            }
        }
        if(flip){
            str.append("*");
        }
        str.append("}");
        return str.toString();
    }

    public static void main(String [] args){
        //test 1
        Dichain chain1 = new Dichain(new Dithrow[]
                {
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 2),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 0),
        });
        System.out.println(chain1);
        System.out.println(chain1.toDisiteswap());
        System.out.println(chain1.isPeriodic());
        //test 2
        Dichain chain2 = new Dichain(new Dithrow[]
                {
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 2),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 0),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 2),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 0),
                });
        System.out.println(chain2);
        System.out.println(chain2.toDisiteswap());
        System.out.println(chain2.isPeriodic());
        //test 3


        Dichain chain3 = new Dichain(new Dithrow[]
                {
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 1),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 0),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3).mirror(),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 1).mirror(),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 0).mirror(),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 1),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 0),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3).mirror(),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 1).mirror(),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 0).mirror(),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 1),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 0),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3).mirror(),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.RIGHT, 1).mirror(),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 0).mirror(),

                });


        System.out.println(new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 3).mirror());
        System.out.println(chain3.isPeriodic());
        System.out.println(chain3.reduce());
        System.out.println(chain3.reduce().expand());

        // test 4

        System.out.println();

        Dichain chain4 = new Dichain(new Dithrow[]
                {
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 1),
                        new Dithrow(Dithrow.Side.RIGHT, Dithrow.Side.LEFT, 1),
                        new Dithrow(Dithrow.Side.LEFT, Dithrow.Side.RIGHT, 0),

                }, true);

        System.out.println(chain4.toDisiteswap());
        System.out.println(chain4.toDisiteswap().isSmooth());
        System.out.println(chain4.toDisiteswap().smoothify());
        System.out.println(chain4.toDisiteswap().smoothify().toSiteswap());
        System.out.println(chain4.toDisiteswap().smoothify().toSiteswap().isSimple());
    }
}

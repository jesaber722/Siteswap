public class Dichain {
    public boolean swich;
    public final Dithrow [] thros;

    public Dichain(Dithrow [] thros){
        this.thros = thros;
        swich = false;
    }

    public Dichain(Dithrow [] thros, boolean swich){
        this.thros = thros;
        this.swich = swich;
    }

    public Dichain expand(){
        if(swich){
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
        if(!swich && thros.length % 2 == 0){
            for(int i = 0; i < thros.length / 2; i++){
                if(thros[i] != thros[i + thros.length / 2].mirror()){
                    return this;
                }
            }
            // otherwise, it passed the test
            Dithrow [] newThros = new Dithrow[thros.length / 2];
            for(int i = 0; i < thros.length / 2; i++){
                newThros[i] = thros[i];
            }
            return new Dichain(thros, true);
        } else {
            return this;
        }
    }

    public Dichain reduce(){
        Dichain chain = this.expand();


        for(int divisor = 1; divisor < Math.ceil(Math.sqrt(chain.thros.length)); divisor ++){
            if(chain.thros.length % divisor != 0){
                continue;
            }
            boolean success = true;
            breaklev:
            for(int residue = 0; residue < divisor; residue ++){
                for(int equiv = 0; residue + equiv*divisor < chain.thros.length; equiv ++){
                    if(chain.thros[residue] != chain.thros[residue + equiv*divisor]){
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
                return new Dichain(newThrows).compress();
            }
        }
        return this;
    }

    public Disiteswap toDisiteswap(){
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
        }
    }
}

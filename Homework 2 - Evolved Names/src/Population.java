// Grant Schorbach
// TCSS 342 - Data Structures

import java.util.ArrayList;
import java.util.Collections;

public class Population {

    public Genome mostFit;
    public ArrayList<Genome> population;
    double muteRate;

    /* Initializes a Population with a number of default genomes. **/
    Population(Integer numGenomes, double mutationRate){
        population = new ArrayList<>(numGenomes);
        muteRate = mutationRate;
        for(int i = 0; i < numGenomes; i++){
            population.add(new Genome(muteRate));
        }
        mostFit = population.get(0);
    }

    /* Called every breeding cycle **/
    /* 1) mostFit
     * 2) delete the least fit half of the population
     * 3) create new genomes from the remaining population until the number of genomes
     * is restores by doing either of the following:
     *  A) pick a remaining genome at random and clone it and mutate the clone.
     *  B) pick a remaining genome at random and clone it and then crossover the clone
     *  with another remaining genome selected at random and then mutate the result.
     **/
    public void day(){
//      make sure your compare to is biggest to smallest
        Collections.sort(population);

        mostFit = population.get(0);
        final int popSize = population.size();

        while(population.size() > popSize/2){
            population.remove(popSize/2);
//            population.remove(population.size()-1);
        }
        while(population.size() < popSize){
            if(Main.RANDOM.nextInt(2)== 0) {
                Genome clone = new Genome(population.get(Main.RANDOM.nextInt(popSize/2)));
                clone.mutate();
                population.add(clone);
            } else {
                Genome cloneOne = new Genome(population.get(Main.RANDOM.nextInt(popSize/2)));
                Genome cloneTwo = new Genome(population.get(Main.RANDOM.nextInt(popSize/2)));
                cloneOne.crossover(cloneTwo);
                cloneOne.mutate();
                population.add(cloneOne);
            }
        }
    }

// This is the toString which will give us our output in to our main.
    public String toString(){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < population.size(); i++){
            output.append(/*i + ": " +*/ this.population.get(i));
        }
        return output.toString();
    }
}

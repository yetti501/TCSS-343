// Grant Schorbach
// TCSS 342 - Data Structures

import java.util.Random;

public class Main {

    public static final Integer  HUNDRED = 100;
    public static final Double FIVEPERCENT = 0.05;
    public static Random RANDOM = new Random();

    public static void main(String[] args) {
        System.out.println("Evolve Name");
// This is where the first population begins. From here it will begin the mutation.
        int generations = 0;
        Population population = new Population(HUNDRED, FIVEPERCENT);
// This is where the time starts to tick to the total time propagation
        long startTime = System.currentTimeMillis();
        while(generations <= 10000 && population.mostFit.fitness() != 0){
// Sending the population to the day method.
            population.day();
            System.out.println("Generations: " + generations + " " + population.mostFit.toString());
// Generation counter
            generations++;
        }
// End of the propagation clock.
        long endTime = System.currentTimeMillis();
// Calculating for the total time.
        long totalTime = endTime - startTime;
// Giving you the total time propagation.
        System.out.println("Total Time: " + totalTime + " mS");

//        testGenome();
//        testPopulation();
    }
// Genome testing method.
    private static void testGenome(){
// You will need two different genomes because you need to mutate them and at some point you will need
// to send them to the crossover method which takes two genomes.
        Genome testone = new Genome(0.05);
        Genome testtwo = new Genome(0.05);
// This will mutate them 1000s to get a result that they are changing with time.
        for(int i = 0; i< 1000; i++){
            testone.mutate();
            testtwo.mutate();
            testone.crossover(testtwo);
            System.out.println("STRING 1: " + testone.toString() + " " +
                    "STRING 2: " + testtwo.toString() +
                    testone.compareTo(testtwo));
        }
    }
// Population test.
    private static void testPopulation() {
// This will show that the genomes do actually change fitness over time.
        Population testPopulation = new Population (HUNDRED, FIVEPERCENT);
        for(int i = 0; i < 1000; i++) {
            System.out.println(testPopulation.mostFit.toString());
            testPopulation.day();
            System.out.println(testPopulation.mostFit.toString());
        }
    }
}

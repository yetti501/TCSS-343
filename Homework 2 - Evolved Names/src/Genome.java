// Grant Schorbach
// TCSS 342 - Data Structures

/**
 * You can use .equaleIgnoreCase();
 */
public class Genome implements Comparable<Genome> {
// Making the character set that we are going to be pulling variables from. This is also out alphabet.
    public static final char[] charSet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', ' ', '-', '\''};
// This is the mutation rate that we are getting from the main method.
    double mutationRate;
// This is the target string that we are trying to get to.
    private static final String target = "CHRISTOPHER PAUL MARRIOTT";
// This is the variable that we will be mutating throughout this code.
    public StringBuilder name;

    /** Gives a rate between 0 and 1 as a unit of mutation. */
    Genome(double mutationRate){
        this.mutationRate = mutationRate;
        name = new StringBuilder("A");
    }
    /** Copies the Genome. */
    Genome(Genome gene) {
        this.mutationRate = gene.mutationRate;
        name = new StringBuilder(gene.name);
    }

    /** Will move the  string to one of the following classes. */
    public void mutate(){
        double one = Main.RANDOM.nextDouble();
        if(this.mutationRate >= one){
            add();
        }
        double two = Main.RANDOM.nextDouble();
        if(this.mutationRate >= two) {
            delete();
        }
    /** This will change a character in the string. */
        change();
    }

    /** Adds a new character somewhere in the string. */
    private void add(){
        name.insert(Main.RANDOM.nextInt(name.length() + 1), charSet[Main.RANDOM.nextInt(29)]);
    }

    /** Removes a character from the string at random. */
    private void delete(){
        if(name.length() > 2){
            name.deleteCharAt(Main.RANDOM.nextInt(name.length()));
        }
    }

    /** Change a character in a string to another character. */
    private void change(){
        int local = Main.RANDOM.nextInt(name.length());
        name.deleteCharAt(local);
        name.insert(local, charSet[Main.RANDOM.nextInt(29)]);

        for(int i = 0; i <= name.length()-1; i++) {
            double one = Main.RANDOM.nextDouble();
            if(this.mutationRate >= one) {
                name.setCharAt(i, charSet[Main.RANDOM.nextInt(name.length())]);
            }
        }
    }

    /** Will create a third string based off two other strings. */
    public void crossover(Genome other){
        int i = 0;
        StringBuilder output = new StringBuilder("");
        boolean flag = true;
        while(flag){
            int choice = Main.RANDOM.nextInt(2);
            if(choice == 0) {
                if(i < other.name.length()){
                    output.append(other.name.charAt(i));
                } else {
                    flag= false;
                }
            } else {
                if(i < name.length()) {
                    output.append(name.charAt(i));
                } else {
                    flag = false;
                }
            }
            i++;
        }
        this.name = output;
    }

    /** This will get the fitness of the String(s). */
    /** (Optional) Levenshtein */
    public Integer fitness(){
        int max = Math.max(name.length(), target.length());
        int min = Math.min(name.length(), target.length());
        int fitness = Math.abs(max-min);

        for(int i = 0; i < max; i++){
            if(i < min){
                if(name.charAt(i) != target.charAt(i)){
                    fitness++;
                }
            } else {
                fitness++;
            }
        }

//   Wagner Fischer Algorithm
//   Found some pseudo code off the Wiki page that was this format.
//**********************************************************************************//
//        int fitness;
//        int nLength = name.length();
//        int tLength = target.length();
//        int temp [][] = new int [nLength + 1][tLength + 1];
//
//        for(int i = 0; i < tLength + 1; i++){
//            temp[0][i] = i;
//        }
//        for(int j = 0; j < nLength + 1; j++){
//            temp[j][0] = j;
//        }
//        for(int k = 1; k < nLength + 1; k++){
//            for(int l = 1; l < tLength + 1; l++){
//                if(name.charAt(k-1) == target.charAt(l-1)){
//                    temp[k][l] = temp[k-1][l-1];
//                } else {
//                    temp[k][l] = Math.min(temp[k-1][l] + 1, Math.min(temp[k][l-1] + 1, temp[k-1][l-1] + 1));
//                }
//            }
//        }
//        fitness = (temp [nLength][tLength] + (Math.abs(nLength-tLength)+1)/2);
// **********************************************************************************//
        return fitness;
    }

    /** This will display the String and its fitness. */
    public String toString(){
        return name.toString() + " Fitness: " + this.fitness();
    }
// This is the comparable string that will help us organize our genomes when we decide
// what portion of the population we want to kill off.
    @Override
    public int compareTo(Genome genome){
        if(fitness() > genome.fitness()){
            return 1;
        }
        if(fitness() < genome.fitness()){
            return -1;
        }
        return 0;
    }
}

package tisch.evolution;

/**
 * Configuration object holds configuration for the optimizer class.
 */
public class Configuration {
    private final double MAX_LENGTH_FACTOR;
    private final int generationSize;
    private final int maxGenerations;
    private final double fitnessGoal;
    private final int tournamentSize;
    private final double MUTATION_RATE;


    public Configuration(int generationSize, int maxGenerations, double fitnessGoal, double maxLength, double mutationRate, int tournamentSize) {
        this.fitnessGoal = fitnessGoal;
        this.generationSize = generationSize;
        this.maxGenerations = maxGenerations;
        this.tournamentSize = tournamentSize;
        this.MAX_LENGTH_FACTOR = maxLength;
        this.MUTATION_RATE = mutationRate;
    }

    public int getSTANDARD_DEVIATION() {
        return 10;
    }

    public int getMEDIAN() {
        return 100;
    }

    public int getGenerationSize() {
        return generationSize;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public double getFitnessGoal() {
        return fitnessGoal;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public double getMUTATION_RATE() {
        return MUTATION_RATE;
    }

    public double getMAX_LENGTH_FACTOR() {
        return MAX_LENGTH_FACTOR;
    }
}

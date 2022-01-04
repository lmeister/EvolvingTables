package tisch.evolution;

import tisch.evolution.crossover.AbstractCrossOverer;
import tisch.evolution.evaluation.AbstractEvaluator;
import tisch.evolution.population.Table;

import java.util.*;

/**
 * Optimizer Class is the core of the application.
 * Performs the optimization loop.
 */
public class Optimizer {

    private final Configuration configuration;
    private final AbstractEvaluator evaluator;
    private final AbstractCrossOverer crossOverer;

    private Map<Table, Double> tableFitnessMap;


    /**
     * Standard optimizer constructor.
     * @param evaluator Evaluator
     * @param crossOverer CrossOverer
     * @param configuration Configuration
     */
    public Optimizer(AbstractEvaluator evaluator, AbstractCrossOverer crossOverer, Configuration configuration) {
        this.configuration = configuration;
        this.evaluator = evaluator;
        this.crossOverer = crossOverer;
    }

    /**
     * Performs the optimization loop.
     * Loop is stopped as soon as fitness goal or maximum amount of generation is reached.
     * @return A list with the fittest table of each generation.
     */
    public List<Table> optimize() {
        List<Table> bestTables = new ArrayList<>();
        Optional<Table> result = Optional.empty();
        // Startpopulation generieren & evaluieren
        tableFitnessMap = generateInitialPopulation();

        Table bestTableOfThisGeneration = findBestTable();
        if (isGoalMet(bestTableOfThisGeneration)) {
            bestTables.add(bestTableOfThisGeneration);
            return bestTables;
        }

        for (int i = 0; i < configuration.getMaxGenerations(); i++) {
            // Neue Generation bilden & evaluieren
            tableFitnessMap = evolvePopulation();

            bestTableOfThisGeneration = findBestTable();
            bestTables.add(bestTableOfThisGeneration);
            if (isGoalMet(bestTableOfThisGeneration)) {
                return bestTables;
            }
        }
        return bestTables;
    }

    private boolean isGoalMet(Table table) {
        return tableFitnessMap.get(table) == configuration.getFitnessGoal();
    }

    /**
     * finds best table of the current generation.
     * @return Best table of the generation.
     */
    private Table findBestTable() {
        return Collections.min(tableFitnessMap.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    /**
     * Generates a randomly generated initial population.
     * Leg lengths are picked according to standard deviation.
     * @return Map consisting
     */
    private Map<Table, Double> generateInitialPopulation() {
        Random random = new Random();
        Map<Table, Double> initialPopulation = new HashMap<>();
        for (int i = 0; i < configuration.getGenerationSize(); i++) {
            int leg1 = (int) (Math.round(random.nextGaussian() * Math.sqrt(configuration.getSTANDARD_DEVIATION())) + configuration.getMEDIAN());
            int leg2 = (int) (Math.round(random.nextGaussian() * Math.sqrt(configuration.getSTANDARD_DEVIATION())) + configuration.getMEDIAN());
            int leg3 = (int) (Math.round(random.nextGaussian() * Math.sqrt(configuration.getSTANDARD_DEVIATION())) + configuration.getMEDIAN());
            int leg4 = (int) (Math.round(random.nextGaussian() * Math.sqrt(configuration.getSTANDARD_DEVIATION())) + configuration.getMEDIAN());
            Table table = new Table(leg1, leg2, leg3, leg4);
            initialPopulation.put(table, this.evaluator.evaluateFitness(table));
        }

        return initialPopulation;
    }

    /**
     * Creates a new generation.
     * Until the new generation has increased size: Pick 2 parents by tournament selection, then generates offspring.
     * @return new generation as map.
     */
    private Map<Table, Double> evolvePopulation() {
        Map<Table, Double> newGeneration = new HashMap<>();
        while (newGeneration.size() < configuration.getGenerationSize()) {
            // Select parents
            Table parent1 = tournamentSelection();
            Table parent2 = tournamentSelection();


            Table offspring = this.crossOverer.generateOffspring(parent1, parent2);
            double fitnessOfOffspring = this.evaluator.evaluateFitness(offspring);
            newGeneration.put(offspring, fitnessOfOffspring);
        }


        return newGeneration;
    }

    /**
     * Performs tournament selection.
     * Picks out random individuals and then chooses the fittest of this subset.
     * @return Fittest table of the tournament.
     */
    private Table tournamentSelection() {
        List<Table> allTables = new ArrayList<>(tableFitnessMap.keySet());
        Collections.shuffle(allTables);
        List<Table> tournamentParticipants;

        // If tournament size is greater than generation size, simply use entire generation
        if (configuration.getTournamentSize() > allTables.size() - 1) {
            tournamentParticipants = new ArrayList<>(allTables);
        } else {
            tournamentParticipants = allTables.subList(0, configuration.getTournamentSize());
        }


        Table best = tournamentParticipants.get(0);
        for (Table table : tournamentParticipants) {
            if (tableFitnessMap.get(table) < tableFitnessMap.get(best)) {
                best = table;
            }
        }

        return best;
    }
}

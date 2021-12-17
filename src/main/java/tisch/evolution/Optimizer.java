package tisch.evolution;

import tisch.evolution.crossover.AbstractCrossOverer;
import tisch.evolution.evaluation.AbstractEvaluator;
import tisch.evolution.population.Table;

import java.util.*;

public class Optimizer {

    // TODO Create Configuration class for all this?
    private final Configuration configuration;
    private final AbstractEvaluator evaluator;
    private final AbstractCrossOverer crossOverer;

    private Map<Table, Double> tableFitnessMap;


    public Optimizer(AbstractEvaluator evaluator, AbstractCrossOverer crossOverer, Configuration configuration) {
        this.configuration = configuration;
        this.evaluator = evaluator;
        this.crossOverer = crossOverer;
    }


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

    private Table findBestTable() {
        return Collections.min(tableFitnessMap.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

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

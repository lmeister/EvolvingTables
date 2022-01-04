package tisch.evolution.crossover;

import tisch.evolution.mutation.AbstractMutator;
import tisch.evolution.population.Table;

import java.util.Random;

/**
 * Abstract CrossOverer Class.
 * Defines interfaces
 */
public abstract class AbstractCrossOverer {
    protected static double mutationRate;
    protected Random random = new Random();
    protected AbstractMutator mutator;

    /**
     * Generates offspring from given parents. Actual Behavior defined in inheriting classes.
     * Always has a chance to mutate the child. Mutation behavior is specified in mutator classes.
     * @param parent1 Parent 1 Table
     * @param parent2 Parent 2 Table
     * @return A new table object
     */
    public Table generateOffspring(Table parent1, Table parent2) {
        // generate child
        Table child = crossover(parent1, parent2);

        // possibly mutate child
        if (this.random.nextDouble() <= mutationRate) {
            child = mutator.mutate(child);
        }

        return child;
    }

    /**
     * Defines crossover behavior.
     * @param parent1 Parent 1
     * @param parent2 Parent 2
     * @return Child Table
     */
    protected abstract Table crossover(Table parent1, Table parent2);
}

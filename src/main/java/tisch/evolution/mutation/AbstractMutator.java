package tisch.evolution.mutation;

import tisch.evolution.population.Table;

import java.util.Random;

/**
 * Defines interface for mutator classes.
 */
public abstract class AbstractMutator {

    protected final Random rnd = new Random();

    /**
     * Mutates a table according to behavior specified in inheriting classes.
     * @param table table to be mutated
     * @return mutated table
     */
    public abstract Table mutate(Table table);
}

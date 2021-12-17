package tisch.evolution.evaluation;

import tisch.evolution.population.Table;

public abstract class AbstractEvaluator {

    /**
     * Evaluates fitness of given table.
     * Fitness function is defined in extending classes.
     * @param table the table that is to be evaluated
     * @return fitness
     */
    public abstract double evaluateFitness(Table table);
}

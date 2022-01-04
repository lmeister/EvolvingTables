package tisch.evolution.evaluation;

import tisch.evolution.population.Table;

/**
 * AbstractEvaluator defines Interfaces for Evaluator classes.
 * Evaluator classes implement certain behavior to judge fitness of individuals.
 */
public abstract class AbstractEvaluator {

    /**
     * Evaluates fitness of given table.
     * Fitness function is defined in extending classes.
     * @param table the table that is to be evaluated
     * @return fitness
     */
    public abstract double evaluateFitness(Table table);
}

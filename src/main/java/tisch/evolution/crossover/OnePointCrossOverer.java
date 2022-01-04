package tisch.evolution.crossover;

import tisch.evolution.mutation.AbstractMutator;
import tisch.evolution.population.Table;

/**
 * OnePointCrossOverer class implements One Point Crossover Technique.
 */
public class OnePointCrossOverer extends AbstractCrossOverer {

    /**
     * Constructor for OnePointCrossOverer Object.
     * @param mutationRate Chance for mutation
     * @param mutator Concrete mutator
     */
    public OnePointCrossOverer(double mutationRate, AbstractMutator mutator) {
        AbstractCrossOverer.mutationRate = mutationRate;
        this.mutator = mutator;
    }

    /**
     * Will either take all even legs from parent 1 and odd legs from parent 2 or vice versa.
     * @param parent1 Parent 1
     * @param parent2 Parent 2
     * @return Child table
     */
    @Override
    protected Table crossover(Table parent1, Table parent2) {
        // if it is true even legs from parent1 and odd legs from parent2 will taken for crossover.
        // And the other way around
        boolean evenCross = this.random.nextBoolean();
        Table child;

        if (evenCross) {
            child = new Table(parent2.getLeg1(), parent1.getLeg2(), parent2.getLeg3(), parent1.getLeg4());
        } else {
            child = new Table(parent1.getLeg1(), parent2.getLeg2(), parent1.getLeg3(), parent2.getLeg4());
        }

        return child;
    }
}

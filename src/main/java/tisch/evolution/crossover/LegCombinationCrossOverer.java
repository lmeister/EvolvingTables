package tisch.evolution.crossover;

import tisch.evolution.mutation.AbstractMutator;
import tisch.evolution.population.Table;


/**
 * LegCombinationCrossOverer generates offspring by randomly selecting legs from parents.
 */
public class LegCombinationCrossOverer extends AbstractCrossOverer{

    /**
     * Constructor for LegCombinationCrossOverer.
     * @param mutationRate Chance for mutation
     * @param mutator Concrete mutator
     */
    public LegCombinationCrossOverer(double mutationRate, AbstractMutator mutator) {
        AbstractCrossOverer.mutationRate = mutationRate;
        this.mutator = mutator;
    }


    /**
     * Randomly selects legs from parents to construct a new table.
     * @param parent1 Parent 1
     * @param parent2 Parent 2
     * @return New Child (Table)
     */
    @Override
    protected Table crossover(Table parent1, Table parent2) {
        Table child = new Table(parent1);
        for (int i = 0; i < 4; i++) {
            if (random.nextDouble() <= 0.5) {
                if (i == 0) {
                    child.setLeg1(parent2.getLeg1());
                } else if (i == 1) {
                    child.setLeg2(parent2.getLeg2());
                } else if (i == 2) {
                    child.setLeg3(parent2.getLeg3());
                } else {
                    child.setLeg4(parent2.getLeg4());
                }
            }
        }
        return child;
    }
}

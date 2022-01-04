package tisch.evolution.crossover;

import tisch.evolution.mutation.AbstractMutator;
import tisch.evolution.population.Table;

public class OnePointCrossoverer extends AbstractCrossOverer {

    public OnePointCrossoverer(double mutationRate, AbstractMutator mutator) {
        AbstractCrossOverer.mutationRate = mutationRate;
        this.mutator = mutator;
    }

    @Override
    public Table generateOffspring(Table parent1, Table parent2) {
        // generate child
        Table child = crossover(parent1, parent2);

        // possibly mutate child
        if (this.random.nextDouble() <= mutationRate) {
            child = mutator.mutate(child);
        }

        return child;
    }

    private Table crossover(Table parent1, Table parent2) {
        // if it is true even legs from parent1 and uneven legs from parent2 will taken for crossover.
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

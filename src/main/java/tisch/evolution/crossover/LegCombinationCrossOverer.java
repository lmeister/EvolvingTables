package tisch.evolution.crossover;

import tisch.evolution.mutation.AbstractMutator;
import tisch.evolution.population.Table;

import java.util.Random;

public class LegCombinationCrossOverer extends AbstractCrossOverer{

    public LegCombinationCrossOverer(double mutationRate, AbstractMutator mutator) {
        AbstractCrossOverer.mutationRate = mutationRate;
        this.mutator = mutator;
    }

    @Override
    public Table generateOffspring(Table parent1, Table parent2) {

        // First generate child
        Table child = crossover(parent1, parent2);

        // Possibly mutate child
        if (this.random.nextDouble() <= mutationRate) {
            child = mutator.mutate(child);
        }

        return child;
    }


    private Table crossover(Table parent1, Table parent2) {
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

package tisch.evolution.mutation;

import tisch.evolution.population.Table;

import java.util.ArrayList;
import java.util.Collections;

public class HeuristicMutator extends AbstractMutator {

    /**
     * Sets the length of the strongest deviating leg to the median length of all other legs.
     * * CAREFUL: Sometimes does not terminate with tournament size >= 70% (?) of population with low population sizes (Local max?)
     * @param table table to be mutated
     * @return mutated table
     */
    @Override
    public Table mutate(Table table) {
        Table copy = new Table(table);

        int strongestDeviatingLeg = 0;
        int strongestDeviation = Integer.MIN_VALUE;
        // Find leg that deviates most
        for (int i = 0; i < 4; i++) {
            int deviation = 0;

            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    deviation += Math.abs(copy.getLegFromNumber(i) - copy.getLegFromNumber(j));
                }
            }

            if (deviation > strongestDeviation) {
                strongestDeviatingLeg = i;
            }
        }

        ArrayList<Integer> legs = new ArrayList<>(copy.getLegList());
        legs.remove(strongestDeviatingLeg);
        Collections.sort(legs);
        int median = legs.get(1); // Since we have 3 elements its always on position 1

        copy.setLegFromNumber(strongestDeviatingLeg, median);

        return copy;
    }
}

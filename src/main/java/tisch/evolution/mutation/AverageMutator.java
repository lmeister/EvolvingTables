package tisch.evolution.mutation;

import tisch.evolution.population.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AverageMutator extends AbstractMutator {

    /**
     * Sets the length of a randomly selected leg to the average length of all other legs.
     * CAREFUL: Sometimes does not terminate with tournament size >= 70% of population with low population sizes (Local max?)
     * @param table table to be mutated
     * @return mutated table
     */
    @Override
    public Table mutate(Table table) {
        Table copy = new Table(table);
        int legNumber = this.rnd.nextInt(4);

        ArrayList<Integer> legs = new ArrayList<>(copy.getLegList());
        int indexOfThisLeg = 0;
        for (int i = 0; i < legs.size(); i++) {
            if (legs.get(i) == copy.getLegFromNumber(legNumber)) {
                indexOfThisLeg = i;
                break;
            }
        }
        legs.remove(indexOfThisLeg);

        // Todo box operator?
        int average = (int) Math.round(legs.stream().mapToInt(e -> e).average().orElse(0));

        copy.setLegFromNumber(legNumber, average);

        return copy;
    }
}

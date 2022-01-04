package tisch.evolution.mutation;

import tisch.evolution.population.Table;

import java.util.Arrays;

public class AverageMutator extends AbstractMutator {

    @Override
    public Table mutate(Table table) {
        Table copy = new Table(table);
        int legNumber = this.rnd.nextInt(4);

        int[] legArray = copy.getLegsAsArray();
        int average = (int) Math.round(Arrays.stream(legArray).average().orElse(0));

        copy.setLegFromNumber(legNumber, average);

        return copy;
    }
}

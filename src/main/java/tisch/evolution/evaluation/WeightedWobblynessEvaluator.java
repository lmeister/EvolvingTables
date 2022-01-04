package tisch.evolution.evaluation;

import tisch.evolution.population.Table;

import java.util.ArrayList;

public class WeightedWobblynessEvaluator extends AbstractEvaluator {

    /**
     * Sums up the deviation of each leg to every other leg.
     * @param table the table that is to be evaluated
     * @return Fitness. High fitness is worse than lower fitness.
     */
    @Override
    public double evaluateFitness(Table table) {
        // TODO: Table with 90,90,90,94 is as bad as 89,90,91,92 ? Not really.
        // Irgendwie gewichten potentiell? in kombination mit "heuristischem" Mutator? -> Mutiere das bein, das am meisten abweicht?
        // (4 * (1/3)) + (4 * (1/3)) + (4 * (1/3)) + 12 = 16
        // 6 + 6 + 6 + 6 = 24

        ArrayList<Integer> legs = new ArrayList<>(table.getLegList());

        int fitness = 0;
        // for each leg
        for (int i = 0; i < 4; i++) {
            int deviation = 0;
            double factor = 0.0;

            // Get deviation to each other leg + get factor
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    if (!legs.get(i).equals(legs.get(j))) {
                        deviation += Math.abs(legs.get(i) - legs.get(j));
                        factor += 1.0 / 3.0;
                    }
                }
            }
            // Calculate weighted deviation
            fitness += deviation * factor;
        }

        return fitness;
    }

}


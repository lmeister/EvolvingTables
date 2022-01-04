package tisch.evolution.evaluation;

import tisch.evolution.population.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * WobblynessEvaluator judges Tables by how wobbly they are.
 */
public class WobblynessEvaluator extends AbstractEvaluator {

    /**
     * Sums up the deviation of each leg to every other leg.
     * @param table the table that is to be evaluated
     * @return Fitness. High fitness is worse than lower fitness.
     */
    @Override
    public double evaluateFitness(Table table) {
        // TODO: Table with 90,90,90,94 is as bad as 89,90,91,92 ? Not really.
        // Irgendwie gewichten potentiell? in kombination mit "heuristischem" Mutator? -> Mutiere das bein, das am meisten abweicht?
        ArrayList<Integer> b = new ArrayList<>(table.getLegList());
        double result = 0;
        for (int i = 0; i < 4; i++) {
            for (int b_i : b) {
                result += Math.abs(b.get(i) - b_i);
            }
        }
        return result;
    }
}

package tisch.evolution.evaluation;

import tisch.evolution.population.Table;

public class WobblynessEvaluator extends AbstractEvaluator {

    @Override
    public double evaluateFitness(Table table) {
        int[] b = table.getLegsAsArray();
        double result = 0;
        for (int i = 0; i < 4; i++) {
            for (int b_i : b) {
                result += Math.abs(b[i] - b_i);
            }
        }
        return result;
    }
}

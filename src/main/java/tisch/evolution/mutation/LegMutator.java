package tisch.evolution.mutation;

import tisch.evolution.population.Table;

public class LegMutator extends AbstractMutator {
    // TODO Hier einen ordentlichen Wert finden - Was Prozentuales?
    private static double MAX_LENGTH_FACTOR;

    public LegMutator(double maxLength) {
        MAX_LENGTH_FACTOR = maxLength;
    }

    /**
     * Mutates a random leg by either cutting/adding some random length within deviation bounds.
     * @param table table to be mutated
     * @return Mutated table
     */
    @Override
    public Table mutate(Table table) {
        Table copy = new Table(table);
        int leg = this.rnd.nextInt(4);

        // Todo refactor
        if (leg == 0) {
            int length = this.rnd.nextInt((int) (MAX_LENGTH_FACTOR * copy.getLeg1()));
            // if smaller than .5 shorten leg
            if (this.rnd.nextDouble() <= 0.5) {
                length *= -1;
            }
            copy.setLeg1(copy.getLeg1() + length);
        } else if (leg == 1) {
            int length = this.rnd.nextInt((int) (MAX_LENGTH_FACTOR * copy.getLeg2()));
            // if smaller than .5 shorten leg
            if (this.rnd.nextDouble() <= 0.5) {
                length *= -1;
            }
            copy.setLeg2(copy.getLeg3() + length);
        } else if (leg == 2) {
            int length = this.rnd.nextInt((int) (MAX_LENGTH_FACTOR * copy.getLeg3()));
            // if smaller than .5 shorten leg
            if (this.rnd.nextDouble() <= 0.5) {
                length *= -1;
            }
            copy.setLeg3(copy.getLeg3() + length);
        } else {
            int length = this.rnd.nextInt((int) (MAX_LENGTH_FACTOR * copy.getLeg4()));
            // if smaller than .5 shorten leg
            if (this.rnd.nextDouble() <= 0.5) {
                length *= -1;
            }
            copy.setLeg4(copy.getLeg4() + length);
        }

        return copy;
    }
}

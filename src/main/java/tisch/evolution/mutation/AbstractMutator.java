package tisch.evolution.mutation;

import tisch.evolution.population.Table;

import java.util.Random;

public abstract class AbstractMutator {

    protected final Random rnd = new Random();

    public abstract Table mutate(Table table);
}

package tisch;

import tisch.evolution.Configuration;
import tisch.evolution.Optimizer;
import tisch.evolution.crossover.AbstractCrossOverer;
import tisch.evolution.crossover.LegCombinationCrossOverer;
import tisch.evolution.evaluation.AbstractEvaluator;
import tisch.evolution.evaluation.WobblynessEvaluator;
import tisch.evolution.gui.MainWindow;
import tisch.evolution.mutation.AbstractMutator;
import tisch.evolution.mutation.LegMutator;
import tisch.evolution.population.Table;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Create Gui
        MainWindow mainWindow = new MainWindow("Genetically evolving the Perfect Table");
        mainWindow.setVisible(true);



//        //Optimizer optimizer = new Optimizer();
//        Random random = new Random();
//
//        Map<Integer, Integer> frequencyMap = new HashMap<>();
//        for (int i = 0; i < 10000; i++) {
//            int value = (int) Math.round(random.nextGaussian() * Math.sqrt(10)) + 100;
//            //double value = random.nextGaussian();
//            //System.out.println(value);
//            frequencyMap.merge(value, 1, Integer::sum);
//        }
//
//        for (int i = 80; i <= 120; i++) {
//            System.out.println("Key: " + i + ", Frequency: " + frequencyMap.get(i));
//        }
    }
}

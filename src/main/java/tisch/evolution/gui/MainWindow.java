package tisch.evolution.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import tisch.evolution.Configuration;
import tisch.evolution.Optimizer;
import tisch.evolution.crossover.AbstractCrossOverer;
import tisch.evolution.crossover.LegCombinationCrossOverer;
import tisch.evolution.evaluation.AbstractEvaluator;
import tisch.evolution.evaluation.WobblynessEvaluator;
import tisch.evolution.mutation.AbstractMutator;
import tisch.evolution.mutation.LegMutator;
import tisch.evolution.population.Table;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

public class MainWindow extends JFrame {
    private JTextArea outputTextArea;
    private JButton runButton;
    private JButton visualizeButton;
    private JSpinner lengthDeviationSpinner;
    private JSpinner mutationRateSpinner;
    private JSpinner tournamentSizeSpinner;
    private JSpinner generationSizeSpinner;
    private JSpinner maximumGenerationsSpinner;
    private JPanel mainPanel;
    private JLabel maxGenerationsLabel;
    private JLabel generationSizeLabel;
    private JLabel tournamentSIzeLabel;
    private JLabel mutationRateLabel;
    private JLabel lengthDeviationLabel;
    private JLabel outputLabel;

    public MainWindow(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        // This will run the optimizer
        runButton.addActionListener(getRunListener());
    }

    private ActionListener getRunListener() {
        return actionEvent -> {
            // Get config
            // Start optimizer

            this.setOutputTextArea("");

            int generationSize = 10;
            int maxGenerations = 20;
            double lengthDeviation = 0.1;
            double mutationRate = 0.15;
            double fitnessGoal = 0.0;
            int tournamentSize = 4;

            try {
                generationSize = getGenerationSpinnerValue();
                maxGenerations = getMaximumGenerationSpinnerValue();
                lengthDeviation = getLengthDeviationSpinnerValue();
                mutationRate = getMutationRateSpinnerValue();
                tournamentSize = getTournamentSizeSpinnerValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Configuration configuration = new Configuration(generationSize, maxGenerations, fitnessGoal, lengthDeviation, mutationRate, tournamentSize);
            AbstractMutator mutator = new LegMutator(configuration.getMAX_LENGTH_FACTOR());
            AbstractCrossOverer crossOverer = new LegCombinationCrossOverer(configuration.getMUTATION_RATE(), mutator);
            AbstractEvaluator evaluator = new WobblynessEvaluator();
            Optimizer optimizer = new Optimizer(evaluator, crossOverer, configuration);

            List<Table> result = optimizer.optimize();
            for (int i = 0; i < result.size(); i++) {
                double currentFitness = evaluator.evaluateFitness(result.get(i));
                this.appendOutputText("Fittest Table of Generation " + (i + 1) + ":\n   " + result.get(i) + "\n   Fitness: " + currentFitness + "\n--------------------------------------\n");
            }
            Table lastTable = result.get(result.size() - 1);
            if (evaluator.evaluateFitness(lastTable) == fitnessGoal) {
                this.appendOutputText("Success!\nPerfect resulting Table: \n   " + lastTable);
            } else {
                this.appendOutputText("No perfect table found. :-(");
            }


            visualizeButton.addActionListener(getVisualizeListener(result, evaluator));
            visualizeButton.setEnabled(true);
        };
    }

    private ActionListener getVisualizeListener(List<Table> tables, AbstractEvaluator evaluator) {
        return actionEvent -> {
            XYSeries tableSeries = new XYSeries("Table Fitness");

            for (int i = 0; i < tables.size(); i++) {
                tableSeries.add(i + 1, evaluator.evaluateFitness(tables.get(i)));
            }
            XYSeriesCollection dataset = new XYSeriesCollection(tableSeries);

            // For the trend line
            double[] coefficients = Regression.getOLSRegression(dataset, 0);
            double b = coefficients[0];
            double m = coefficients[1];

            XYSeries trendLine = new XYSeries("Trend");
            double x = tableSeries.getDataItem(0).getXValue();
            trendLine.add(x, (m * x) + b); // add first point
            x = tableSeries.getDataItem(tableSeries.getItemCount() - 1).getXValue();
            trendLine.add(x, (m * x) + b); // add last point
            dataset.addSeries(trendLine);


            // Create dataset, and plot
            JFreeChart scatterChart = ChartFactory.createScatterPlot(
                    "Fittest per Generation", // Chart title
                    "Generation", // X-Axis Label
                    "Fitness", // Y-Axis Label
                    dataset, // Dataset for the Chart
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false
            );

            XYPlot plot = scatterChart.getXYPlot();
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
            renderer.setSeriesLinesVisible(1, Boolean.TRUE);
            renderer.setSeriesShapesVisible(1, Boolean.FALSE);


            ChartPanel chartPanel = new ChartPanel(scatterChart);
            JFrame chatFrame = new JFrame();
            chatFrame.setSize(500, 500);
            chatFrame.getContentPane().add(chartPanel);
            chatFrame.setVisible(true);
        };
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        SpinnerNumberModel lengthDeviation = new SpinnerNumberModel(0.15, 0.0, 1.00, 0.01);
        this.lengthDeviationSpinner = new JSpinner();
        this.lengthDeviationSpinner.setModel(lengthDeviation);

        SpinnerNumberModel mutationRate = new SpinnerNumberModel(0.1, 0.0, 1.00, 0.01);
        this.mutationRateSpinner = new JSpinner();
        this.mutationRateSpinner.setModel(mutationRate);

        SpinnerNumberModel tournamentSize = new SpinnerNumberModel(10, 2, 10000, 1);
        this.tournamentSizeSpinner = new JSpinner();
        this.tournamentSizeSpinner.setModel(tournamentSize);

        SpinnerNumberModel generationSize = new SpinnerNumberModel(10, 10, 500, 1);
        this.generationSizeSpinner = new JSpinner();
        this.generationSizeSpinner.setModel(generationSize);

        SpinnerNumberModel maxGenerations = new SpinnerNumberModel(5, 1, 500, 1);
        this.maximumGenerationsSpinner = new JSpinner();
        this.maximumGenerationsSpinner.setModel(maxGenerations);

        this.visualizeButton = new JButton();
        this.visualizeButton.setEnabled(false);
    }

    public void setOutputTextArea(String text) {
        this.outputTextArea.setText(text);
    }

    public void appendOutputText(String text) {
        this.outputTextArea.append(text);
    }

    public int getGenerationSpinnerValue() throws ParseException {
        this.generationSizeSpinner.commitEdit();
        return (int) this.generationSizeSpinner.getValue();
    }

    public int getMaximumGenerationSpinnerValue() throws ParseException {
        this.maximumGenerationsSpinner.commitEdit();
        return (int) this.maximumGenerationsSpinner.getValue();
    }

    public int getTournamentSizeSpinnerValue() throws ParseException {
        this.tournamentSizeSpinner.commitEdit();
        return (int) this.tournamentSizeSpinner.getValue();
    }

    public double getMutationRateSpinnerValue() throws ParseException {
        this.mutationRateSpinner.commitEdit();
        return (double) this.mutationRateSpinner.getValue();
    }

    public double getLengthDeviationSpinnerValue() throws ParseException {
        this.lengthDeviationSpinner.commitEdit();
        return (double) this.lengthDeviationSpinner.getValue();
    }
}

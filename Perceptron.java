import java.util.Random;

public class Perceptron {

    private double[] weights;
    private int bias = 1;
    private double[][] training_sets;
    private int iterations;
    private double learnRate;

    public Perceptron(double learnRate, int iterations, double[][] training_sets) {
        this.weights = new double[training_sets[0].length];
        this.training_sets = training_sets;
        this.learnRate = learnRate;
        this.iterations = iterations;
    }
   
    private void initWeights() {
        Random ran = new Random();

        for(int i = 0; i < this.weights.length; i++) {
            this.weights[i] = Math.random();
            System.out.println(Math.random());
        }
        this.bias = ran.nextInt(2);
    }

    public boolean train(double[] desired) {
        int epocas = 0;
        boolean error = true;
        
        this.initWeights();

        do {
            error = false;
            double difference = 0;

            // System.out.println("Época " + (epocas + 1));
            
            for(int i = 0; i < this.training_sets.length; i++){

                double result = this.run(this.training_sets[i]);

                if(result != desired[i]) {
                    error = true;
                    difference = desired[i] - result;
                    this.recalWeights(difference, this.training_sets[i]);
                } else {
                    error = false;
                }

                // System.out.printf("y: %.10f -> desired(saída): %.1f\n", result, desired[i]);            
            }
            epocas++;

        }  while(error && epocas < this.iterations);

        System.out.printf("W - 1 %.1f -> W - 2 %.1f -> W - 3 %.1f\n", this.weights[0], this.weights[1], this.weights[2]);
        return true;
    }

    private void recalWeights(double difference, double[] inputs) {
        for(int i = 0; i < this.weights.length; i++) {
            this.weights[i] += this.learnRate * difference * inputs[i];
        }
    }

    public double run(double[] inputs) {
        double sum = 0.0;

        for(int i = 0; i < this.weights.length; i++){
            sum += (inputs[i] * this.weights[i]);
        }

        sum += this.bias;
        return this.sigmoid(sum);
    }

    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}

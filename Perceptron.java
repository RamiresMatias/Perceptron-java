public class Perceptron {

    private double[] weights;
    private double bias;
    private double[][] training_sets;
    private int iterations;
    private double learnRate = 0.15;

    public Perceptron(double learnRate, int iterations, double[][] training_sets) {
        this.weights = new double[training_sets[0].length];
        this.training_sets = training_sets;
        this.learnRate = learnRate;
        this.iterations = iterations;

        this.initWeights();
    }
   
    private void initWeights() {
        for(int i = 0; i < this.weights.length; i++) {
            this.weights[i] = Math.random();
        }
        this.bias = Math.random();
    }

    public boolean train(double[] desired) {
        int count = 0;
        boolean error = true;

        do {

            System.out.println("Época " + (count + 1));
            
            for(int i = 0; i < this.training_sets.length; i++){

                double difference = 0;
                double result = this.run(this.training_sets[i]);

                if(result != desired[i]) {
                    error = true;
                    difference = desired[i] - result;
                    this.recalWeights(difference, this.training_sets[i]);
                } else {
                    error = false;
                }

                System.out.printf("y: %.10f -> desired(saída): %.1f\n", result, desired[i]);            
            }
            count++;

        }  while(error && count < this.iterations);

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

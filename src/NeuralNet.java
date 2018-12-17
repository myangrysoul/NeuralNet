
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class NeuralNet implements Serializable {
    double[] inputLayer;
    private final double[] hiddenLayerOut;
    private final double[] outputLayerOut;
    private final double[] hiddenLayerErr;
    private final double[] outputLayerErr;
    private ArrayList<Neuron> hiddenLayer;
    private ArrayList<Neuron> outputLayer;
    ImageLoader imageLoader = new ImageLoader();
    double[][] patterns = imageLoader.getArray();
    int studycounter = 0;

    private final double[][] answers = {
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    };

    NeuralNet() {
        inputLayer = new double[300];
        hiddenLayerOut = new double[80];
        outputLayerOut = new double[answers[0].length];
        outputLayerErr = new double[answers[0].length];
        hiddenLayerErr = new double[hiddenLayerOut.length];
    }

    void initInputLayer(String name) {
        int[] rgbArray;
        rgbArray=imageLoader.rgbArr(imageLoader.loadImg(name));
        for (int i = 0; i < rgbArray.length; i++) {
            inputLayer[i] = rgbArray[i];
        }
    }

    private double[] initWeight(int numOfEl) {
        double[] weight = new double[numOfEl];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = Math.random() * -2 + 1;
        }
        return weight;
    }

    void initHidden(double[] input) {
        hiddenLayer = new ArrayList<>();
        for (int i = 0; i < hiddenLayerOut.length; i++) {
            hiddenLayer.add(new Neuron(input, initWeight(inputLayer.length)));
        }
    }

    void initOutputLayer() {
        outputLayer = new ArrayList<>();
        for (int i = 0; i < outputLayerOut.length; i++) {
            outputLayer.add(new Neuron(hiddenLayerOut, initWeight(hiddenLayerOut.length)));
        }
    }

    void counthiddenLayerOut() {
        int index = 0;
        for (Neuron neuron : hiddenLayer) {
            hiddenLayerOut[index] = neuron.Output(neuron.getInputs(), neuron.getWeights());
            //System.out.println("h: "+hiddenLayerOut[index]);
            index++;
        }
    }

    private void counOutput() {
        int index = 0;
        for (Neuron neuron : outputLayer) {
            outputLayerOut[index] = neuron.Output(neuron.getInputs(), neuron.getWeights());
            //System.out.println("'"+index+"' : "+outputLayerOut[index]);
            index++;
        }
    }

    private double ERROR(double[] answer) {
        double sum = 0;
        for (int i = 0; i < answer.length; i++) {
            sum += Math.pow(answer[i] - outputLayerOut[i], 2);
        }
        System.out.println("Err: " + 0.5 * sum);
        return 0.5 * sum;
    }

    private double[] getOutWeight(int index) {
        double[] weight = new double[answers[0].length];
        int j = 0;
        for (Neuron neuron : outputLayer) {
            weight[j] = neuron.getWeight(index);
            j++;
        }
        return weight;
    }

    private void setHiddenLayerInputs(double[] hiddenLayerInputs) {
        for (Neuron hiddenNeuron : hiddenLayer) {
            hiddenNeuron.setInputs(hiddenLayerInputs);
        }
    }

    private void setOutputLayerInputs() {
        for (Neuron outputNeuron : outputLayer) {
            outputNeuron.setInputs(hiddenLayerOut);
        }
    }

    void study() {
        initHidden(patterns[0]);
        counthiddenLayerOut();
        initOutputLayer();
        do {
            for (int numOfPatterns = 0; numOfPatterns < patterns.length; numOfPatterns++) {
                setHiddenLayerInputs(patterns[numOfPatterns]);
                counthiddenLayerOut();
                setOutputLayerInputs();
                counOutput();
                do {
                    for (int i = 0; i < outputLayer.size(); i++) {
                        Neuron neuron = outputLayer.get(i);
                        outputLayerErr[i] =
                                neuron.errorOutNeuron(neuron.Output(neuron.getInputs(), neuron.getWeights()),
                                                      answers[numOfPatterns][i]);
                        //System.out.println("o -- " + outputLayerErr[i]);
                    }
                    for (int i = 0; i < hiddenLayer.size(); i++) {
                        Neuron neuron = hiddenLayer.get(i);
                        hiddenLayerErr[i] = neuron.errorHiddenNeuron(getOutWeight(i), outputLayerErr);
                        //System.out.println("h -- " + hiddenLayerErr[i]);
                    }

                    for (int i = 0; i < outputLayer.size(); i++) {
                        Neuron neuron = outputLayer.get(i);
                        double[] outputWeight = neuron.getWeights();
                        double[] newWeight = new double[outputWeight.length];
                        for (int j = 0; j < outputWeight.length; j++) {
                            Neuron hiddenNeuron = hiddenLayer.get(j);
                            newWeight[j] = outputWeight[j] + 0.5 * outputLayerErr[i] * hiddenNeuron
                                    .Output(hiddenNeuron.getInputs(), hiddenNeuron.getWeights());
                        }
                        neuron.setWeights(newWeight);
                    }
                    for (int i = 0; i < hiddenLayer.size(); i++) {
                        Neuron neuron = hiddenLayer.get(i);
                        double[] hiddenWeight = neuron.getWeights();
                        double[] newWeight = new double[hiddenWeight.length];
                        double[] inputNeuron = patterns[numOfPatterns];
                        for (int j = 0; j < hiddenWeight.length; j++) {
                            newWeight[j] = hiddenWeight[j] + 0.5 * hiddenLayerErr[i] * inputNeuron[j];
                        }
                        neuron.setWeights(newWeight);
                    }
                    counthiddenLayerOut();
                    setOutputLayerInputs();
                    counOutput();
                    studycounter++;
                    System.out.println(numOfPatterns);
                } while (ERROR(answers[numOfPatterns]) > 0.0001);
            }
            setHiddenLayerInputs(patterns[0]);
            counthiddenLayerOut();
            setOutputLayerInputs();
            counOutput();
        } while (ERROR(answers[0]) > 0.0001);
    }

    HashMap<Character, String> check(double[] input) {
        HashMap<Character, String> out = new HashMap<>();
        for (Neuron hiddenNeuron : hiddenLayer) {
            hiddenNeuron.setInputs(input);
        }
        counthiddenLayerOut();
        for (Neuron outputNeuron : outputLayer) {
            outputNeuron.setInputs(hiddenLayerOut);
        }
        counOutput();
        double sum=0;
        for(double outputNeuron:outputLayerOut){
            sum+=outputNeuron;
        }

        char index = 'A';
        for (double outputNeuron : outputLayerOut) {
            out.put(index, new DecimalFormat("#0.00").format(outputNeuron/sum * 100));
            index++;
        }
        return out;
    }
}

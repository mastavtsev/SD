package org.example;

/// Свёрточная нейронная сеть
//          == Convolution Neural Network
public class ConvNN implements NeuralNetwork {

    // Путь к обучающим данным.
    private final String pathToPictures;

    // Конструктор, в котором назначается путь к
    // данным и вызывается метод обучения.
    public ConvNN(String pathToPictures) {
        this.pathToPictures = pathToPictures;
        trainModel(this.pathToPictures);
    }

    // Результат анализа сетью.
    @Override
    public void analyze(String pathToPicture) {
        System.out.println("Convolution Neural Network says " +
                "that you have give picture of CAT in "  + pathToPicture);
    }

    // Долгое и тернистое обучениие модели по тестовым данным.
    private void trainModel(String trainingDataSet) {
        System.out.println("Conv neural network has trained so hard and so long. \n" +
                "Training on dataset from " + pathToPictures);
    }
}

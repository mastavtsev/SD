package org.example;

public class Main {
    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new ProxyCNN("trainingDataSet.csv");

        // Нейросеть очень-очень обучиться на данных и отдаст результат анализа.
        neuralNetwork.analyze("testPicture.png");

        System.out.println();

        // Нейросеть не будет заново учиться и сразу отдаст результат.
        neuralNetwork.analyze("testPicture.png");
    }
}
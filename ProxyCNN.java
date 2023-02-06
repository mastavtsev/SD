package org.example;

public class ProxyCNN implements NeuralNetwork{
    // Нейронная сеть, прокси над который мы строим.
    private ConvNN cnn;

    // Путь к данным для обучения.
    private final String pathToPictures;


    // Конструктор с путём к данным.
    public ProxyCNN(String pathToPictures) {
        this.pathToPictures = pathToPictures;
    }

    // Процесс анализа
    @Override
    public void analyze(String pathToPicture) {
        if (cnn == null) {
            cnn = new ConvNN(pathToPictures);
        }
        cnn.analyze(pathToPicture);
    }
}

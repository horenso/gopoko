import horenso.model.Card;
import horenso.model.Deck;
import horenso.model.Hand;
import horenso.service.HandEvaluationService;
import horenso.service.impl.HandEvaluationServiceImpl;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CreateRandomEvaluationFile {

    static final int TESTS = 1_000_000; // takes about 4 seconds on a modern laptop
    static final Path PATH = Path.of("src/test/resources/randomEvaluations.txt");
    static final HandEvaluationService HAND_EVALUATION_SERVICE = new HandEvaluationServiceImpl(); // TODO: Dep injection

    public static void main(String[] args) {
        try {
            long startTime = System.nanoTime();
            createTests();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("It took: " + duration / 1000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTests() throws Exception {
        FileWriter fileWriter = null;

        Files.deleteIfExists(PATH);
        File file = new File(PATH.toString());

        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }
        fileWriter = new FileWriter(file);

        StringBuilder cardStringBuilder = new StringBuilder();
        for (int i = 0; i < TESTS; i++) {
            Deck deck = new Deck();
            List<Card> cards = new ArrayList<>(7);
            deck.shuffle();
            for (int j = 0; j < 7; j++) {
                cards.add(deck.drawCard());
            }
            Hand ranking = HAND_EVALUATION_SERVICE.rateHand(cards);

            cards.forEach(card -> cardStringBuilder.append(card.toString()).append(" "));
            cardStringBuilder
                    .append("- ")
                    .append(ranking.getHandType().toString().toLowerCase())
                    .append(" - ");
            ranking.getCards().forEach(card -> cardStringBuilder.append(card.toString()).append(" "));
            cardStringBuilder.append("\n");
        }
        fileWriter.write(cardStringBuilder.toString());
        fileWriter.flush();
    }
}

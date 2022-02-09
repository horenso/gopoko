package horenso.logic.handevaluation;

import horenso.model.Card;
import horenso.model.HandRanking;
import horenso.service.HandRankerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class HandRankerServiceImplTest {
    private static final String absolutePath = Paths.get("").toAbsolutePath().toString();
    private static final String handsPath = absolutePath + "/src/test/resources/randomEvaluations.txt";
    private static Scanner scanner;

    @Autowired
    private HandRankerService handRankerService;

    private static List<Card> readCardsFromString(String cardString, int number) {
        String[] stringCards = cardString.split(" ");
        Card[] hand = new Card[number];

        for (int i = 0; i < number; ++i) {
            hand[i] = new Card(stringCards[i]);
        }

        return List.of(hand);
    }

    @Test
    @DisplayName("Test hand evaluations")
    void testRatings() {
        try {
            scanner = new Scanner(new File(handsPath));
        } catch (FileNotFoundException e) {
            fail("Couldn't read test file.");
        }

        scanner.useDelimiter("[\n-]");

        String cardsInput, ratingInput, handInput;

        int line = 0;

        while (scanner.hasNext()) {
            cardsInput = scanner.next().strip();
            line++;

            // Skipping empty lines
            if (cardsInput.isBlank()) {
                scanner.nextLine();
                continue;
            }

            // Skipping comments and displaying them
            if (cardsInput.startsWith("#")) {
                System.out.println("Testing: " + cardsInput.replaceAll("#", "").strip());
                scanner.nextLine();
                continue;
            }

            ratingInput = scanner.next().strip();
            handInput = scanner.next().strip();

            List<Card> cards = readCardsFromString(cardsInput, 7);
            HandRanking result = handRankerService.rateHand(cards);

            List<Card> expectedHand = readCardsFromString(handInput, 5);

//            System.out.println(
//                    String.format("Cards: %s Expect: %s %s Got: %s %s",
//                            cardsInput,
//                            ratingInput,
//                            expectedHand.toString(),
//                            result.getHandType().toString().toLowerCase(),
//                            result.getCards().toString()));

            assertEquals(ratingInput, result.getHandType().toString().toLowerCase(),
                    String.format("Hand was: %s one line %s ", cardsInput, line));
            assertEquals(expectedHand.toString(), result.getCards().toString(),
                    String.format("Hand was: %s one line %s ", cardsInput, line));
        }
    }
}

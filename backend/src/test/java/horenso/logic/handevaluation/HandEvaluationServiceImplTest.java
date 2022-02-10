package horenso.logic.handevaluation;

import horenso.TestHelper;
import horenso.model.Card;
import horenso.model.Hand;
import horenso.service.HandEvaluationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class HandEvaluationServiceImplTest {
    private static final String absolutePath = Paths.get("").toAbsolutePath().toString();
    private static final String handsPath = absolutePath + "/src/test/resources/evaluations.txt";
    private static Scanner scanner;

    @Autowired
    private HandEvaluationService handEvaluationService;

    @Test
    @DisplayName("Test hand evaluations")
    void testRateHand() {
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

            List<Card> cards = TestHelper.readCardsFromString(cardsInput, 7);
            Hand result = handEvaluationService.rateHand(cards);

            List<Card> expectedHand = TestHelper.readCardsFromString(handInput, 5);

            assertEquals(ratingInput, result.getHandType().toString().toLowerCase(),
                    String.format("Hand was: %s one line %s ", cardsInput, line));
            assertEquals(expectedHand.toString(), result.getCards().toString(),
                    String.format("Hand was: %s one line %s ", cardsInput, line));
        }
    }

    @Test
    @DisplayName("Test find winner set out of different hands")
    void testFindWinnerSet() {
        List<Hand> handList = new ArrayList<>(3);
        handList.add(new Hand(Hand.Type.PAIR, TestHelper.readCardsFromString("4♥ 4♣ T♠ 9♥ 8♥", 5)));
        handList.add(new Hand(Hand.Type.PAIR, TestHelper.readCardsFromString("A♥ A♦ J♥ T♠ 5♥", 5)));
        handList.add(new Hand(Hand.Type.PAIR, TestHelper.readCardsFromString("A♥ A♦ J♥ T♠ 4♥", 5)));
//        HandEvaluationService.findWinnerSet(handList);
        // TODO!
    }
}

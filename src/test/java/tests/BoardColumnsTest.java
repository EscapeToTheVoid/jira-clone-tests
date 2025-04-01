//package tests;
//
//import com.microsoft.playwright.*;
//import org.junit.jupiter.api.*;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class BoardColumnsTest {
//
//    private Playwright playwright;
//    private Browser browser;
//    private Page page;
//
//    @BeforeAll
//    void setup() {
//        playwright = Playwright.create();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        page = browser.newPage();
//    }
//
//    //@Test
//    void testBoardColumnsPresence() throws InterruptedException {
//        page.navigate("https://jira.trungk18.com/project/board");
//
//        // Wait for the board to load
//        page.waitForSelector("text=Kanban board");
//
//        // Check for each column title
//        assertTrue(page.locator("text=BACKLOG").isVisible(), "BACKLOG column is missing");
//        assertTrue(page.locator("text=SELECTED FOR DEVELOPMENT").isVisible(), "SELECTED FOR DEVELOPMENT column is missing");
//        assertTrue(page.locator("text=IN PROGRESS").isVisible(), "IN PROGRESS column is missing");
//        assertTrue(page.locator("text=DONE").isVisible(), "DONE column is missing");
//        page.locator("text=Angular Spotify 🎧").click();
//
//        Thread.sleep(3000);
//
//    }
//
//    @Test
//    void testBacklogLabelMatchesCardCount() {
//        page.navigate("https://jira.trungk18.com/project/board");
//
//        // Wait for the BACKLOG column to load
//        page.waitForSelector("#Backlog");
//
//        // More specific: grab only column headers
//        Locator backlogHeader = page.locator(".status-list div:has-text(\"BACKLOG\")").first();
//        String titleText = backlogHeader.innerText().trim(); // should be "BACKLOG 3"
//        System.out.println("Backlog header text: " + titleText);
//
//        // Split and grab last word (should be the number)
//        String[] parts = titleText.split(" ");
//        String lastPart = parts[parts.length - 1];
//        int numberInTitle = Integer.parseInt(lastPart); // ← safely parsed now
//        System.out.println("Backlog number from header: " + numberInTitle);
//
//        // Count actual cards
//        int actualCardCount = page.locator("#Backlog issue-card").count();
//        System.out.println("Actual cards in Backlog: " + actualCardCount);
//
//        Assertions.assertEquals(numberInTitle, actualCardCount, "Mismatch in Backlog card count");
//    }
//
//
//    @Test
//    void testAllColumnLabelCountsMatchCards() {
//        page.navigate("https://jira.trungk18.com/project/board");
//        page.waitForSelector("#Backlog");
//
//        Map<String, String> columns = new LinkedHashMap<>();
//        columns.put("BACKLOG", "#Backlog");
//        columns.put("SELECTED FOR DEVELOPMENT", "#Selected");
//        columns.put("IN PROGRESS", "#InProgress");
//        columns.put("DONE", "#Done");
//
//        for (Map.Entry<String, String> entry : columns.entrySet()) {
//            String label = entry.getKey();
//            String columnId = entry.getValue();
//
//            // Select the column header div using partial match with the label
//            Locator headerDiv = page.locator(".status-list div:has-text(\"" + label + "\")").first();
//
//            // Find the span inside it and get the number
//            Locator countSpan = headerDiv.locator("span");
//            int numberInTitle = Integer.parseInt(countSpan.innerText().trim());
//
//            // Count actual issue cards
//            int actualCardCount = page.locator(columnId + " issue-card").count();
//
//            System.out.println("[" + label + "] → Expected: " + numberInTitle + ", Actual: " + actualCardCount);
//            Assertions.assertEquals(numberInTitle, actualCardCount, "Mismatch in column: " + label);
//        }
//    }
//
//
//
//
//
//    @AfterAll
//    void tearDown() {
//        browser.close();
//        playwright.close();
//    }
//}

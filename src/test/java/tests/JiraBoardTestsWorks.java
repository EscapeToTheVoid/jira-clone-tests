//package tests;
//
//import com.microsoft.playwright.*;
//import com.microsoft.playwright.options.AriaRole;
//import org.junit.jupiter.api.*;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class JiraBoardTestsWorks {
//    static Playwright playwright;
//    static Browser browser;
//    Page page;
//
//    @BeforeAll
//    static void setupAll() {
//        playwright = Playwright.create();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//    }
//
//    @BeforeEach
//    void setup() {
//        page = browser.newPage();
//        page.navigate("https://jira.trungk18.com/project/board");
//        page.waitForSelector("#Backlog");
//    }
//
//    @AfterEach
//    void teardown() {
//        page.close();
//    }
//
//    @AfterAll
//    static void teardownAll() {
//        browser.close();
//        playwright.close();
//    }
//
//    @Test
//    void testColumnTitlesExist() {
//        String[] expected = {"BACKLOG", "SELECTED FOR DEVELOPMENT", "IN PROGRESS", "DONE"};
//        for (String label : expected) {
//            assertTrue(page.locator(".status-list div:has-text(\"" + label + "\")").first().isVisible());
//        }
//    }
//
//    @Test
//    void testCardCountMatchesHeaderInAllColumns() {
//        Map<String, String> columns = Map.of(
//                "BACKLOG", "#Backlog",
//                "SELECTED FOR DEVELOPMENT", "#Selected",
//                "IN PROGRESS", "#InProgress",
//                "DONE", "#Done"
//        );
//
//        for (Map.Entry<String, String> entry : columns.entrySet()) {
//            String label = entry.getKey();
//            String columnId = entry.getValue();
//
//            Locator header = page.locator(".status-list div:has-text(\"" + label + "\")").first();
//            Locator countSpan = header.locator("span");
//            int numberInTitle = Integer.parseInt(countSpan.innerText().trim());
//            int actualCardCount = page.locator(columnId + " issue-card").count();
//
//            assertEquals(numberInTitle, actualCardCount, "Mismatch in column: " + label);
//        }
//    }
//
//    @Test
//    void testClickCardAndVerifyDetailAppears() {
//        page.locator("text=Angular Spotify 🎧").click();
//        Locator detailPanel = page.locator("text=STORY-2021");
//        assertTrue(detailPanel.isVisible(), "Detail panel should appear for selected card");
//    }
//
//    @Test
//    void testDragCardToAnotherColumn() {
//        Locator card = page.locator("#Backlog issue-card").first();
//        Locator target = page.locator("#InProgress");
//        card.dragTo(target);
//
//        // Verify it's now in In Progress
//        assertTrue(page.locator("#InProgress issue-card").count() > 0);
//    }
//
//    @Test
//    void testSearchFiltersCards() {
//        Locator searchInput = page.locator("input.form-input"); // Based on what you showed earlier
//        searchInput.waitFor(new Locator.WaitForOptions().setTimeout(5000)); // optional
//        searchInput.fill("spotify");
//
//        Locator match = page.locator("text=Angular Spotify 🎧");
//        assertTrue(match.isVisible());
//    }
//
//    @Test
//    void testOnlyMyIssuesFilter() {
//        page.locator("button:has-text('Only My Issues')").click();
//        Locator cards = page.locator("issue-card");
//        assertTrue(cards.count() > 0); // May vary, we just check it's not broken
//    }
//
//    @Test
//    void testIgnoreResolvedFilter() {
//        int initialDoneCount = page.locator("#Done issue-card").count();
//        page.locator("button:has-text('Ignore Resolved')").click();
//        int afterFilterCount = page.locator("#Done issue-card").count();
//        assertTrue(afterFilterCount < initialDoneCount);
//    }
//
//    @Test
//    void testCardTitlesAreNotEmpty() {
//        Locator titles = page.locator("issue-card p");
//        for (int i = 0; i < titles.count(); i++) {
//            String title = titles.nth(i).innerText().trim();
//            assertFalse(title.isEmpty(), "Card title should not be empty");
//        }
//    }
//
//    @Test
//    void testTaskIdsAreUnique() {
//        Set<String> ids = new HashSet<>();
//        Locator labels = page.locator("issue-card span:has-text('-')");
//        for (int i = 0; i < labels.count(); i++) {
//            String id = labels.nth(i).innerText().trim();
//            assertTrue(ids.add(id), "Duplicate task ID found: " + id);
//        }
//    }
//
//    @Test
//    void testTopBarLinksOpen() {
//        Locator spotifyBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("🎧 Spotify"));
//        Page popup = page.waitForPopup(() -> spotifyBtn.click());
//        assertTrue(popup.url().contains("spotify"));
//        popup.close();
//    }
//}

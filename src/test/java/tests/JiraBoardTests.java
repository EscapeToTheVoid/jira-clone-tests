package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Jira Clone Board")
@Feature("Board Functional Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JiraBoardTests {
    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate("https://jira.trungk18.com/project/board");
        page.waitForSelector("#Backlog");
    }

    @AfterEach
    void teardown() {
        page.close();
    }

    @AfterAll
    static void teardownAll() {
        browser.close();
        playwright.close();
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-01: Verify that all 4 Kanban column headers exist on the board")
    @Description("Verify that all 4 Kanban column headers exist on the board.")
    void testColumnTitlesExist() {
        Allure.step("Verify all 4 columns are present", () -> {
            String[] expected = {"BACKLOG", "SELECTED FOR DEVELOPMENT", "IN PROGRESS", "DONE"};
            for (String label : expected) {
                boolean visible = page.locator(".status-list div:has-text(\"" + label + "\")").first().isVisible();
                assertTrue(visible, "Column '" + label + "' should be visible on the board.");
            }
        });
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC-02: Verify card counts match header counts in each column")
    @Description("Check that the card count in each column matches the number shown in the header.")
    void testCardCountMatchesHeaderInAllColumns() {
        Allure.step("Verify column card counts match header labels", () -> {
            Map<String, String> columns = Map.of("BACKLOG", "#Backlog", "SELECTED FOR DEVELOPMENT", "#Selected", "IN PROGRESS", "#InProgress", "DONE", "#Done");

            for (Map.Entry<String, String> entry : columns.entrySet()) {
                String label = entry.getKey();
                String columnId = entry.getValue();

                Locator header = page.locator(".status-list div:has-text(\"" + label + "\")").first();
                Locator countSpan = header.locator("span");
                int numberInTitle = Integer.parseInt(countSpan.innerText().trim());
                int actualCardCount = page.locator(columnId + " issue-card").count();

                assertEquals(numberInTitle, actualCardCount, "Mismatch in card count for column '" + label + "'");
            }
        });
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC-03: Create a new Story ticket")
    @Description("Create a new Story ticket with highest priority and assign to Spiderman and Thor.")
    void testCreateStoryTicket() {
        Allure.step("Open the ticket creation modal", () -> {
            page.locator("i.anticon-plus").click();
            page.locator("add-issue-modal").waitFor();
        });

        Allure.step("Select issue type 'Story'", () -> {
            page.locator("issue-type-select nz-select").click();
            page.locator("nz-option-item span:text('Story')").click();
        });

        Allure.step("Select priority 'Highest'", () -> {
            page.locator("issue-priority-select nz-select").click();
            page.locator("div span:text('Highest')").click();
        });

        Allure.step("Fill out summary and description", () -> {
            page.locator("input[formcontrolname='title']").fill("Test Title for Story");
            page.locator("quill-editor .ql-editor").fill("Once upon a time...");
        });

        Allure.step("Assign to Spider Man and Thor", () -> {
            page.locator("issue-assignees-select nz-select").click();
            page.locator("nz-option-item[title='Spider Man']").click();
            page.locator("nz-option-item[title='Thor']").click();
            page.keyboard().press("Escape");
        });

        Allure.step("Submit the issue and verify creation", () -> {
            page.locator("button:has-text('Create Issue')").click();
            Locator newCard = page.locator("#Backlog issue-card").last();
            assertTrue(newCard.innerText().contains("Test Title for Story"));
        });
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-04: Verify card detail panel visibility")
    @Description("Click a card and verify that the detail panel appears.")
    void testClickCardAndVerifyDetailAppears() {
        Allure.step("Click on 'Angular Spotify 🎧' card", () -> {
            page.locator("text=Angular Spotify 🎧").click();
        });

        Allure.step("Verify that the detail panel is visible", () -> {
            Locator detailPanel = page.locator("text=STORY-2021");
            assertTrue(detailPanel.isVisible(), "Detail panel should appear for selected card");
        });

        Allure.step("Click the expand button and verify redirected URL ends with '2021'", () -> {
            Locator expandBtn = page.locator("j-button[icon='expand']").locator("button");
            expandBtn.click();
            String urlEnding = page.url().substring(page.url().length() - 4);
            assertEquals("2021", urlEnding);
        });
    }

    @Test
    @Order(5)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC-05: Move a card from Backlog to In Progress")
    @Description("Move a card from Backlog to In Progress and verify the move was successful.")
    void moveCardToAnotherColumn() {
        Allure.step("Click the first Backlog card", () -> {
            page.locator("#Backlog issue-card").first().click();
        });
        String cardTitle = page.locator("#Backlog issue-card p").first().innerText().trim();

        Allure.step("Change card status to 'In progress'", () -> {
            page.locator("span:has-text('Backlog')").click();
            page.locator("div.ant-dropdown li span:text('In progress')").click();
        });

        Allure.step("Close the detail panel", () -> {
            page.locator("j-button[icon='times']").locator("button").click();
        });

        Allure.step("Verify card moved to In Progress", () -> {
            String movedCardTitle = page.locator("#InProgress issue-card").last().locator("p").innerText().trim();
            assertEquals(movedCardTitle, cardTitle);
        });
    }

    @Test
    @Order(6)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-06: Search and validate card filter")
    @Description("Search for a keyword and verify matching cards are shown.")
    void testSearchFiltersCards() {
        Allure.step("Fill search input with keyword 'merry'", () -> {
            Locator searchInput = page.locator("input.form-input");
            searchInput.fill("merry");
            Allure.step("Wait briefly for the UI to update search results", () -> {
                page.waitForTimeout(100);
            });
        });

        Allure.step("Verify 'Merry Christmas' card is visible", () -> {
            assertTrue(page.locator("text=Merry Christmas").isVisible(), "'Merry Christmas' card should be visible");
        });

        Allure.step("Verify unrelated card 'STORY-2021' is hidden", () -> {
            assertTrue(page.locator("text=STORY-2021").isHidden(), "'STORY-2021' card should be hidden when searching 'merry'");
        });
    }

    @Test
    @Order(7)
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC-07: Only My Issues filter")
    @Description("Verify that 'Only My Issues' filter reduces the number of visible cards.")
    void testOnlyMyIssuesFilter() {
        int initialCount = Allure.step("Count total visible cards before applying filter", () -> {
            int count = page.locator("issue-card").count();
            assertTrue(count > 0, "There should be at least one card before filtering.");
            return count;
        });

        Allure.step("Click the 'Only My Issues' filter button", () -> {
            page.locator("button:has-text('Only My Issues')").click();
        });

        int filteredCount = Allure.step("Count visible cards after applying the filter", () -> {
            return page.locator("issue-card").count();
        });

        Allure.step("Verify filtered card count is less than initial count", () -> {
            assertTrue(filteredCount < initialCount, "Filtered count (" + filteredCount + ") should be less than initial count (" + initialCount + ")");
        });
    }

    @Test
    @Order(8)
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC-08: Ignore Resolved filter")
    @Description("Verify that 'Ignore Resolved' filter reduces visible Done cards to 0.")
    void testIgnoreResolvedFilter() {
        Allure.step("Click the 'Ignore Resolved' filter button", () -> {
            page.locator("button:has-text('Ignore Resolved')").click();
        });

        Allure.step("Verify that 'Done' column has no visible cards", () -> {
            int afterFilterCount = page.locator("#Done issue-card").count();
            assertEquals(0, afterFilterCount, "There are still cards visible in the DONE column.");
        });
    }

    @Test
    @Order(9)
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC-09: Hide sidebar functionality")
    @Description("Verify sidebar hides when the collapse button is clicked.")
    void testHideSidebar() {
        Allure.step("Verify sidebar is initially expanded (width: 240px)", () -> {
            Locator sidebar = page.locator("div.sidebar");
            assertTrue(sidebar.getAttribute("style").contains("width: 240px"), "Sidebar should be expanded initially.");
        });

        Allure.step("Click the collapse button to hide the sidebar", () -> {
            Locator hideBtn = page.locator("div.overlay");
            hideBtn.click();
        });

        Allure.step("Verify sidebar is collapsed (width: 15px)", () -> {
            Locator sidebar = page.locator("div.sidebar");
            assertTrue(sidebar.getAttribute("style").contains("width: 15px"), "Sidebar should be collapsed after clicking.");
        });
    }

    @Test
    @Order(10)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC-10: Change project name and category")
    @Description("Ensure Project name and category can be changed.")
    void testSettingsProjectNameAndCategoryChange() {
        Allure.step("Get initial project name and category", () -> {
            String title = page.locator("div.font-medium.text-15").innerText().trim();
            String subTitle = page.locator("div.text-textMedium.text-13").first().innerText().trim();
            Allure.addAttachment("Old Title", title);
            Allure.addAttachment("Old Category", subTitle);
        });

        Allure.step("Navigate to Project Settings page", () -> {
            page.locator("a[href='/project/settings']").click();
        });

        Allure.step("Change the project name to 'Completely different name'", () -> {
            Locator input = page.locator("input[placeholder='Project Name']");
            input.clear();
            input.fill("Completely different name");
        });

        Allure.step("Change the category to 'Marketing'", () -> {
            Locator dropdown = page.locator("select[formcontrolname='category']");
            dropdown.selectOption(new SelectOption().setLabel("Marketing"));
        });

        Allure.step("Click Save to submit changes", () -> {
            page.locator("button:has-text('Save')").click();
        });

        Allure.step("Validate project name and category were updated", () -> {
            String newTitle = page.locator("div.font-medium.text-15").innerText().trim();
            String newSubTitle = page.locator("div.text-textMedium.text-13").first().innerText().trim();
            assertEquals("Completely different name", newTitle);
            assertTrue(newSubTitle.startsWith("Marketing"));
        });
    }


}

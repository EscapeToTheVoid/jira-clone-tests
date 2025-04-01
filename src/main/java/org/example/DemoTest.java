package org.example;

import com.microsoft.playwright.*;


public class DemoTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://playwright.dev/java/docs/intro");

            System.out.println("Page title: " + page.title());

            browser.close();
        }
    }
}

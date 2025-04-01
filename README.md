# 🧪 Jira Clone Board - Automated Testing Suite

This repository contains an end-to-end automated UI test suite for the [Angular Jira Clone](https://jira.trungk18.com/project/board), built with:

- ✅ **Playwright for Java** – browser automation framework
- ☕ **Java 21** - programming language
- 🧪 **JUnit 5** – testing framework
- 🛠️ **Maven** – dependency management and build automation for Java projects
- 📊 **Allure** – reporting and test visualization
- 🚀 **GitHub Actions** – CI/CD integration
- 📄 **Well-documented test scenarios** – implemented & unimplemented test cases


---

## 📂 Project Structure

```bash
.
├── docs/
│   ├── Implemented Test Cases.pdf    # Test cases implemented in the test
│   └── Unimplemented Test Cases.pdf  # Documented but unimplemented test cases
├── tests/                            # Test cases organized by feature
├── resources/                        # Allure configuration (optional)
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions workflow file
├── README.md                         # Project overview
```

---

## 📊 View Test Report

### 🌐 Live Test Report (GitHub Pages)

The latest **Allure Test Report** is automatically published after each successful CI run:

🔗 [escapetothevoid.github.io/jira-clone-tests](https://escapetothevoid.github.io/jira-clone-tests)

This provides a visual overview of passed/failed tests, durations, flaky tests, etc.

---

### 💾 Download & View Locally

If you prefer to view the report locally:

1. Go to the [GitHub Actions](https://github.com/EscapeToTheVoid/jira-clone-tests/actions) tab
2. Open any successful workflow run
3. Scroll to the **Artifacts** section and download `allure-report.zip`
4. Extract the contents and serve it locally using a static server:
```bash
unzip allure-report.zip
cd allure-report
npx serve
```
5. Open the provided URL in your browser (usually http://localhost:3000)
> ⚠️ **Note:** Do *not* open `index.html` directly from the filesystem — it requires a local server to work correctly.

---

## ✅ Implemented Test Scenarios

- Column header validation
- Card counts match header numbers
- Search functionality
- Filters: "Only My Issues", "Ignore Resolved"
- Sidebar toggle
- Creating issues (form & validation)
- Assigning users
- Project settings update
- Drag and drop card across columns

> Full Allure-annotated test logic is found in `JiraBoardTests.java`.

---

## 🔁 CI Pipeline
- This project uses GitHub Actions for automated test execution:

- Every push and pull request to the main branch triggers the test suite

- On success, an Allure Report is generated and uploaded

- The report is also deployed to GitHub Pages for public access

---

## 📄 Test Documentation

- [Implemented Test Cases (PDF)](docs/Jira_Board_Test_Cases.pdf)
- [Unimplemented Test Cases (PDF)](docs/Jira_Board_Unimplemented_Test_Cases.pdf)


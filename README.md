# 🧪 Jira Clone Board - Automated Testing Suite

This repository contains an end-to-end automated UI test suite for the [Angular Jira Clone](https://jira.trungk18.com/project/board), built with:

- ✅ **Playwright for Java** – modern browser automation
- 🧪 **JUnit 5** – testing framework
- 📊 **Allure** – reporting and test visualization
- 🚀 **GitHub Actions** – CI/CD integration
- 📄 **Well-documented test scenarios** – implemented & unimplemented

---

## 📂 Project Structure

.
├── docs/
│   ├── Implemented Test Cases.pdf
│   └── Unimplemented Test Cases.pdf
├── tests/                        # Test cases organized by feature
├── resources/                   # Allure configuration (optional)
├── .github/
│   └── workflows/
│       └── ci.yml               # GitHub Actions workflow file
├── README.md                    # Project overview
└── Unimplemented Jira Tests.docx  # Documented but unimplemented test cases


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

## 📄 Test Documentation

- [Implemented Test Cases (PDF)](docs/Implemented%20Test%20Cases.pdf)
- [Unimplemented Test Cases (PDF)](docs/Unimplemented%20Test%20Cases.pdf)

---

## 🧪 Running Tests Locally

### 🔧 Prerequisites
- Java 17+
- Maven 3.6+
- Node.js (for Playwright)
- Allure CLI (for reports)

### 📦 Setup

```bash
npm install -g allure-commandline --save-dev
mvn clean install
```

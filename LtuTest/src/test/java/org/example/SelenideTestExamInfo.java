package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelenideTestExamInfo {
    private Logger logger = LoggerFactory.getLogger(SelenideTestExamInfo.class);

    static {
        System.setProperty("logback.configurationFile", "src/logback.xml");
    }

    @BeforeAll
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
    }

    @Test
    public void loginTest() {
        // Code to retrieve login credentials
        File jsonFile = new File("C:\\temp\\ltu.json");
        String email = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            email = jsonNode.get("ltuCredentials").get("email").asText();
            password = jsonNode.get("ltuCredentials").get("password").asText();

        } catch (IOException e) {
            logger.error("Error reading credentials from file");
        }

        //test to log in to ltu.com with valid credentials and check that the login was successful, first accept cookies and then login
        open("https://www.ltu.se");
        try {
            $(byText("Tillåt urval")).click();
            logger.info("Cookies accepted");
        } catch (Exception e) {
            logger.error("Cookies not accepted");
        }

        // target and click the student option
        try {
            $(byText("Student")).click();
            logger.info("Student option found");
        } catch (Exception e) {
            logger.error("Student option not found");
        }

        // click the button with the text "Logga in"
        try {
            $(byText("Logga in")).click();
            $(By.id("username")).sendKeys(email);
            $(By.id("password")).sendKeys(password);
            // target a btn by name=submit and click it to login
            $(By.name("submit")).click();
        } catch (Exception e) {
            logger.error("Login failed");
        }

        sleep(3000);
        // check that the login was successful
        try {
            $(byText("Kursrum")).isDisplayed();
            assertTrue($(byText("Kursrum")).isDisplayed());
            logger.info("Login successful");
        } catch (Exception e) {
            logger.error("Login failed");
        }
        try {
            $(byText("Kursrum")).click();
            logger.info("Clicked on Kurser i Canvas button");
        } catch (Exception e) {
            logger.error("Failed to click on Kurser i Canvas button");
        }
        sleep(5000);

        // switch to the new tab that opens up
        switchTo().window(1);

        // check that kursrum is displayed
        try {
            $(byText("översikt")).isDisplayed();
            logger.info("Kursrum is displayed");
        } catch (Exception e) {
            logger.error("Kursrum is not displayed");
        }
        sleep(5000);

        // check that the kurser button is displayed
        try {
            open("https://ltu.instructure.com/courses/18863");
            logger.info("Kurssidan öppnades");
        } catch (Exception e) {
            logger.error("Kurssidan kunde inte öppna");
        }

        sleep(3000);
        // click on the button containing the text "modules"
        try {
            $(byText("Moduler")).click();
            logger.info("Moduler button clicked");
        } catch (Exception e) {
            logger.error("Moduler button not clicked");
        }

        sleep(3000);

        // click in the final exam information button
        try {
            $(byXpath("//a[@href='/courses/18863/modules/items/321907']")).click();
            logger.info("Final button clicked");
        } catch (Exception e) {
            logger.error("Final button not clicked");
        }
        sleep(3000);

        try {
            $(byXpath("//*[contains(text(),'May 30th, from 9:00 - 14:00')]")).isDisplayed();
            logger.info("Text found on the page");
        } catch (Exception e) {
            logger.error("Text not found on the page");
        }

        sleep(3000);

    }
}

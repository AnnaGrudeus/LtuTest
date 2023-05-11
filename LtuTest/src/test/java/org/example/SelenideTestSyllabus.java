package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelenideTestSyllabus {
    private Logger logger = LoggerFactory.getLogger(SelenideTestSyllabus.class);


    static {
        System.setProperty("logback.xml.configurationFile", "src/logback.xml.xml");
    }

    @BeforeAll
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
    }

    @Test
    public void loginTest() {

            open("https://ltu.se");

            var element = $(byText("Tillåt urval"));
            element.click();

            // <button class="button is-medium ltu-search-btn" aria-label="Sök" onclick="showTopInput()">
            //                    <i class="fa fa-search" aria-hidden="true"></i>
            //                </button>
            element = $(byXpath("//button[@class='button is-medium ltu-search-btn']"));
            element.click();

            sleep(3000);

            element = $(byXpath("//input[@id='cludo-search-bar-input']"));

            element.sendKeys("I0015N");
            element.sendKeys(Keys.ENTER); // press the enter key instead of clicking the search button
            sleep(3000);


        }
        /*


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

        //click in the button "utbildning" to get to the search bar
        try {
            $(byXpath("//li[@id='topMenuItem-2']")).click();
            logger.info("Kursplaner clicked");
        } catch (Exception e) {
            logger.error("Kursplaner not found");
        }

        //if the page allow cookies, accept them
        if ($(byText("Tillåt urval")).isDisplayed()) {
            try {
                $(byText("Tillåt urval")).click();
                logger.info("Cookies accepted");
            } catch (Exception e) {
                logger.error("Cookies not accepted");
            }
        }

        sleep(3000);
    /*
    //DOES NOT WORK is not clickable at point (1253, 80)
    //click in the search button
    try{
      $(byId("ltu-menu-search")).shouldBe(visible).click();
        logger.info("Search button clicked");
    } catch (Exception e) {
        logger.error("Search button not found");
    }

    //DOES NOT WORK
    // click on the button containing the text "sök"
    try {
      $(byText("Sök")).waitUntil(visible, 5000).click();
      logger.info("Clicked on Sök button");
    } catch (Exception e) {
      logger.error("Failed to click on Sök button");
    }



    // DOES NOT WORK element not found
    //click on the search button
    try {
      $(byXpath("//button[contains(text(),'ltu-search-btn')]")).click();
      logger.info("Clicked on Sök button");
    } catch (Exception e) {
      logger.error("Failed to click on Sök button");
    }


    // DOES NOT WORK </button> is not clickable at point (1253, 80)
    //click on search
    try {
      $(byXpath("//button[contains(@class, 'ltu-search-btn')]")).click();
      logger.info("Clicked on Sök button");
    } catch (Exception e) {
      logger.error("Failed to click on Sök button");
    }


    // DOES NOT WORK </button> is not clickable at point (1253, 80)
    //click on search
    try {
      $(byXpath("//button[contains(@class, 'search')]")).waitUntil(visible, (5000)).click();
      logger.info("Clicked on Sök button");
    } catch (Exception e) {
      logger.error("Failed to click on Sök button");
    }

        // DOES NOT WORK </button> is not clickable at point (1253, 80)

        // show search result for "I0015N"


        try {
            open("https://www.ltu.se/ltu/search#?cludoquery=I0015N&cludocrawlerLang=Swedish&cludopage=1&cludorefurl=https%3A%2F%2Fwww.ltu.se%2Fedu%2Fcourse%2FI00%2FI0015N%2FI0015N-Test-av-IT-system-1.81215&cludorefpt=Test%20av%20IT-system%2C%20Kurs%2C%20testfall%20testprinciper%20testprocesser%20riskhantering%20testfaser%20mjukvarutest%20testdesign%20testplan%20-%20V%C3%A5ren%202023%20-%20Lule%C3%A5%20tekniska%20universitet%2C%20LTU&cludoinputtype=standard");
            logger.info("Search result for I0015N");
        } catch (Exception e) {
            logger.error("Search result for I0015N not found");
        }

        sleep(3000);
    /*
    //DOES NOT WORK
    // Search for a link with the text "kursplan"
    try {
      $(byText("kursplan")).click();
      logger.info("Kursplan clicked");
    } catch (Exception e) {
      logger.error("Kursplan not found");
    }


    // DOES NOT WORK </a> is not clickable at point (1265, 593)
    //click in "kursplan"
    try{
      $(byXpath("//a[contains(@href, 'https://www.ltu.se/edu/course/I00/I0015N/I0015N-Test-av-IT-system-1.81215?kursView=kursplan')]")).shouldBe(visible).click();
        logger.info("Kursplan clicked");
    } catch (Exception e) {
      logger.error("Kursplan not found");
    }



        //DOES NOT WORK is not clickable at point (1265, 500)
        //click in kursplan
        try {
            $(byXpath("//a[@href='https://www.ltu.se/edu/course/I00/I0015N/I0015N-Test-av-IT-system-1.81215?kursView=kursplan']")).waitUntil(visible, 5000).click();
            logger.info("Kursplan clicked");
        } catch (Exception e) {
            logger.error("Kursplan not found");
        }
    }

         */
}

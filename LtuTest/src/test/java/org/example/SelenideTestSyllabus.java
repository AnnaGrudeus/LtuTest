package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.ex.ElementNotFound;
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
            //open ltu.se
            try{
            open("https://ltu.se");
            logger.info("ltu.se opened");
            } catch (Exception e) {
                logger.error("Error opening ltu.se");
            }

            //accept cookies
            try {
            var element = $(byText("Tillåt urval"));
            element.click();
            logger.info("Cookies accepted");
            } catch (ElementNotFound e) {
                logger.error("Error accepting cookies");
            }

            // <button class="button is-medium ltu-search-btn" aria-label="Sök" onclick="showTopInput()">
            //                    <i class="fa fa-search" aria-hidden="true"></i>
            //                </button>
            //click on search button
            try{
            var element = $(byXpath("//button[@class='button is-medium ltu-search-btn']"));
            element.click();
            logger.info("Search button clicked");
            } catch (ElementNotFound e) {
                logger.error("Error clicking search button");
            }

            sleep(3000);

            try{
                var element = $(byXpath("//input[@id='cludo-search-bar-input']"));
                element.sendKeys("I0015N");
                element.sendKeys(Keys.ENTER); // press the enter key instead of clicking the search button
                logger.info("Search for course I0015N");
                } catch (ElementNotFound e) {
                    logger.error("Error searching for course I0015N");
                }
              sleep(3000);


        }
}

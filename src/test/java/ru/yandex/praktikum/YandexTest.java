package ru.yandex.praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class YandexTest {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/questions.csv")
    public void allQuestionsHaveAnswerText(String question, String answer) {
        Actions actions = new Actions(driver);

        driver.get("https://qa-scooter.praktikum-services.ru/");
        driver.findElement(By.cssSelector(".App_CookieButton__3cvqF")).click();

        WebElement questionElement = driver.findElement(By.xpath(String.format("//div[@class='accordion__button' and text()='%s']", question)));
        actions
                .moveToElement(questionElement)
                .perform();
        questionElement.click();

        WebElement answerElement = driver.findElement(By.xpath(String.format("//p[text()='%s']", answer)));
        actions
                .moveToElement(answerElement)
                .perform();

        assertTrue(answerElement.isDisplayed());
    }
}

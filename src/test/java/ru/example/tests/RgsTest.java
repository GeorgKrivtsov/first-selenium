package ru.example.tests;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RgsTest {

    @Test
    public void test() {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");

        WebDriver driver = new ChromeDriver();

        driver.get("http://www.rgs.ru");


    }

}

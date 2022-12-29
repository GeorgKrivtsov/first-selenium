package ru.example.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RgsTest {

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");

        driver = new ChromeDriver();

        //wait = new WebDriverWait(driver, 10, 2);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.manage().window().maximize(); //развернуть браузер
        driver.get("http://www.rgs.ru");
    }

    @Test
    public void test() {

        //driver.navigate().to("http://www.rgs.ru"); сохраняет историю перемещений по сайтам

        driver.switchTo().frame("fl-616371");
        WebElement closePoint = driver.findElement(By.xpath("//div[@data-fl-track='click-close-login']"));
        closePoint.click();

        driver.switchTo().defaultContent();

        WebElement cookie = driver.findElement(By.xpath("//button[@class='btn--text']"));
        cookie.click();

        //WebElement baseMenu = driver.findElement(By.linkText("Компаниям"));//поиск по тексту ссылке
        WebElement baseMenu = driver.findElement(By.xpath("//*[text()='Компаниям']"));
        baseMenu.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Проверка загрузки страницы
        WebElement titleCompany = driver.findElement(By.xpath("//li[@id='thumb-0']"));

        Assert.assertTrue("Страничка не загрузилась", titleCompany.isDisplayed() &&
                titleCompany.getText().contains("Ответственность проектировщиков"));

        WebElement subMenu = driver.findElement(By.xpath("//span[text()='Здоровье']"));
        subMenu.click();

        WebElement parentSubMenu = subMenu.findElement(By.xpath("./.."));

        //Проверка загрузки всплывающего меню
        Assert.assertTrue("Клик не был совершен", parentSubMenu.getAttribute("class").contains("active"));


        WebElement healthButton = driver.findElement(By.xpath("//a[@href='/for-companies/zdorove/dobrovolnoe-meditsinskoe-strakhovanie'] "));
        healthButton.click();

        //Проверка загрузки страницы
        WebElement titleHeader = driver.findElement(By.xpath("//h1[@class='title word-breaking title--h2']"));

        Assert.assertEquals("Страница здоровье не открыта", "Добровольное медицинское страхование",
                titleHeader.getText());

        WebElement buttonSent = driver.findElement(By.xpath("//button[@type='button' and @class='action-item btn--basic']"));
        buttonSent.click();



        WebElement userName = driver.findElement(By.xpath("//input[@name='userName']"));
        userName.sendKeys("Георгий");

        WebElement phoneNumber = driver.findElement(By.xpath("//input[@name='userTel']"));
        phoneNumber.sendKeys("800-000-0000");

        WebElement userEmail = driver.findElement(By.xpath("//input[@name='userEmail']"));
        userEmail.sendKeys("qwertyqwerty");

        WebElement userAddress = driver.findElement(By.xpath("//input[@type='text' and @class='vue-dadata__input']"));
        userAddress.sendKeys("Краснодарский край, г Сочи, ул Тимирязева");

        driver.switchTo().parentFrame();

        WebElement checkBox = driver.findElement(By.xpath("//input[@type='checkbox']"));
        checkBox.click();

        WebElement buttonConnect = driver.findElement(By.xpath("//button[@type='submit' and text()='Свяжитесь со мной']"));
        buttonConnect.click();

        WebElement errorText = driver.findElement(By.xpath("//span[@class='input__error text--small'] "));

        Assert.assertEquals("не правильный текст ошибки", "Введите корректный адрес электронной почты", errorText.getText());
    }

    //@After
    public void after() {
        driver.quit();
    }

}

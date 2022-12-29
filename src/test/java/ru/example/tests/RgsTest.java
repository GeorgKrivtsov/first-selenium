package ru.example.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RgsTest {

    WebDriver driver;
    WebDriverWait wait;

    JavascriptExecutor js;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");

        driver = new ChromeDriver();

        //js = (JavascriptExecutor) driver;

        //wait = new WebDriverWait(driver, 10, 2);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));



        driver.manage().window().maximize(); //развернуть браузер
        driver.get("http://www.rgs.ru");
    }

    @Test
    public void test() {

        //driver.navigate().to("http://www.rgs.ru"); сохраняет историю перемещений по сайтам

        //Ждем и закрываем всплывающее окно
        driver.switchTo().frame("fl-616371");
        WebElement closePoint = driver.findElement(By.xpath("//div[@data-fl-track='click-close-login']"));
        closePoint.click();

        driver.switchTo().defaultContent();

        //Закрываем куки
        WebElement cookie = driver.findElement(By.xpath("//button[@class='btn--text']"));
        cookie.click();

        //нажимаем кнопку "Компаниям"
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

        //открываем вкладку "Здоровье"
        WebElement subMenu = driver.findElement(By.xpath("//span[text()='Здоровье']"));
        subMenu.click();

        //Проверка загрузки всплывающего меню
        WebElement parentSubMenu = subMenu.findElement(By.xpath("./.."));
        Assert.assertTrue("Клик не был совершен", parentSubMenu.getAttribute("class").contains("active"));

        //нажимаем на кнопку ДМС
        WebElement healthButton = driver.findElement(By.xpath("//a[@href='/for-companies/zdorove/dobrovolnoe-meditsinskoe-strakhovanie'] "));
        healthButton.click();

        //Проверка загрузки страницы
        WebElement titleHeader = driver.findElement(By.xpath("//h1[@class='title word-breaking title--h2']"));
        Assert.assertEquals("Страница здоровье не открыта", "Добровольное медицинское страхование",
                titleHeader.getText());

        //Нажатие на кнопку "отправить заявку"
        WebElement buttonSent = driver.findElement(By.xpath("//button[@type='button' and @class='action-item btn--basic']"));
        buttonSent.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Заполняем форму
        WebElement userName = driver.findElement(By.xpath("//input[@name='userName']"));
        userName.sendKeys("Георгий");
        WebElement phoneNumber = driver.findElement(By.xpath("//input[@name='userTel']"));
        phoneNumber.sendKeys("800-000-0000");
        WebElement userEmail = driver.findElement(By.xpath("//input[@name='userEmail']"));
        userEmail.sendKeys("qwertyqwerty");



        scrollWithOffset(driver.findElement(By.xpath("//input[@type='checkbox']")), 0, 250);
        //WebElement checkBox = driver.findElement(By.id("data-v-62a801c6"));
        WebElement checkBox = driver.findElement(By.xpath("//input[@type='checkbox']/.."));
        checkBox.click();

        WebElement userAddress = driver.findElement(By.xpath("//input[@type='text' and @class='vue-dadata__input']"));
        userAddress.sendKeys("Краснодарский край, г Сочи, ул Тимирязева, д 11");

        //Нажимаем кнопку "Свяжитесь со мной"
        WebElement buttonConnect = driver.findElement(By.xpath("//button[@type='submit' and text()='Свяжитесь со мной']"));
        buttonConnect.click();

        //Проверка сообщения об ошибке
        WebElement errorText = driver.findElement(By.xpath("//span[@class='input__error text--small']"));
        Assert.assertEquals("не правильный текст ошибки", "Введите корректный адрес электронной почты", errorText.getText());
    }

    @After
    public void after() {
        driver.quit();
    }

    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driver).executeScript(code, element, x, y);
        return element;
    }

}

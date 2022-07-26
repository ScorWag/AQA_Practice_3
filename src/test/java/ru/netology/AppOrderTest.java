package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppOrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testFormSuccess() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("p[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void testFormEmptyName() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormEmptyNameAndPhone() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormEmptyPhone() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormCheckboxNotPressed() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector(".input_invalid .checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);
    }

    @Test
    void testFormInvalidName() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Steve Wozniak");
        form.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void testFormInvalidNameAndPhone() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Steve Wozniak");
        form.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7987");
        form.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void testFormInvalidPhone() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7987");
        form.findElement(By.cssSelector("label[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

}

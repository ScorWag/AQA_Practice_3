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

        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testFormSuccess() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testFormSuccessCompositeName() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив-Иван Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testFormSuccessCompositeFamily() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Стив Возняк-Берджгановски");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testFormSuccessCompositeNameAndFamily() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Стив-Иван Возняк-Берджгановски");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testFormSuccessCompositeNameAndFamilyWithSpaces() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Стив - Иван Возняк - Берджгановски");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testFormSuccessOneRussianSymbol() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("В");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                text.trim());
    }

    @Test
    void testFormCheckboxNotPressed() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector(".button_view_extra")).click();
        form.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
    }

    @Test
    void testFormInvalidNameLatinSymbols() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Steve Wozniak");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testFormInvalidNameOneSpecialSymbols() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив& Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testFormInvalidNameOnlySpecialSymbols() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("& <>");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testFormInvalidNameOnArabian() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("ستيف وزنياك");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testFormInvalidNameAndPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Steve Wozniak");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7987");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                text.trim());
    }

    @Test
    void testFormEmptyNameAndPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormEmptyName() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormEmptyPhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testFormIncompletePhone() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7987");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormInvalidPhoneWithoutSymbolPlus() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79876345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormInvalidPhoneWithSpaces() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7 987 634 56 87");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormInvalidPhoneWithHyphen() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7987-634-56-87");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormInvalidPhoneWithBrackets() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7(987)6345687");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormInvalidPhoneInRussianLetters() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("телефон");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormInvalidPhoneInEnglishLetters() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("phone");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testFormInvalidPhoneInSpecSymbols() {
        WebElement form = driver.findElement(By.cssSelector("form"));

        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Стив Возняк");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+????%%%;;**");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button_view_extra")).click();
        String text = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

}

package ru.yandex.gotulx;

// Базовый класс для запуска драйвера браузера Chrome
// и его утилизации

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 25); // 10 сек для загрузки ютуба оказалось мало!
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

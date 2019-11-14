package ru.yandex.gotulx;

// ������� ����� ��� ������� �������� �������� Chrome
// � ��� ����������

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
        wait = new WebDriverWait(driver, 25); // 10 ��� ��� �������� ����� ��������� ����!
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

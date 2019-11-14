package ru.yandex.gotulx;

// В этом классе производится тестирование входа в магазин
// путем различных переборов последовательностей "Логин" и "Пароль"
// с последующим анализом ожидаемого состояния

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import org.openqa.selenium.chrome.ChromeDriver;


public class Test2 extends Base {
    // Массив даных для тестов
    TstSampl[] arrayOfTstSampl =
            {
                    new TstSampl("xgotix92@gmail.com", "Tester", true),
                    new TstSampl("Xgotix92@gmail.com", "Tester", true),  // ? Неверный регистр в "Логин"
                    new TstSampl("xgotix92@gmail.coM ", "Tester", true), // ? Лишний пробел и неверный регистр в "Логин"
                    new TstSampl(" xgotix92@Gmail.com ", "Tester", true), // ? Два лишних пробела и неверный регистр в "Логин"
                    new TstSampl("xgotix92@gmail.com", "tester", false),
                    new TstSampl("xgotix92@gmail.com", " Tester", true), // ? Лишний пробел в "Пароль"
                    new TstSampl("xgotix92@gmail.com", "Tester ", true), // ? Лишний пробел в "Пароль"
                    new TstSampl("xgotix92@gmail.com", " Testerr", false),
                    new TstSampl("xgotix92@gmail.com", "Te ster", false)

            };
    // Частная проверка отдельного экземпляра тестовой последовательности "Логин","Пароль"
    private void checkLogPas(int i) {
        WebElement tmpWebElem;  // ВрЕменная переменная

        // Наполнение поля "Логин"
        tmpWebElem = driver.findElement(By.id("id_email"));
        tmpWebElem.clear();
        tmpWebElem.sendKeys(arrayOfTstSampl[i].log);

        // Наполнение поля "Пароль"
        driver.findElement(By.id("id_password")).sendKeys(arrayOfTstSampl[i].pas);

        // Попытка входа
        driver.findElement(By.id("login-submit-btn")).click();

        if (arrayOfTstSampl[i].expect) {
            wait.until(titleIs("Управление магазином"));
            // Вошли, теперь выход назад для следующего теста
            driver.get("http://sa-rc.litebox.ru/accounts/login/");
            wait.until(titleIs("Войти"));
        }
        else {
            // Надо дождаться алерта с информацией о неверном пароле
            wait.until(presenceOfElementLocated(By.xpath(
                    "//div[contains(@class,'alert') and contains(string(),'пароль не вер')]")));
                    // Элемент див у которпого
                    // класс содержит "alert"
                    // и string содержит "пароль не вер"
            // И погасить этот алерт
            tmpWebElem = driver.findElement(By.xpath("//div[contains(@class,'alert')]/button[@class='close']"));
                                                   // Элемент див у которпого
                                                   // класс содержит "alert"
                                                   // и button c классом равным "close"
            tmpWebElem.click();
        }
    }

    @Test
    public void test2() {

        driver.get("http://sa-rc.litebox.ru/accounts/login/");
        wait.until(titleIs("Войти"));

        // Пробег по всем тестам
        for (int i = 0; i <arrayOfTstSampl.length ; i++) {
            checkLogPas(i);
        }
    }
}

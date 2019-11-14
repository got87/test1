package ru.yandex.gotulx;

// В этом классе производится общее предварительное тестирование страницы http://sa-rc.litebox.ru/accounts/login/
// проверяются на вход все возможные внешние ссылки из страницы и возврат из них обратно

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Test1 extends Base {
    private String saveWin;
    private Set<String> existingWindows;

    // Тест перехода по ссылке найденной по xpath локатору
    // С выходом на главную страницу
    private void preTest(String xPathStr, String acceptStr) {
        WebElement elemTmp = driver.findElement(By.xpath(xPathStr));
        elemTmp.click();
        wait.until(titleIs(acceptStr));
        // Выход в глав меню
        driver.get("http://sa-rc.litebox.ru/accounts/login/");
        wait.until(titleIs("Войти"));
    }

    // Тест перехода по ссылке найденной по xpath локатору
    // С выходом на главную страницу через ссылку по xpath локатору
    private void preTest(String xPathStr, String acceptStr, String xPathExitStr) {
        WebElement elemTmp = driver.findElement(By.xpath(xPathStr));
        elemTmp.click();
        wait.until(titleIs(acceptStr));
        // Выход назад
        elemTmp = driver.findElement(By.xpath(xPathExitStr));
        elemTmp.click();
        wait.until(titleIs("Войти"));
    }

    // Ожидание появления нового окна, код честно стырен с интернета
    private ExpectedCondition<String> anyWindowOtherThat(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver input) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next() : null;
            }
        };
    }

    // Тест перехода по ссылке которая влечет новое окно или вкладку
    private void testOtherWindow(WebElement webElem, String waitTytle) {
        String newWin;
        // Перейдем по выбранному элементу
        webElem.click();
        // Дождемся нового окна(вкладки)
        newWin = wait.until(anyWindowOtherThat(existingWindows));
        // Переключимся на него
        driver.switchTo().window(newWin);
        // Подтверждение входа
        wait.until(titleIs(waitTytle));
        // Теперь это окно закроем
        driver.close();
        // и вернемся в исходное
        driver.switchTo().window(saveWin);
        wait.until(titleIs("Войти"));
    }

    @Test
    public void test1() {
        WebElement tmpWebElem;  // ВрЕменная переменная

        // Вход на страницу
        driver.get("http://sa-rc.litebox.ru/accounts/login/");
        wait.until(titleIs("Войти"));

        // Запоминание текущего окна
        saveWin = driver.getWindowHandle();
        // И списка всех открытых окон
        existingWindows = driver.getWindowHandles();

        // Порождает новое окно logo в браузере, со всеми вытекающими последствиями
        tmpWebElem = driver.findElement(By.className("logo"));
        testOtherWindow(tmpWebElem, "Автоматизация магазина - кассовая программа для розничной торговли, система учета продаж LiteBox");

        // Переход в новое окно "Корзина"
        tmpWebElem = driver.findElement(By.className("btn-shop"));
        testOtherWindow(tmpWebElem, "Купить онлайн-кассу 54-ФЗ для ИП и ООО (УСН,ОСНО и ЕНВД) – доставка по Москве и всей России");

        // Проверка входа в демо-режим
        preTest("//a[@class='btn btn-lg btn-primary col-xs-12']", "Добро пожаловать в ДЕМО-режим");

        // Проверка входа в регистрацию
        preTest("//div[contains(@class,'col-xs-12 links')]/a[1]", "Регистрация"); // На допинге

        // Проверка на вход в восстановление пароля
        preTest("//a[@class='button secondaryAction']", "Сброс пароля",
                "/html/body/div[2]/div/div/form/p[2]/a");

        // Проверка на вход не приходит рассылка
        preTest("//*[@id=\"login-form\"]/div[5]/div/a[3]/span", "Не приходят email?",
                "/html/body/div[2]/div/div/p/a");

        // Проверка на вход в тарифы
        preTest("//div[@class='links']/a[1]/span", "Тарифы Litebox");

        // Проверка перехода к центру поддержки
        preTest("//div[@class='links']/a[2]/span", "Support LiteBox");

        // Проветка входа в телефон. Пока непонятно как
//        tmpWebElem =  driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/a[3]/span"));
//        tmpWebElem.click();

        // Проверка входа в facebook
        tmpWebElem =  driver.findElement(By.xpath("//a[@class='soc_fb']"));
        testOtherWindow(tmpWebElem, "Facebook");

        // Проверка входа в вКонтакт
        tmpWebElem =  driver.findElement(By.xpath("//a[@class='soc_vk']"));
        testOtherWindow(tmpWebElem, "LiteBox. Автоматизация торговли. ЕГАИС. 54-ФЗ | ВКонтакте");

        // Проверка входа в youtube
        tmpWebElem =  driver.findElement(By.xpath("//a[@class='soc_yotube']"));
        testOtherWindow(tmpWebElem, "info info - YouTube");
    }
}

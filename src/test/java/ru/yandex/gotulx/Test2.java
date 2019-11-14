package ru.yandex.gotulx;

// � ���� ������ ������������ ������������ ����� � �������
// ����� ��������� ��������� ������������������� "�����" � "������"
// � ����������� �������� ���������� ���������

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import org.openqa.selenium.chrome.ChromeDriver;


public class Test2 extends Base {
    // ������ ����� ��� ������
    TstSampl[] arrayOfTstSampl =
            {
                    new TstSampl("xgotix92@gmail.com", "Tester", true),
                    new TstSampl("Xgotix92@gmail.com", "Tester", true),  // ? �������� ������� � "�����"
                    new TstSampl("xgotix92@gmail.coM ", "Tester", true), // ? ������ ������ � �������� ������� � "�����"
                    new TstSampl(" xgotix92@Gmail.com ", "Tester", true), // ? ��� ������ ������� � �������� ������� � "�����"
                    new TstSampl("xgotix92@gmail.com", "tester", false),
                    new TstSampl("xgotix92@gmail.com", " Tester", true), // ? ������ ������ � "������"
                    new TstSampl("xgotix92@gmail.com", "Tester ", true), // ? ������ ������ � "������"
                    new TstSampl("xgotix92@gmail.com", " Testerr", false),
                    new TstSampl("xgotix92@gmail.com", "Te ster", false)

            };
    // ������� �������� ���������� ���������� �������� ������������������ "�����","������"
    private void checkLogPas(int i) {
        WebElement tmpWebElem;  // ��������� ����������

        // ���������� ���� "�����"
        tmpWebElem = driver.findElement(By.id("id_email"));
        tmpWebElem.clear();
        tmpWebElem.sendKeys(arrayOfTstSampl[i].log);

        // ���������� ���� "������"
        driver.findElement(By.id("id_password")).sendKeys(arrayOfTstSampl[i].pas);

        // ������� �����
        driver.findElement(By.id("login-submit-btn")).click();

        if (arrayOfTstSampl[i].expect) {
            wait.until(titleIs("���������� ���������"));
            // �����, ������ ����� ����� ��� ���������� �����
            driver.get("http://sa-rc.litebox.ru/accounts/login/");
            wait.until(titleIs("�����"));
        }
        else {
            // ���� ��������� ������ � ����������� � �������� ������
            wait.until(presenceOfElementLocated(By.xpath(
                    "//div[contains(@class,'alert') and contains(string(),'������ �� ���')]")));
                    // ������� ��� � ���������
                    // ����� �������� "alert"
                    // � string �������� "������ �� ���"
            // � �������� ���� �����
            tmpWebElem = driver.findElement(By.xpath("//div[contains(@class,'alert')]/button[@class='close']"));
                                                   // ������� ��� � ���������
                                                   // ����� �������� "alert"
                                                   // � button c ������� ������ "close"
            tmpWebElem.click();
        }
    }

    @Test
    public void test2() {

        driver.get("http://sa-rc.litebox.ru/accounts/login/");
        wait.until(titleIs("�����"));

        // ������ �� ���� ������
        for (int i = 0; i <arrayOfTstSampl.length ; i++) {
            checkLogPas(i);
        }
    }
}

package ru.yandex.gotulx;

// � ���� ������ ������������ ����� ��������������� ������������ �������� http://sa-rc.litebox.ru/accounts/login/
// ����������� �� ���� ��� ��������� ������� ������ �� �������� � ������� �� ��� �������

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

    // ���� �������� �� ������ ��������� �� xpath ��������
    // � ������� �� ������� ��������
    private void preTest(String xPathStr, String acceptStr) {
        WebElement elemTmp = driver.findElement(By.xpath(xPathStr));
        elemTmp.click();
        wait.until(titleIs(acceptStr));
        // ����� � ���� ����
        driver.get("http://sa-rc.litebox.ru/accounts/login/");
        wait.until(titleIs("�����"));
    }

    // ���� �������� �� ������ ��������� �� xpath ��������
    // � ������� �� ������� �������� ����� ������ �� xpath ��������
    private void preTest(String xPathStr, String acceptStr, String xPathExitStr) {
        WebElement elemTmp = driver.findElement(By.xpath(xPathStr));
        elemTmp.click();
        wait.until(titleIs(acceptStr));
        // ����� �����
        elemTmp = driver.findElement(By.xpath(xPathExitStr));
        elemTmp.click();
        wait.until(titleIs("�����"));
    }

    // �������� ��������� ������ ����, ��� ������ ������ � ���������
    private ExpectedCondition<String> anyWindowOtherThat(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver input) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next() : null;
            }
        };
    }

    // ���� �������� �� ������ ������� ������ ����� ���� ��� �������
    private void testOtherWindow(WebElement webElem, String waitTytle) {
        String newWin;
        // �������� �� ���������� ��������
        webElem.click();
        // �������� ������ ����(�������)
        newWin = wait.until(anyWindowOtherThat(existingWindows));
        // ������������ �� ����
        driver.switchTo().window(newWin);
        // ������������� �����
        wait.until(titleIs(waitTytle));
        // ������ ��� ���� �������
        driver.close();
        // � �������� � ��������
        driver.switchTo().window(saveWin);
        wait.until(titleIs("�����"));
    }

    @Test
    public void test1() {
        WebElement tmpWebElem;  // ��������� ����������

        // ���� �� ��������
        driver.get("http://sa-rc.litebox.ru/accounts/login/");
        wait.until(titleIs("�����"));

        // ����������� �������� ����
        saveWin = driver.getWindowHandle();
        // � ������ ���� �������� ����
        existingWindows = driver.getWindowHandles();

        // ��������� ����� ���� logo � ��������, �� ����� ����������� �������������
        tmpWebElem = driver.findElement(By.className("logo"));
        testOtherWindow(tmpWebElem, "������������� �������� - �������� ��������� ��� ��������� ��������, ������� ����� ������ LiteBox");

        // ������� � ����� ���� "�������"
        tmpWebElem = driver.findElement(By.className("btn-shop"));
        testOtherWindow(tmpWebElem, "������ ������-����� 54-�� ��� �� � ��� (���,���� � ����) � �������� �� ������ � ���� ������");

        // �������� ����� � ����-�����
        preTest("//a[@class='btn btn-lg btn-primary col-xs-12']", "����� ���������� � ����-�����");

        // �������� ����� � �����������
        preTest("//div[contains(@class,'col-xs-12 links')]/a[1]", "�����������"); // �� �������

        // �������� �� ���� � �������������� ������
        preTest("//a[@class='button secondaryAction']", "����� ������",
                "/html/body/div[2]/div/div/form/p[2]/a");

        // �������� �� ���� �� �������� ��������
        preTest("//*[@id=\"login-form\"]/div[5]/div/a[3]/span", "�� �������� email?",
                "/html/body/div[2]/div/div/p/a");

        // �������� �� ���� � ������
        preTest("//div[@class='links']/a[1]/span", "������ Litebox");

        // �������� �������� � ������ ���������
        preTest("//div[@class='links']/a[2]/span", "Support LiteBox");

        // �������� ����� � �������. ���� ��������� ���
//        tmpWebElem =  driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/a[3]/span"));
//        tmpWebElem.click();

        // �������� ����� � facebook
        tmpWebElem =  driver.findElement(By.xpath("//a[@class='soc_fb']"));
        testOtherWindow(tmpWebElem, "Facebook");

        // �������� ����� � ��������
        tmpWebElem =  driver.findElement(By.xpath("//a[@class='soc_vk']"));
        testOtherWindow(tmpWebElem, "LiteBox. ������������� ��������. �����. 54-�� | ���������");

        // �������� ����� � youtube
        tmpWebElem =  driver.findElement(By.xpath("//a[@class='soc_yotube']"));
        testOtherWindow(tmpWebElem, "info info - YouTube");
    }
}

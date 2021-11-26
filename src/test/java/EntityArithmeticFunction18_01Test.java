import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import  static utils.TestUtils.*;
import static utils.EntityUtils.*;

public class EntityArithmeticFunction18_01Test extends BaseTest {

    public void sendValues(String f1, String f2) {
        getDriver().findElement(By.xpath("//input[@data-field_name = 'f1']")).sendKeys(f1);
        getDriver().findElement(By.xpath("//input[@data-field_name = 'f2']")).sendKeys(f2);
    }

    public void checkResults(String sum, String sub, String mul, String div) {
        Assert.assertEquals(getDriver().findElement(By.id("sum")).getText(), sum);
        Assert.assertEquals(getDriver().findElement(By.id("sub")).getText(), sub);
        Assert.assertEquals(getDriver().findElement(By.id("mul")).getText(), mul);
        Assert.assertEquals(getDriver().findElement(By.id("div")).getText(), div);
    }

    public void preconditions(String[] valuesAndExpectedResults) {
        scrollClick(getDriver(), By.xpath("//p[text() = ' Arithmetic Function ']"));
        createNew(getDriver());
        sendValues(valuesAndExpectedResults[0], valuesAndExpectedResults[1]);
//        checkResults(valuesAndExpectedResults[2], valuesAndExpectedResults[3], valuesAndExpectedResults[4], valuesAndExpectedResults[5]);
        checkResults("", "", "", "");
        getWait().until(ExpectedConditions.
                elementToBeClickable(By.id("pa-entity-form-save-btn"))).click();
    }

    public void checkResultString(String[] valuesAndExpectedResults) {
        List<WebElement> rows = getDriver().findElements(By.xpath("//tr[@data-row_id]"));
        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        Assert.assertEquals(rows.size(), 1);
        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(elements.get(i).getText(), valuesAndExpectedResults[i]);
        }
    }

    public void clearF(){
        getDriver().findElement(By.xpath("//input[@data-field_name = 'f1']")).clear();
        getDriver().findElement(By.xpath("//input[@data-field_name = 'f2']")).clear();
    }

    @Test
    public void testCreateNewRecord() {
        final String[] valuesResult = new String[] {"25", "5", "0", "0", "0", "0"};
        preconditions(valuesResult);
        checkResultString(valuesResult);
    }

    @Test
    public void testViewRecord() {
        final String[] valuesResult = new String[] {"25", "5", "0", "0", "0", "0"};
        preconditions(valuesResult);
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//tr[@data-index = '0']//button"))).click();
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//tr[@data-index = '0']//a[text() ='view']"))).click();
        List<WebElement> elements = getDriver().findElements(By.xpath("//div[@class = 'card-body']//span"));
        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(elements.get(i).getText(), valuesResult[i]);
        }
    }

    @Test
    public void testEditRecord() {
        final String[] valuesResult = new String[] {"25", "5", "0", "0", "0", "0"};
        final String[] incorrectValuesResult = new String[] {"abc", "8", "0", "0", "0", "0"};
        final String[] newValuesResult = new String[] {"9", "3", "0", "0", "0", "0"};

        preconditions(valuesResult);
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//tr[@data-index = '0']//button"))).click();
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//tr[@data-index = '0']//a[text() ='edit']"))).click();
        clearF();
        sendValues(incorrectValuesResult[0], incorrectValuesResult[1]);
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.id("pa-entity-form-save-btn"))).click();
        List<WebElement> elements = getDriver().findElements(By.xpath("//div[@class = 'card-body']//span"));
        boolean alarm = false;
        for (int i = 0; i < elements.size(); i++) {
            String str = elements.get(i).getAttribute("class");
            if((str.contains("is-focused")))
                alarm = true;
        }
        Assert.assertTrue(alarm);
        clearF();
        sendValues(newValuesResult[0], newValuesResult[1]);
        checkResults("", "", "", "");
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.id("pa-entity-form-save-btn"))).click();
        checkResultString(newValuesResult);
    }

    @Test
    public void testDeleteRecord() {
        final String[] valuesResult = new String[] {"25", "5", "0", "0", "0", "0"};
        preconditions(valuesResult);
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//tr[@data-index = '0']//button"))).click();
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//tr[@data-index = '0']//a[text() ='delete']"))).click();
        List<WebElement> elements = getDriver().findElements(By.xpath("//div[@class = 'card-body']//tr"));
        Assert.assertTrue(elements.size()==0);
    }
}

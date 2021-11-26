import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.scroll;
import static utils.TestUtils.scrollClick;

public class EntityEventsChain2_021_01_15Test extends BaseTest {

    @Test
    public void testNewRecord() {
        final String f1Value = "1";
        final String[] expectedValues = {"1", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        List<String> actualValues = new ArrayList<>();

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//div[@id='menu-list-parent']/ul/li/a/p[text()=' Events Chain 2 ']/..")));
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.id("f1")).sendKeys(f1Value);
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//button[text() = 'Save']")));
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text() = 'Save']")));
        getDriver().findElement(By.xpath("//button[text() = 'Save']")).click();

        List<WebElement> row = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td/a"));

        for (int i = 0; i < elements.size(); i++) {
            actualValues.add(i, elements.get(i).getText());
        }

        Assert.assertEquals(actualValues.size(), expectedValues.length);
        Assert.assertEquals(row.size(), 1);

        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(actualValues.get(i), expectedValues[i]);
        }
    }

    @Test
    public void testViewRecord() {

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Events Chain 2 ']")));

        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.id("f1")).sendKeys("1");
        scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-save-btn")));
        scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));

        List<WebElement> referenceElements = getDriver().findElements(By.xpath("//table/tbody/tr/td/a"));
        List<String> referenceValues = new ArrayList<>();

        for (int i = 0; i < referenceElements.size(); i++) {
            referenceValues.add(i, referenceElements.get(i).getText());
        }
        getDriver().findElement(By.xpath("//td/div/button[@data-toggle = 'dropdown']")).click();
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.linkText("view")))
                .click();

        List<WebElement> viewElements = getDriver().findElements(By.className("pa-view-field"));
        List<String> actualValues = new ArrayList<>();
        for (int i = 0; i < viewElements.size(); i++) {
            actualValues.add(i, viewElements.get(i).getText());
        }
        Assert.assertEquals(actualValues.size(), referenceValues.size());

        for (int i = 0; i < actualValues.size(); i++) {
            Assert.assertEquals(actualValues.get(i), referenceValues.get(i));
        }
    }
    @Test
    public void testEditRecord(){
        final String newF1Value = "0";
        List<String> actualValues = new ArrayList<>();

        scrollClick(getDriver(), By.xpath("//p[text() = ' Events Chain 2 ']"));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("f1")).sendKeys("1");
        scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-save-btn")));
        scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));

        List<WebElement> referenceElements = getDriver().findElements(By.xpath("//table/tbody/tr/td/a"));
        List<String> referenceValues = new ArrayList<>();
        for (int i = 0; i < referenceElements.size(); i++) {
            referenceValues.add(i, referenceElements.get(i).getText());
        }

        getDriver().findElement(By.xpath("//td/div/button[@data-toggle = 'dropdown']")).click();
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.linkText("edit")))
                .click();

        getDriver().findElement(By.id("f1")).click();
        getDriver().findElement(By.id("f1")).clear();
        getDriver().findElement(By.id("f1")).sendKeys(newF1Value);
        scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-save-btn")));
        scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td/a"));

        Assert.assertEquals(rows.size(), 1);

        for(int i = 0; i < elements.size(); i++) {
            actualValues.add(i, elements.get(i).getText());
        }

        for(int i = 0; i < actualValues.size(); i ++) {
            Assert.assertEquals(actualValues.get(i), referenceValues.get(1));
        }
    }
}

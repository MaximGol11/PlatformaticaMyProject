import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.*;

public class EntityEventsChain201Test extends BaseTest {

    @Test
    public void testCreateNewRecord() {
        final String f1Value = "1";
        final String[] expectedValues = new String[]{"1", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        List<String> actualValues = new ArrayList<>();

        scrollClick(getDriver(), By.xpath("//p[text() = ' Events Chain 2 ']"));
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("f1")).sendKeys(f1Value);
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-save-btn")));
        scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td/a"));

        for(int i = 0; i < elements.size(); i++) {
            actualValues.add(i, elements.get(i).getText());
        }

        Assert.assertEquals(rows.size(), 1);
        Assert.assertEquals(elements.size(), expectedValues.length);

        for(int i = 0; i < expectedValues.length; i ++) {
            Assert.assertEquals(actualValues.get(i), expectedValues[i]);
        }
    }

    @Test
    public void testViewRecord() {
        scrollClick(getDriver(), By.xpath("//p[text() = ' Events Chain 2 ']"));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("f1")).sendKeys("1");
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-save-btn")));
        scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));

        List<WebElement> referenceElements = getDriver().findElements(By.xpath("//table/tbody/tr/td/a"));
        List<String> referenceValues = new ArrayList<>();
        for(int i = 0; i < referenceElements.size(); i ++) {
            referenceValues.add(i, referenceElements.get(i).getText());
        }

        getDriver().findElement(By.xpath("//td/div/button[@data-toggle = 'dropdown']")).click();
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.linkText("view")))
                .click();

        List<WebElement> viewElements = getDriver().findElements(By.className("pa-view-field"));
        List<String> actualValues = new ArrayList<>();
        for(int i = 0; i < viewElements.size(); i ++) {
            actualValues.add(i, viewElements.get(i).getText());
        }

        Assert.assertEquals(actualValues.size(), referenceValues.size());
        for (int i = 0; i < actualValues.size(); i ++) {
            Assert.assertEquals(actualValues.get(i), referenceValues.get(i));
        }
    }

    @Test
    public void testEditRecord() {
        final String newF1Value = "0";
        List<String> actualValues = new ArrayList<>();

        scrollClick(getDriver(), By.xpath("//p[text() = ' Events Chain 2 ']"));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("f1")).sendKeys("1");
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
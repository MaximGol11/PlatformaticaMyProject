import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;

public class EntityPlatformFunctions_3_Test extends BaseTest {
    private static final String LAST_INT = "80";
    private static final String LAST_STRING = "Last string";
    private static final String CONSTANT = "Be happy";

    private void fillDefaultValues() {
        WebElement blackField = getDriver().findElement(By.xpath("//div[@data-color = 'rose']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(blackField).build().perform();
        TestUtils.scrollClick(getDriver(), By.xpath("//a[@href = 'index.php?action=action_list&entity_id=66&mod=2']"));
        getDriver().findElement(By.className("card-icon")).click();
    }

    private void changeData() {
        WebElement last_int = getDriver().findElement(By.id("last_int"));
        last_int.sendKeys(Keys.CONTROL, "a");
        last_int.sendKeys(LAST_INT);
        WebElement last_string = getDriver().findElement(By.id("last_string"));
        last_string.sendKeys(Keys.CONTROL, "a");
        last_string.sendKeys(LAST_STRING);
        WebElement cons = getDriver().findElement(By.id("constant"));
        cons.sendKeys(Keys.CONTROL, "a");
        cons.sendKeys(CONSTANT);
    }

    private void savePlatform() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    private void saveDraftPlatform() {
        getDriver().findElement(By.id("pa-entity-form-draft-btn")).click();
    }



    @Test
    public void testEntityPlatformFunctionsCreateFirstRecord() {
        final String[] expectedValues = {"", "1", "null suffix", "contact@company.com", "menu"};

        fillDefaultValues();
        savePlatform();

        List<WebElement> actualValues1 = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));
        Assert.assertEquals(actualValues1.size(), expectedValues.length);
        for (int i = 0; i < actualValues1.size(); i++) {
            Assert.assertEquals(actualValues1.get(i).getText(), expectedValues[i]);
        }

        WebElement reset = getDriver().findElement(By.xpath("//a[text() = '!!! Reset all for my user !!!']"));
        TestUtils.jsClick(getDriver(), reset);

        fillDefaultValues();
        saveDraftPlatform();

        List<WebElement> actualValues2 = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));

        Assert.assertEquals(actualValues2.size(), expectedValues.length);
        for (int i = 0; i < actualValues2.size(); i++) {
            Assert.assertEquals(actualValues2.get(i).getText(), expectedValues[i]);
        }
    }

    @Test
    public void testEntityPlatformFunctionsCreateRecord() {
        fillDefaultValues();
        changeData();
        savePlatform();

        final String[] expectedValues = {"", "80", "Last string", "Be happy", "menu"};
        List<WebElement> actualValues1 = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));

        Assert.assertEquals(actualValues1.size(), expectedValues.length);
        for (int i = 0; i < actualValues1.size(); i++) {
            Assert.assertEquals(actualValues1.get(i).getText(), expectedValues[i]);
        }

        WebElement reset = getDriver().findElement(By.xpath("//a[text() = '!!! Reset all for my user !!!']"));
        TestUtils.jsClick(getDriver(), reset);

        fillDefaultValues();
        changeData();
        saveDraftPlatform();

        List<WebElement> actualValues2 = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));
        Assert.assertEquals(actualValues2.size(), expectedValues.length);
        for (int i = 0; i < actualValues2.size(); i++) {
            Assert.assertEquals(actualValues2.get(i).getText(), expectedValues[i]);
        }
    }

}

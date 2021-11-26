import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityEventsChain202Test extends BaseTest {

    @Test
    public void testCreateNewRecord() {
        final String f1Value = "1";
        final String[] expectedValues = {"1", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        List<String> actualValues = new ArrayList<>();

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//div[@id='menu-list-parent']/ul/li/a/p[text()=' Events Chain 2 ']/..")));
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.id("f1")).sendKeys(f1Value);
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text() = 'Save']")));
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//button[text() = 'Save']")));

        List<WebElement> rows  = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td/a"));

        for (int i = 0; i < elements.size(); i++) {
            actualValues.add(i, elements.get(i).getText());
        }

        Assert.assertEquals(actualValues.size(), expectedValues.length);
        Assert.assertEquals(rows.size(), 1);

        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(actualValues.get(i), expectedValues[i]);
        }
    }
}

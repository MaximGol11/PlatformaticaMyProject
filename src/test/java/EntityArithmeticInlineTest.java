import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.EntityUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.scrollClick;

public class EntityArithmeticInlineTest extends BaseTest {

    @Test
    public void testSaveDraft() {
        final String f1Value = "11";
        final String f2Value = "12";
        final String[] expectedValues = new String[] {"", f1Value, f2Value, "0", "0", "0", "0", "menu"};

        scrollClick(getDriver(), By.xpath("//p[text()=' Arithmetic Inline ']"));

        EntityUtils.createNew(getDriver());
        getDriver().findElement(By.id("f1")).sendKeys(f1Value);
        getDriver().findElement(By.id("f2")).sendKeys(f2Value);

        getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-draft-btn"))).click();

        List<String> actualValues = getDriver().findElements(By.xpath("//tbody//td"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(actualValues.size(), expectedValues.length);

        for (int i = 0; i < expectedValues.length; i++) {
            Assert.assertEquals(actualValues.get(i), expectedValues[i]);
        }
    }
}

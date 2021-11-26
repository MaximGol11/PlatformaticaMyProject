import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.scrollClick;

public class EntityArithmeticFunction04Test extends BaseTest {
    @Test
    public void testCreateRecord() {
        final String f1 = "8";
        final String f2 = "4";
        final String[] expectedValues = {"", "8", "4", "0", "0", "0", "0", "menu"};

        scrollClick(getDriver(), By.xpath("//p[text()=' Arithmetic Function ']"));
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.xpath("//input[@data-field_name='f1']")).sendKeys(f1);
        getDriver().findElement(By.xpath("//input[@data-field_name='f2']")).sendKeys(f2);
        getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.id("pa-entity-form-save-btn"))).click();

        List<String> actualValues = getDriver().findElements(By.xpath("//tbody//td"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        Assert.assertEquals(actualValues.size(), expectedValues.length);

        for (int i = 0; i < expectedValues.length; i++) {
            Assert.assertEquals(actualValues.get(i), expectedValues[i]);
        }
    }
}

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;

public class EntityPlaceholder2Test extends BaseTest {

    private static final String STRING_PLACEHOLDER = "String placeholder";
    private static final String TEXT_PLACEHOLDER = "Text placeholder";
    private static final String INT_PLACEHOLDER = "27";
    private static final String DECIMAL_PLACEHOLDER = "289.63";
    private static final String USER_PLACEHOLDER = "apptester1@tester.test";

    @Test
    public void inputRecordTest() {
        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(),'Placeholder')]"));
        getDriver().findElement(By.className("card-icon")).click();
        getDriver().findElement(By.id("string")).sendKeys(STRING_PLACEHOLDER);
        getDriver().findElement(By.id("text")).sendKeys(TEXT_PLACEHOLDER);
        getDriver().findElement(By.id("int")).sendKeys(INT_PLACEHOLDER);
        getDriver().findElement(By.id("decimal")).sendKeys(DECIMAL_PLACEHOLDER);

        TestUtils.scrollClick(getDriver(), By.xpath("//tr[@id='add-row-12']/td/button"));
        getDriver().findElement(By.id("t-12-r-1-string")).sendKeys(STRING_PLACEHOLDER);
        getDriver().findElement(By.id("t-12-r-1-text")).sendKeys(TEXT_PLACEHOLDER);
        getDriver().findElement(By.id("t-12-r-1-decimal")).sendKeys(DECIMAL_PLACEHOLDER);
        Select userDropdown = new Select(getDriver().findElement(By.id("t-12-r-1-user")));
        userDropdown.getFirstSelectedOption();
        TestUtils.scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));

        final String[] expectedValues = {"", STRING_PLACEHOLDER, TEXT_PLACEHOLDER, INT_PLACEHOLDER, DECIMAL_PLACEHOLDER, "", "",
                "", "", USER_PLACEHOLDER, "menu"};
        List<WebElement> actualValues = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));

        Assert.assertEquals(actualValues.size(), expectedValues.length);
        for (int i = 0; i < actualValues.size(); i++) {
            Assert.assertEquals(actualValues.get(i).getText(), expectedValues[i]);
        }
    }
}

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.EntityUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.scrollClick;

public class EntityParentInputChildTest extends BaseTest {

    private static final String STRING = "String";
    private static final String TEXT = "Text";
    private static final String INT = "22";
    private static final String DECIMAL = "22.22";
    private static final String DATE = "18/11/2021";
    private static final String DATETIME = "18/11/2021 23:08:06";

    private void createAndFillAndSaveEntity() {
        EntityUtils.createNew(getDriver());
        getDriver().findElement(By.id("string")).sendKeys(STRING);
        getDriver().findElement(By.id("text")).sendKeys(TEXT);
        getDriver().findElement(By.id("int")).sendKeys(INT);
        getDriver().findElement(By.id("decimal")).sendKeys(DECIMAL);
        getDriver().findElement(By.id("date")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("date")).sendKeys(DATE);
        getDriver().findElement(By.id("datetime")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("datetime")).sendKeys(DATETIME);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    @Test
    public void testCreateChild() {
        final String[] expectedValues = new String[] {
                "",
                STRING,
                TEXT,
                INT,
                DECIMAL,
                DATE,
                DATETIME,
                "",
                "apptester1@tester.test",
                "menu"
        };

        scrollClick(getDriver(), By.xpath("//p[text()=' Parent ']"));
        createAndFillAndSaveEntity();
        getDriver().findElement(By.xpath("//tbody")).click();
        createAndFillAndSaveEntity();

        List<String> actualValues = getDriver().findElements(By.xpath("//tbody//td"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(actualValues.size(), expectedValues.length);

        for (int i = 0; i < expectedValues.length; i++) {
            Assert.assertEquals(actualValues.get(i), expectedValues[i]);
        }
    }
}

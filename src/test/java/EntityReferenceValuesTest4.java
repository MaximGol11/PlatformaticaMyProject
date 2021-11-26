import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.EntityUtils;
import utils.TestUtils;

import java.util.List;

public class EntityReferenceValuesTest4 extends BaseTest {
    private static final String LABEL = "for the child";
    private static final String FILTER1 = "book title";
    private static final String FILTER2 = "author";

    @Test
    public void testCreateNewRecord() {
        TestUtils.scrollClick(getDriver(), By.xpath("//p[text()=' Reference values ']"));
        EntityUtils.createNew(getDriver());

        getDriver().findElement(By.id("label")).sendKeys(LABEL);
        getDriver().findElement(By.id("filter_1")).sendKeys(FILTER1);
        getDriver().findElement(By.id("filter_2")).sendKeys(FILTER2);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        List<WebElement> list = getDriver().findElements(By.xpath("//table[@id='pa-all-entities-table']//td "));

        Assert.assertEquals(list.get(1).getText(), LABEL);
        Assert.assertEquals(list.get(2).getText(), FILTER1);
        Assert.assertEquals(list.get(3).getText(), FILTER2);
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-check-square-o']")).isDisplayed());
        }
}

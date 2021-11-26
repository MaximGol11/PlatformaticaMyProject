import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.List;

public class EntityChildRecordsLoopTest2 extends BaseTest {

    private static final String START_BALANCE = "100.00";
    private static final String AMOUNT = "200.00";
    private static final String END_BALANCE = "300.00";
    private static final String ITEM_VALUE = "book";

    public void testCreateRecord() {
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(text(),'Child records loop')]")));
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        getDriver().findElement(By.xpath("//input[@id='start_balance']")).sendKeys(START_BALANCE);
        getDriver().findElement(By.xpath("//tbody/tr[@id='add-row-68']/td[1]/button[1]")).click();
        getDriver().findElement(By.xpath("//textarea[@id='t-68-r-1-amount']")).sendKeys(AMOUNT);
        getDriver().findElement(By.xpath("//textarea[@id='t-68-r-1-item']")).sendKeys(ITEM_VALUE);
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-save-btn"))).click();
    }

    @Test
    public void testViewExistingRecord(){

        testCreateRecord();

        getDriver().findElement(By.xpath("//div[@class = 'dropdown pull-left']//button")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'view')]")).click();

        List<WebElement> actualList = getDriver().findElements(By.xpath("//tbody/tr[(td>1)and(td<11)]/td"));
        final String[] expectedValues = {"1", AMOUNT, ITEM_VALUE};

        Assert.assertEquals(actualList.size(), expectedValues.length);
        for (int i = 0; i < actualList.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedValues[i]);
        }

        List<WebElement> actualListDataTable = getDriver().findElements(By.className("pa-view-field"));
        final String[] expectedValuesDataTable = {START_BALANCE, END_BALANCE};

        Assert.assertEquals(actualListDataTable.size(), expectedValuesDataTable.length);
        for (int i = 0; i < actualListDataTable.size(); i++) {
            Assert.assertEquals(actualListDataTable.get(i).getText(), expectedValuesDataTable[i]);
        }
    }
}

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.List;
import static utils.TestUtils.jsClick;

public class EntityReferenceValues5Test extends BaseTest {
    private final static String[] BASE_VALUES = {"test label", "test filter1", "test filter2"};

    private void openNewReferenceValuesForm () {
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Reference values ']")));
        getDriver().findElement(By.xpath("//i[normalize-space(text())='create_new_folder']")).click();
    }
    private void fillTheField(WebElement element, String text, boolean clearBeforeSend) {
        if (clearBeforeSend) {
            element.clear();
        }
        element.sendKeys(text);
    }

    private void filingFieldsReferenceValues(String label, String filter1, String filter2, boolean clearBeforeSend) {
        fillTheField(getDriver().findElement(By.id("label")), label, clearBeforeSend);
        fillTheField(getDriver().findElement(By.id("filter_1")), filter1, clearBeforeSend);
        fillTheField(getDriver().findElement(By.id("filter_2")), filter2, clearBeforeSend);
        getDriver().findElement(By.xpath("//button[normalize-space(text())='Save']")).click();
    }

    private void assertCheckingTableRows (String[] expectedResult) {
        List<WebElement> tableRows = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        for (int i = 0; i < tableRows.size(); i++) {
            Assert.assertEquals(tableRows.get(i).getText(), expectedResult[i]);
        }
    }

    private void createNewRecord(String[] values) {
        openNewReferenceValuesForm();
        filingFieldsReferenceValues(values[0], values[1], values[2], false);
    }
    private void selectActions(String visibleText) {
        getDriver().findElement(By.xpath("//div[@class = 'dropdown pull-left']//button")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//ul[@role = 'menu']"))));
        jsClick(getDriver(), getDriver().findElement(By.xpath("//a[text()='" + visibleText + "']")));
    }

    @Test
    public void testReferenceValuesInputNewRecord1() {
        createNewRecord(BASE_VALUES);
        assertCheckingTableRows(BASE_VALUES);
    }

    @Test
    public void testEditReferenceValuesRecord() {
        final String[] editValues = {"edit text", "edit filter1", "edit filter2"};
        createNewRecord(BASE_VALUES);
        selectActions("edit");
        filingFieldsReferenceValues(editValues[0], editValues[1], editValues[2], true);
        assertCheckingTableRows(editValues);
    }



    @Test
    public void testViewReferenceValueRecord() {
        createNewRecord(BASE_VALUES);
        selectActions("view");
        Assert.assertTrue(getDriver().findElement(By.xpath("//p[contains(text(), '" + BASE_VALUES[0] + "')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[contains(text(), '" + BASE_VALUES[1] + "')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[contains(text(), '" + BASE_VALUES[2] + "')]")).isDisplayed());
    }
}

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.EntityUtils.*;
import static utils.TestUtils.jsClick;

public class EntityReferenceValuesTest extends BaseTest {
    private static final String[] EXPECTED_VALUE = {"Label", "FILTER1", "FILTER2"};

    private void fillTheFields(String label, String filter1, String filter2, boolean cleanField) {
        fillTheField(getDriver().findElement(By.id("label")), label, cleanField);
        fillTheField(getDriver().findElement(By.id("filter_1")), filter1, cleanField);
        fillTheField(getDriver().findElement(By.id("filter_2")), filter2, cleanField);

        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    private void fillTheField(WebElement element, String text, boolean clearBeforeSend) {
        if (clearBeforeSend) {
            element.clear();
        }
        element.sendKeys(text);
    }

    private void selectDropDownMenuByName(String menuName) {
        getDriver().findElement(By.xpath("//div[@class = 'dropdown pull-left']//button")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//ul[@role = 'menu']"))));
        jsClick(getDriver(), getDriver().findElement(By.xpath("//a[text()='" + menuName + "']")));
    }

    @Test
    public void testCreateRecord() {
        openTestClassByName(getDriver(), "Reference values");
        clickAddCard(getDriver());
        fillTheFields(EXPECTED_VALUE[0], EXPECTED_VALUE[1], EXPECTED_VALUE[2], false);
        List<WebElement> actualResult = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));
        assertFields(EXPECTED_VALUE, actualResult, true);
    }

    @Test
    public void testEditRecord() {
        final String TEXT_FOR_EDIT = " TEST";
        String[] expectedValueForEdit = EXPECTED_VALUE;
        for (int i = 0; i < expectedValueForEdit.length; i++) {
            expectedValueForEdit[i] = expectedValueForEdit[i] + TEXT_FOR_EDIT;
        }
        testCreateRecord();
        selectDropDownMenuByName("edit");

        fillTheFields(expectedValueForEdit[0], expectedValueForEdit[1], expectedValueForEdit[2], true);
        List<WebElement> actualResult = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));
        assertFields(expectedValueForEdit, actualResult, true);
    }

    @Test
    public void testViewRecord() {
        testCreateRecord();
        selectDropDownMenuByName("view");
        Assert.assertTrue(getDriver().findElement(By.xpath("//p[contains(text(), '" + EXPECTED_VALUE[0] + "')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[contains(text(), '" + EXPECTED_VALUE[1] + "')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[contains(text(), '" + EXPECTED_VALUE[2] + "')]")).isDisplayed());
    }

    @Test
    public void testDeleteRecord() {
        testCreateRecord();
        selectDropDownMenuByName("delete");
        Assert.assertEquals(
                getDriver().findElement(By.className("card-body")).getText(),
                "");

        getDriver().findElement(By.xpath("//i[text() = 'delete_outline']")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class = 'pa-recycle-col']//b")));
        List<WebElement> actualResult = getDriver().findElements(By.xpath("//td[@class = 'pa-recycle-col']//b"));
        assertFields(EXPECTED_VALUE, actualResult, false);
    }
}
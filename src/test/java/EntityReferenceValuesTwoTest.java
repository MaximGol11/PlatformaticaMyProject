import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;

public class EntityReferenceValuesTwoTest extends BaseTest {
    private static final String LABEL = "t-shirt";
    private static final String FILTER_1 = "women's";
    private static final String FILTER_2 = "size";
    private static final String EDIT_LABEL = "pants";
    private static final String EDIT_FILTER_1 = "men's";
    private static final String EDIT_FILTER_2 = "price";

    private static final String[] REFERENCE_INPUT_VALUES = {
            LABEL, FILTER_1, FILTER_2};
    private static final String[] REFERENCE_EDIT_VALUES = {
            EDIT_LABEL, EDIT_FILTER_1, EDIT_FILTER_2};

    private static final By[] REFERENCE_LOCATORS = {
            By.id("label"),
            By.id("filter_1"),
            By.id("filter_2")};

    private void goToReferenceValuesPage() {
        TestUtils.scrollClick(getDriver(), By.xpath("//p[text()=' Reference values ']"));
    }

    private void clickOnCreateNewRecordButton() {
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
    }

    private void clickSaveButton() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    private void fillFields(String... inputValues) {
        for (int i = 0; i < inputValues.length; i++) {
            getDriver().findElement(EntityReferenceValuesTwoTest.REFERENCE_LOCATORS[i]).sendKeys(inputValues[i]);
        }
    }

    private void waitToBeDisplayed() throws InterruptedException {
        Thread.sleep(1000);
    }

    private void createRecord() {
        goToReferenceValuesPage();
        clickOnCreateNewRecordButton();
        fillFields(REFERENCE_INPUT_VALUES);
        clickSaveButton();
    }

    private void assertFieldEquals(List<WebElement> webElements, String... inputValues) {
        Assert.assertTrue(webElements.size() != 0);
        for (int i = 0; i < inputValues.length; i++) {
            Assert.assertEquals(webElements.get(i).getText(), inputValues[i]);
        }
    }

    private void assertReferenceValues(String[] inputValues) {
        for (int i = 0; i < inputValues.length; i++) {
            List<WebElement> webElementsValues = getDriver().findElements(By.xpath("//table[@id='pa-all-entities-table']//td"));
            webElementsValues.remove(0);
            assertFieldEquals(webElementsValues, inputValues);
        }
    }

    @Test
    public void testCreateNewRecord() {
        createRecord();
        assertReferenceValues(REFERENCE_INPUT_VALUES);
    }

    @Test
    public void testEditRecord() throws InterruptedException {
        createRecord();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//button/i[@class='material-icons']"))).click().perform();
        waitToBeDisplayed();
        actions.moveToElement(getDriver().findElement(By.xpath("//a[contains(text(),'edit')]"))).click().perform();
        getDriver().findElement(By.id("label")).clear();
        getDriver().findElement(By.id("label")).sendKeys(EDIT_LABEL);
        getDriver().findElement(By.id("filter_1")).clear();
        getDriver().findElement(By.id("filter_1")).sendKeys(EDIT_FILTER_1);
        getDriver().findElement(By.id("filter_2")).clear();
        getDriver().findElement(By.id("filter_2")).sendKeys(EDIT_FILTER_2);
        clickSaveButton();

        assertReferenceValues(REFERENCE_EDIT_VALUES);
    }
}

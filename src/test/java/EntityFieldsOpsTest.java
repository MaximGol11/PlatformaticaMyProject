import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityFieldsOpsTest extends BaseTest {

    private static final String JOHN_DOE = "John Doe";
    private static final String ANNA_BELLE = "Anna Belle";
    private static final String JUAN_CARLOS = "Juan Carlos";
    private static final String PENDING = "Pending";
    private static final String DONE = "Done";

    private static final By[] REFERENCE_LOCATORS = {
            By.id("label"),
            By.id("filter_1"),
            By.id("filter_2")};

    private static final String[][] REFERENCES_INPUT_VALUES = {
            {JOHN_DOE, "French", "France"},
            {ANNA_BELLE, "Italian", "Italy"},
            {JUAN_CARLOS, "Spanish", "Spain"}};

    private void navigateToReferenceValuesPage() {
        Actions act = new Actions(getDriver());
        act.moveToElement(getDriver().findElement(By.className("nav-link"))).perform();
        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(),'Reference values')]"));
    }

    private void fillFieldsFor(By[] locators, String... inputValues) {
        for (int i = 0; i < inputValues.length; i++) {
            getDriver().findElement(locators[i]).sendKeys(inputValues[i]);
        }
    }

    private void createReferenceValue(String[] inputValues) {
        clickOnCreateNewRecordButton();
        fillFieldsFor(REFERENCE_LOCATORS, inputValues);
        clickOnSaveButton();
    }

    private void createReferenceValues() {
        navigateToReferenceValuesPage();
        for (String[] inputValues : REFERENCES_INPUT_VALUES) {
            createReferenceValue(inputValues);
        }
    }

    private void navigateToFieldsOpsPage() {
        Actions act = new Actions(getDriver());
        act.moveToElement(getDriver().findElement(By.className("nav-link"))).perform();
        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(),'Fields Ops')]"));
    }

    private void clickOnCreateNewRecordButton() {
        getDriver().findElement(By.xpath("//i[contains(text(), 'create_new_folder')]")).click();
    }

    public static void waitToBeDisplayed() throws InterruptedException {
        Thread.sleep(1000);
    }

    private void clickOnSaveButton() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    private void assertFieldEquals(List<WebElement> webElements, String... inputValues) {
        Assert.assertTrue(webElements.size() != 0);
        for (int i = 0; i < inputValues.length; i++) {
            Assert.assertEquals(webElements.get(i).getText(), inputValues[i]);
        }
    }

    public void assertPreviewFormData(String[] inputValues) {
        List<WebElement> webElementsPreviewRecord = getDriver().findElements(By.xpath("//td[@class= 'pa-list-table-th']"));
        webElementsPreviewRecord.remove(0);
        assertFieldEquals(webElementsPreviewRecord, inputValues);
    }

    public void assertFormData(String[] inputValues) {
        WebElement dropdown = getDriver().findElement(By.xpath("//span[@class = 'pa-view-field']"));
        List<WebElement> webElementsForm = getDriver().findElements(By.xpath("//div[@class = 'form-group']/p"));
        webElementsForm.add(0, dropdown);
        Assert.assertEquals(inputValues.length, webElementsForm.size());
        assertFieldEquals(webElementsForm, inputValues);
    }

    public void openRecordFullView() throws InterruptedException {
        getDriver().findElement(By.xpath("//i[contains(text(), 'menu')]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//a[contains(text(), 'view')]")).click();
    }

    private boolean isSwitchOnAtPreviewRecord() {
        List<WebElement> squareCheckBox = getDriver().findElements(By.xpath("//td[@class = 'pa-list-table-th']/i[@class = 'fa fa-check-square-o']"));
        return squareCheckBox.size() != 0;
    }

    private boolean isSwitchOnAtFullViewRecord() {
        List<WebElement> squareCheckBox = getDriver().findElements(By.xpath("//div/i[@class = 'fa fa-check-square-o']"));
        return squareCheckBox.size() != 0;
    }

    private void createNewRecord() throws InterruptedException {
        navigateToFieldsOpsPage();
        clickOnCreateNewRecordButton();
        getDriver().findElement(By.className("toggle")).click();
        getDriver().findElement(By.className("filter-option-inner-inner")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//span[contains(text(), '" + DONE + "')]")).click();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[2]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//span[contains(text(), '" + JOHN_DOE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + ANNA_BELLE + "')]")).click();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[3]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("(//span[contains(text(), '" + JUAN_CARLOS + "')])[2]")).click();
        TestUtils.scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        clickOnSaveButton();
    }

    public void clickOnEditButton() throws InterruptedException {
        getDriver().findElement(By.xpath("//i[contains(text(), 'menu')]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//a[contains(text(), 'edit')]")).click();
    }

    public void clickOnDeleteButton() throws InterruptedException {
        getDriver().findElement(By.xpath("//i[contains(text(), 'menu')]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//a[contains(text(), 'delete')]")).click();
    }

    public int getNumberOfRecordDisplayed() {
        List<WebElement> element = getDriver().findElements(By.xpath("//tbody/tr"));
        return element.size();
    }

    public void clickOnTrashIcon() {
        getDriver().findElement(By.xpath("//i[@class = 'material-icons'][text() = 'delete_outline']")).click();
    }

    public void assertRecordDateInTrashBin(String[] values) {
        List<WebElement> elements = getDriver().findElements(By.xpath("//td[@style]"));
        elements.remove(2);
        elements.remove(0);
        assertFieldEquals(elements, values);
    }

    @Test
    public void testCreateNewRecord01() throws InterruptedException {
        createReferenceValues();

        navigateToFieldsOpsPage();
        clickOnCreateNewRecordButton();
        getDriver().findElement(By.className("toggle")).click();
        getDriver().findElement(By.className("filter-option-inner-inner")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//span[contains(text(), '" + DONE + "')]")).click();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[2]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//span[contains(text(), '" + JOHN_DOE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + ANNA_BELLE + "')]")).click();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[3]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("(//span[contains(text(), '" + JUAN_CARLOS + "')])[2]")).click();
        TestUtils.scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        clickOnSaveButton();

        Assert.assertTrue(isSwitchOnAtPreviewRecord());
        String[] values = {DONE, JOHN_DOE, ANNA_BELLE, JUAN_CARLOS};
        assertPreviewFormData(values);

        openRecordFullView();
        Assert.assertTrue(isSwitchOnAtFullViewRecord());
        assertFormData(values);
    }

    @Test
    public void testCreateNewRecord02() throws InterruptedException {
        createReferenceValues();

        navigateToFieldsOpsPage();
        clickOnCreateNewRecordButton();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[2]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//span[contains(text(), '" + ANNA_BELLE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + JOHN_DOE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + ANNA_BELLE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + JUAN_CARLOS + "')]")).click();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[3]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("(//span[contains(text(), '" + JUAN_CARLOS + "')])[2]")).click();
        TestUtils.scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        clickOnSaveButton();

        Assert.assertFalse(isSwitchOnAtPreviewRecord());
        String[] valuesForPreviewForm = {PENDING, ANNA_BELLE, JOHN_DOE + ", " + ANNA_BELLE + ", " + JUAN_CARLOS, JUAN_CARLOS};
        assertPreviewFormData(valuesForPreviewForm);

        openRecordFullView();
        Assert.assertFalse(isSwitchOnAtFullViewRecord());
        String[] valuesForFullViewForm = {PENDING, ANNA_BELLE, JOHN_DOE, ANNA_BELLE, JUAN_CARLOS, JUAN_CARLOS};
        assertFormData(valuesForFullViewForm);
    }

    @Test
    public void testEditRecord() throws InterruptedException {
        String[] expectedValuesForPreviewForm = {PENDING, ANNA_BELLE, JOHN_DOE + ", " + JUAN_CARLOS, ""};
        String[] expectedValuesForFullViewForm = {PENDING, ANNA_BELLE, JOHN_DOE, JUAN_CARLOS};

        createReferenceValues();
        createNewRecord();

        clickOnEditButton();
        getDriver().findElement(By.className("toggle")).click();
        getDriver().findElement(By.className("filter-option-inner-inner")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//span[contains(text(), '" + PENDING + "')]")).click();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[2]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("//span[contains(text(), '" + ANNA_BELLE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + JOHN_DOE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + ANNA_BELLE + "')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), '" + JUAN_CARLOS + "')]")).click();
        getDriver().findElement(By.xpath("(//div[@class=\"filter-option-inner\"])[3]")).click();
        waitToBeDisplayed();
        getDriver().findElement(By.xpath("(//span[contains(text(), '...')])[2]")).click();
        TestUtils.scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        clickOnSaveButton();

        Assert.assertFalse(isSwitchOnAtPreviewRecord());
        assertPreviewFormData(expectedValuesForPreviewForm);

        openRecordFullView();
        Assert.assertFalse(isSwitchOnAtFullViewRecord());
        assertFormData(expectedValuesForFullViewForm);
    }

     @Test
    public void deleteRecord() throws InterruptedException {
        final String[] expectedValues = {"Fields Ops", "restore as draft", "delete permanently"};

        createReferenceValues();
        createNewRecord();

        clickOnDeleteButton();
        Assert.assertEquals(getNumberOfRecordDisplayed(), 0);

        String numberAtTrashIcon = getDriver().findElement(By.xpath("//span[@class = 'notification']/b")).getText();
        Assert.assertEquals(numberAtTrashIcon, "1");

        clickOnTrashIcon();
        assertRecordDateInTrashBin(expectedValues);
    }
}
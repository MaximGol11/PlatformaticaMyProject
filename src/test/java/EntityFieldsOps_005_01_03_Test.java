import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityFieldsOps_005_01_03_Test extends BaseTest {

    private static final By CREATE_NEW_FOLDER_BUTTON_LOCATOR = By.xpath("//i[text()='create_new_folder']");
    private static final By SAVE_BUTTON_LOCATOR = By.id("pa-entity-form-save-btn");

    private static final String FIRST_DATA = "John Doe";
    private static final String SECOND_DATA = "Anna Belle";
    private static final String THIRD_DATA = "Juan Carlos";
    private static final String DONE_ITEM_OF_DROPDOWN = "Done";
    private static final String PENDING_ITEM_OF_DROPDOWN = "Pending";
    private static final String EMAIL = "contact@company.com";
    private static final String EMPTY_STRING = "";
    private static final String VIEW_BUTTON = "view";

    private void setFields(By[] locators, String[] inputValues) {
        for (int i = 0; i < inputValues.length; i++) {
            getDriver().findElement(locators[i]).sendKeys(inputValues[i]);
        }
        clickActionMethod(SAVE_BUTTON_LOCATOR);
    }

    private void clickActionMethod(By locator) {
        getDriver().findElement(locator).click();
    }

    public void fillFieldsWithPreconditionData() {
        final By[] FORM_FIELDS_LOCATORS = {
                By.xpath("//input[@id='label']"),
                By.xpath("//input[@id='filter_1']"),
                By.xpath("//input[@id='filter_2']")};
        scrollClick(getDriver(), By.xpath("//p[normalize-space()='Reference values']/../i"));
        clickActionMethod(CREATE_NEW_FOLDER_BUTTON_LOCATOR);
        setFields(FORM_FIELDS_LOCATORS, new String[]{FIRST_DATA, "French", "France"});
        clickActionMethod(CREATE_NEW_FOLDER_BUTTON_LOCATOR);
        setFields(FORM_FIELDS_LOCATORS, new String[]{SECOND_DATA, "Italian", "Italy"});
        clickActionMethod(CREATE_NEW_FOLDER_BUTTON_LOCATOR);
        setFields(FORM_FIELDS_LOCATORS, new String[]{THIRD_DATA, "Spanish", "Spain"});
    }

    public void navigateToEntity() {
        clickActionMethod(By.xpath("//p[normalize-space()='Fields Ops']/../i"));
        clickActionMethod(CREATE_NEW_FOLDER_BUTTON_LOCATOR);
    }

    public void selectMultireferenceOptions(String value) {
        getDriver().findElement(By.xpath("//label[@class = 'form-check-label'][contains(text(),".concat("'").concat(value).concat("')]"))).click();
    }

    public void pushEmbedPlusRowButton(int numbersOfRows) {
        for (int i = 0; i < numbersOfRows; i++) {
            clickActionMethod(By.xpath("//button[@data-table_id = '15']"));
        }
    }

    public void changeToggle() {
        clickActionMethod(By.xpath("//span[@class = 'toggle']"));
    }

    public void selectRightMenuOptions(String values) {
        clickActionMethod(By.xpath("//i[@class='material-icons'][text()='menu']"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickActionMethod(By.xpath("//ul[@role='menu']/li/a[text()='".concat(values).concat("']")));
    }

    public void selectDropDownMenuOptions(String selector, String text) {
        new Select(getDriver().findElement(By.id(selector))).selectByVisibleText(text);
    }

    public void createNewRecord() {
        navigateToEntity();
        changeToggle();
        selectDropDownMenuOptions("dropdown", DONE_ITEM_OF_DROPDOWN);
        selectDropDownMenuOptions("reference", FIRST_DATA);
        selectMultireferenceOptions(FIRST_DATA);
        selectMultireferenceOptions(SECOND_DATA);
        selectMultireferenceOptions(THIRD_DATA);
        selectDropDownMenuOptions("reference_with_filter", THIRD_DATA);
        pushEmbedPlusRowButton(1);
        clickActionMethod(By.xpath("//input[@id = 't-15-r-1-switch']/../span[@class='toggle']"));
        scrollClick(getDriver(), SAVE_BUTTON_LOCATOR);
    }

    public void assertMethod(List<WebElement> actual, String[] expected) {
        Assert.assertEquals(actual.size(), expected.length);
        Assert.assertTrue(expected.length != 0);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(actual.get(i).getText(), expected[i]);
        }
    }

    public void assertFirstPageData(String[] expectedValues) {
        List<WebElement> actualResult = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        assertMethod(actualResult, expectedValues);
    }

    public void assertSecondPage(String[] expectedValues) {
        List<WebElement> actualResultSecondPage = getDriver().findElements(By.xpath("//div[@class = 'form-group']/p"));
        assertMethod(actualResultSecondPage, expectedValues);
    }

    public void assertEmbedFO(String[] expectedValues) {
        List<WebElement> actualResultEmbed = getDriver().findElements(By.xpath("//tr[1]/td"));
        assertMethod(actualResultEmbed, expectedValues);
    }

    @Test
    public void testCreateRecord() {
        final String COMMA_SPACE = ", ";
        final String[] EXPECTED_RESULT_FIRST_PAGE = {EMPTY_STRING, DONE_ITEM_OF_DROPDOWN, FIRST_DATA, FIRST_DATA
                .concat(COMMA_SPACE).concat(SECOND_DATA).concat(COMMA_SPACE).concat(THIRD_DATA), THIRD_DATA, EMAIL};

        fillFieldsWithPreconditionData();
        createNewRecord();

        assertFirstPageData(EXPECTED_RESULT_FIRST_PAGE);
    }

    @Test
    public void testViewRecord() {
        final String[] EXPECTED_RESULT_POPUP = {FIRST_DATA, FIRST_DATA, SECOND_DATA, THIRD_DATA, THIRD_DATA};
        final String[] EXPECTED_EMBED_FO = {"1", EMPTY_STRING, "Plan", FIRST_DATA, THIRD_DATA, EMAIL, EMPTY_STRING};

        fillFieldsWithPreconditionData();
        createNewRecord();
        selectRightMenuOptions(VIEW_BUTTON);

        assertSecondPage(EXPECTED_RESULT_POPUP);
        assertEmbedFO(EXPECTED_EMBED_FO);
    }

    @Test
    public void testEditRecord() {
        final String EDIT_BUTTON = "edit";
        final String[] EXPECTED_RESULT_FIRST_PAGE = {EMPTY_STRING, PENDING_ITEM_OF_DROPDOWN, SECOND_DATA
                , SECOND_DATA, THIRD_DATA, EMAIL};
        final String[] EXPECTED_RESULT_POPUP = {SECOND_DATA, SECOND_DATA, THIRD_DATA};
        final String[] EXPECTED_EMBED_FO = {"1", EMPTY_STRING, "Done", SECOND_DATA, THIRD_DATA, EMAIL, EMPTY_STRING};

        fillFieldsWithPreconditionData();
        createNewRecord();
        selectRightMenuOptions(EDIT_BUTTON);
        changeToggle();
        selectDropDownMenuOptions("dropdown", PENDING_ITEM_OF_DROPDOWN);
        selectDropDownMenuOptions("reference", SECOND_DATA);
        selectMultireferenceOptions(THIRD_DATA);
        selectMultireferenceOptions(FIRST_DATA);
        selectDropDownMenuOptions("reference_with_filter", THIRD_DATA);
        selectDropDownMenuOptions("t-15-r-1-dropdown", DONE_ITEM_OF_DROPDOWN);
        selectDropDownMenuOptions("t-15-r-1-reference", SECOND_DATA);
        getDriver().findElement(By.id("t-15-r-1-multireference")).sendKeys("17643");
        scrollClick(getDriver(), SAVE_BUTTON_LOCATOR);

        assertFirstPageData(EXPECTED_RESULT_FIRST_PAGE);

        selectRightMenuOptions(VIEW_BUTTON);

        assertSecondPage(EXPECTED_RESULT_POPUP);
        assertEmbedFO(EXPECTED_EMBED_FO);
    }

    @Test
    public void testDeleteRecord() {
        final String DELETE_BUTTON = "delete";
        final int EXPECTED_RESULT = 1;

        fillFieldsWithPreconditionData();
        createNewRecord();
        selectRightMenuOptions(DELETE_BUTTON);
        clickActionMethod(By.xpath("//i[text()='delete_outline']"));
        List<WebElement> numbersOfRows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(numbersOfRows.size(), EXPECTED_RESULT);
    }

}
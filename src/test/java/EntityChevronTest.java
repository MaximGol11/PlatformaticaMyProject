import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntityChevronTest extends BaseTest {

    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By SAVE_DRAFT_BUTTON = By.id("pa-entity-form-draft-btn");
    private static final By STRING_STATUS_MENU = By.xpath("//div[@class='filter-option-inner-inner'][text()='Pending']");
    private static final By STRING_STATUS_PENDING = By.xpath("//span[text()='Pending']");
    private static final By STRING_PENDING = By.xpath("//div[text()='Pending']");
    private static final By PENDING_FILTER = By.xpath("//div/a[text() = 'Pending']");
    private static final By RED_NOTIFICATION = By.xpath("//span[@class='notification']");
    private static final By CREATE_NEW_FOLDER = By.xpath("//i[text()='create_new_folder']");
    private static final By TEXT_FIELD = By.xpath("//span[@class='bmd-form-group']/textarea");
    private static final By INT_FIELD = By.xpath("//span[@class='bmd-form-group']/input[@id='int']");
    private static final By DEC_FIELD = By.xpath("//div[@id='_field_container-decimal']//span/input");
    private static final By DATE_FIELD = By.xpath("//input[@id='date']");
    private static final By DATETIME_FIELD = By.xpath("//div[@id='_field_container-datetime']/input");
    private static final By ACTIONS_MENU = By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']");
    private static final By ACTIONS_DELETE_BUTTON = By.xpath("//a[text()='delete']");
    private static final By RECYCLE_BIN = By.xpath("//i[contains (text(), 'delete_outline')]");
    private static final By DELETE_PERMANENTLY = By.xpath("//a[text()='delete permanently']");

    private static final String CURRENT_DATE = createCurrentDateValue();
    private static final String CURRENT_DATETIME = createCurrentDateTimeValue();

    private void openNewChevronCreationWidget() {
        Actions moveMouse = new Actions(getDriver());
        moveMouse.moveToElement(getDriver().findElement(By.xpath("//a[@href='#menu-list-parent']"))).perform();
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Chevron ']")));
        getDriver().findElement(CREATE_NEW_FOLDER).click();
    }

    private void selectByVisibleTextStringStatus(String visibleText) {
        Select select = new Select(getDriver().findElement(By.id("string")));
        select.selectByVisibleText(visibleText);
    }

    private void selectByVisibleTextUserValue(String visibleText) {
        Select select = new Select(getDriver().findElement(By.id("user")));
        select.selectByVisibleText(visibleText);
    }

    private void sendInputs(String text,String int_,String dec){
        getDriver().findElement(TEXT_FIELD).sendKeys(text);
        getDriver().findElement(INT_FIELD).sendKeys(int_);
        getDriver().findElement(DEC_FIELD).sendKeys(dec);
        getDriver().findElement(DATE_FIELD).click();
        getDriver().findElement(DATETIME_FIELD).click();
    }

    private static String createCurrentDateValue() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormatter.format(date);
    }

    private static String createCurrentDateTimeValue() {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date datetime = new Date();
        return dateTimeFormatter.format(datetime);
    }

    private void fillInNewChevronRecord(String statusValue, String textValue, int intValue, double decimalValue, String dateValue, String datetimeValue, String userValue) {
        selectByVisibleTextStringStatus(statusValue);
        getDriver().findElement(By.id("text")).sendKeys(textValue);
        getDriver().findElement(By.id("int")).sendKeys(String.valueOf(intValue));
        getDriver().findElement(By.id("decimal")).sendKeys(String.valueOf(decimalValue));
        getDriver().findElement(By.id("date")).clear();
        getDriver().findElement(By.id("date")).sendKeys(dateValue);
        getDriver().findElement(By.id("datetime")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("datetime")).sendKeys(datetimeValue);
        selectByVisibleTextUserValue(userValue);
    }

    @Test
    public void testCreateRecord() {
        openNewChevronCreationWidget();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-index='0']")).isDisplayed());
    }

    @Test
    public void testSendRecordFilterPending() {
        openNewChevronCreationWidget();
        getDriver().findElement(STRING_PENDING).click();
        getDriver().findElement(By.id("text")).sendKeys("automation test");
        getDriver().findElement(By.id("int")).sendKeys("1");
        getDriver().findElement(By.id("decimal")).sendKeys("1.01");
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(PENDING_FILTER).click();
        getDriver().findElement(By.xpath("//button[contains(@class, 'btn-success')]")).click();
        WebElement actualResult = getDriver().findElement(By.xpath("//tr[@class='no-records-found']"));
        Assert.assertEquals(actualResult.getText(), "No matching records found");
    }

    @Test
    public void testCreateRecordPending() {
        openNewChevronCreationWidget();
        getDriver().findElement(STRING_STATUS_MENU).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getDriver().findElement(By.xpath("//span[text()='Pending']")).click();
        getDriver().findElement(SAVE_BUTTON).click();

        List<WebElement> recordValues = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertEquals(recordValues.get(0).getText(), "Pending");
    }

    @Test
    public void testCreateRecordSent() {
        openNewChevronCreationWidget();
        getDriver().findElement(STRING_STATUS_MENU).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getDriver().findElement(By.xpath("//span[text()='Sent']")).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(By.xpath("//a[text()='Sent']")).click();

        List<WebElement> recordValues = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertEquals(recordValues.get(0).getText(), "Sent");
    }

    @Test
    public void testCreateRecordFulfillment() {
        openNewChevronCreationWidget();
        getDriver().findElement(STRING_STATUS_MENU).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getDriver().findElement(By.xpath("//span[text()='Fulfillment']")).click();
        getDriver().findElement(SAVE_BUTTON).click();

        List<WebElement> recordValues = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertEquals(recordValues.get(0).getText(), "Fulfillment");
    }

    @Test
    public void testCreateRecordWithValidData() throws InterruptedException {
        openNewChevronCreationWidget();
        fillInNewChevronRecord("Pending", "Test", 1, 2.00, CURRENT_DATE, CURRENT_DATETIME, "apptester1@tester.test");
        JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        executor.executeScript("arguments[0].click();", getDriver().findElement(SAVE_BUTTON));

        List<WebElement> actualValues = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        final String[] expectedValues = {"Pending", "Test", "1", "2.00", CURRENT_DATE, CURRENT_DATETIME, "", "apptester1@tester.test"};

        for(int i = 0;i<actualValues.size();i++){
            Assert.assertEquals(actualValues.get(i).getText(), expectedValues[i]);
        }
    }

    @Test
    public void testCancelRecordWithValidData() {
        openNewChevronCreationWidget();
        fillInNewChevronRecord("Pending", "Test", 1, 2.00, CURRENT_DATE, CURRENT_DATETIME, "apptester1@tester.test");
        getDriver().findElement(By.xpath("//button[@class='btn btn-dark' and text()='Cancel']")).click();

        List<WebElement> actualList = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertTrue(actualList.size() == 0);
    }

    @Test
    public void testSaveDraftPending() {
        final String testingData = "Pending";
        openNewChevronCreationWidget();
        selectByVisibleTextStringStatus(testingData);
        getDriver().findElement(SAVE_DRAFT_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-index='0']//i[@class='fa fa-pencil']")).isDisplayed());
        Assert.assertEquals(getDriver().findElement(By.xpath("//table//a[contains(text(), 'Pending')]")).getText(), testingData);
    }

    @Test
    public void testSaveDraftFulfillment() {
        final String testingData = "Fulfillment";
        openNewChevronCreationWidget();
        selectByVisibleTextStringStatus(testingData);
        getDriver().findElement(SAVE_DRAFT_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-index='0']//i[@class='fa fa-pencil']")).isDisplayed());
        Assert.assertEquals(getDriver().findElement(By.xpath("//table//a[contains(text(), 'Fulfillment')]")).getText(), testingData);
    }

    @Test
    public void testSaveDraftSent() {
        final String testingData = "Sent";
        openNewChevronCreationWidget();
        selectByVisibleTextStringStatus(testingData);
        getDriver().findElement(SAVE_DRAFT_BUTTON).click();
        getDriver().findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=36&stage=Sent']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-index='0']//i[@class='fa fa-pencil']")).isDisplayed());
        Assert.assertEquals(getDriver().findElement(By.xpath("//table//a[contains(text(), 'Sent')]")).getText(), testingData);
    }

    @Test
    public void testSaveDraftWithValidData() {
        openNewChevronCreationWidget();
        fillInNewChevronRecord("Pending", "Test", 1, 2.00, CURRENT_DATE, CURRENT_DATETIME, "apptester1@tester.test");

        JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        executor.executeScript("arguments[0].click();", getDriver().findElement(SAVE_DRAFT_BUTTON));

        final String[] expectedValues = {"Pending", "Test", "1", "2.00", CURRENT_DATE, CURRENT_DATETIME, "", "apptester1@tester.test"};
        List<WebElement> actualList = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertEquals(actualList.size(), expectedValues.length);
        for (int i = 0; i < actualList.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedValues[i]);
        }
    }

    @Ignore
    @Test
    public void testDeleteRecord() {
        openNewChevronCreationWidget();
        fillInNewChevronRecord("Pending", "Test", 1, 2.00, CURRENT_DATE, CURRENT_DATETIME, "apptester1@tester.test");
        getDriver().findElement(SAVE_BUTTON).click();
        getWait().until(ExpectedConditions.elementToBeClickable(ACTIONS_MENU));
        getDriver().findElement(ACTIONS_MENU).click();
        getWait().until(ExpectedConditions.elementToBeClickable(ACTIONS_DELETE_BUTTON));
        getDriver().findElement(ACTIONS_DELETE_BUTTON).click();
        List<WebElement> recordValues = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertTrue(recordValues.size() == 0);
        Assert.assertTrue(getDriver().findElement(RED_NOTIFICATION).isDisplayed());

        getDriver().findElement(RED_NOTIFICATION).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//td[@class='pa-recycle-col']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//td[@class='pa-recycle-col']")).getText().contains("Pending"));
        Assert.assertTrue(getDriver().findElement(By.xpath("//td[@class='pa-recycle-col']")).getText().contains("Test"));
        Assert.assertTrue(getDriver().findElement(By.xpath("//td[@class='pa-recycle-col']")).getText().contains("1"));
        Assert.assertTrue(getDriver().findElement(By.xpath("//td[@class='pa-recycle-col']")).getText().contains("2.00"));
    }

    @Test
    public void testCreateRecordSent2(){
        final String expectedResult = "Sent";

        openNewChevronCreationWidget();

        sendInputs("Test","123","123.123");
        TestUtils.scrollClick(getDriver(),SAVE_BUTTON);

        WebElement sentButton = getDriver().findElement(By.xpath("//button[text()='Sent']"));
        sentButton.click();

        WebElement actualResult1 = getDriver().findElement(By.xpath("//tr/td"));
        Assert.assertEquals(actualResult1.getText(),"No matching records found");

        WebElement sentFilterButton = getDriver().findElement(By.xpath("//a[text()='Sent']"));
        sentFilterButton.click();

        WebElement actualResult2 = getDriver().findElement(By.xpath("//tr/td[2]"));
        Assert.assertEquals(actualResult2.getText(),expectedResult);
    }

    @Test
    public void testChevronDeleteFromRecycleBin() {
        openNewChevronCreationWidget();
        TestUtils.scrollClick(getDriver(), getDriver().findElement(SAVE_BUTTON));

        TestUtils.scrollClick(getDriver(), getDriver().findElement(ACTIONS_MENU));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']"))));
        getDriver().findElement(ACTIONS_DELETE_BUTTON).click();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(RECYCLE_BIN));
        getWait().until((ExpectedConditions.elementToBeClickable(getDriver().findElement(DELETE_PERMANENTLY)))).click();

        String text = getDriver().findElement(By.xpath("//div[@class='card-body']")).getText();
        Assert.assertEquals(text, "Good job with housekeeping! Recycle bin is currently empty!");
    }

    @Test
    public void testCheckExistingRecords(){
        final String testText = "New";
        final String testInt = "123456";
        final String testDecimal = "123456.1";

        openNewChevronCreationWidget();

        sendInputs(testText,testInt,testDecimal);

        getDriver().navigate().back();
        getDriver().navigate().forward();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span/textarea")).getAttribute("value"),testText);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='_field_container-int']/span/input")).getAttribute("value"),testInt);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='_field_container-decimal']/span/input")).getAttribute("value"),testDecimal);
    }
}
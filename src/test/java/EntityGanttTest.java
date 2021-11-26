import base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EntityGanttTest extends BaseTest {

    private static final String STRING = "String";
    private static final String TEXT = "Text Gantt";
    private static final String INT = "2";
    private static final String DECIMAL = "2.20";
    private static final String DATA = "01/01/2021";
    private static final String DATA_TIME = "01/01/2021 02:27:54";
    private static final String USER = "apptester4@tester.test";

    private void scrollClickblackField() {
        WebElement blackField = getDriver().findElement(By.xpath("//div[@data-color = 'rose']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(blackField).build().perform();
        TestUtils.scrollClick(getDriver(), By.xpath("//a[@href = 'index.php?action=action_list&entity_id=35&mod=2']"));
    }

    private static File createTempFile() throws IOException {
        return File.createTempFile("txt", "txt").getAbsoluteFile();
    }

    private void selectUser() {
        WebElement selectUs = getDriver().findElement(By.id("user"));
        Select dropdown = new Select(selectUs);
        dropdown.selectByValue("305");

    }
    private void fillingFields() {
        getDriver().findElement(By.className("card-icon")).click();
        getDriver().findElement(By.id("string")).sendKeys(STRING);
        getDriver().findElement(By.id("text")).sendKeys(TEXT);
        getDriver().findElement(By.id("int")).sendKeys(INT);
        getDriver().findElement(By.id("decimal")).sendKeys(DECIMAL);

        WebElement dat = getDriver().findElement(By.id("date"));
        dat.sendKeys(Keys.DELETE);
        dat.sendKeys(DATA);
        WebElement datTime = getDriver().findElement(By.id("datetime"));
        datTime.sendKeys(Keys.DELETE);
        datTime.sendKeys(DATA_TIME);
        selectUser();
    }

    private void inputGannt() {
        scrollClickblackField();
        fillingFields();
    }

    private void saveGannt() {
        getDriver().findElement(By.xpath("//form[@id = 'pa-entity-form']")).click();
        getDriver().findElement(By.xpath("//button[@id= 'pa-entity-form-save-btn']")).click();
    }

    private void clickOnList() {
        getDriver().findElement(By.xpath("//ul[contains(@class, 'nav-pills')]//i[text() = 'list']")).click();
    }

    private void selectAction (WebElement element) {
        TestUtils.jsClick(getDriver(), element);
    }

    private void clickOnGantt() {
        getDriver().findElement(By.xpath("//ul[contains(@class, 'nav-pills')]//i[text() = 'bar_chart']")).click();
    }

    private void inputNewGanttData(String string, String date) {
        TestUtils.scrollClick(getDriver(), By.xpath("//*[contains(text(), 'Gantt')]"));
        getDriver().findElement(By.xpath("//*[text()= 'create_new_folder']")).click();
        getDriver().findElement(By.xpath("//*[@id='string']")).sendKeys(string);
        WebElement field_date = getDriver().findElement(By.xpath("//*[@id='date']"));
        field_date.sendKeys(Keys.DELETE);
        field_date.sendKeys(date);
        TestUtils.scrollClick(getDriver(), By.xpath("//button[@data-id='user']"));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestUtils.scrollClick(getDriver(), By.xpath("//span[text()='tester6@tester.test']"));
    }

    private void createDraftGanttData(String string, String text, String intt, String decimal, String date, String datetime, String user) {
        TestUtils.scrollClick(getDriver(), By.xpath("//p[normalize-space()='Gantt']/../I"));
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.xpath("//input[@id='string']")).sendKeys(string);
        getDriver().findElement(By.xpath("//textarea[@id='text']")).sendKeys(text);
        getDriver().findElement(By.xpath("//input[@id='int']")).sendKeys(intt);
        getDriver().findElement(By.xpath("//input[@id='decimal']")).sendKeys(decimal);
        WebElement field_date = getDriver().findElement(By.xpath("//input[@id='date']"));
        field_date.sendKeys(Keys.DELETE);
        field_date.sendKeys(date + "\n");
        WebElement field_datetime = getDriver().findElement(By.xpath("//input[@id='datetime']"));
        field_datetime.sendKeys(Keys.DELETE);
        field_datetime.sendKeys(datetime + "\n");
        WebElement choiceUser = getDriver().findElement(By.xpath("//select[@id='user']"));
        Select dropdown = new Select(choiceUser);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dropdown.selectByVisibleText(user);
        TestUtils.scrollClick(getDriver(), By.xpath("//button[@id= 'pa-entity-form-draft-btn']"));
    }

    private boolean isElementPresent(By by) {
        try {
            getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private String randomDate(){

        LocalDate randomDay = LocalDate.ofEpochDay(RandomUtils.nextLong(1, 25000));
        String randomDate = randomDay.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return randomDate;
    }

    private String randomTime(){

        LocalTime randomTime = LocalTime.ofSecondOfDay(RandomUtils.nextLong(0, 86399));
        String result = randomTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return result;
    }

    @Test
    public void testEntityGanttInput() throws IOException {
        final String subData = "1/1/2021";

        inputGannt();
        WebElement file = getDriver().findElement(By.id("file"));
        TestUtils.scroll(getDriver(), file);
        file.sendKeys(createTempFile().getAbsolutePath());
        saveGannt();

        String blueElement = getDriver()
                .findElement(By.xpath("//div[@aria-label = 'Name String Start Date 1/1/2021 End Date 1/2/2021 Duration 2 days']"))
                .getAttribute("aria-label");

        Assert.assertEquals(getDriver().findElement
                (By.xpath("//td[@aria-label = 'String column header Task']")).getText(), STRING);
        Assert.assertTrue(blueElement.contains(subData));
    }

    @Test
    public void testEntityGanttView() throws IOException {
        File myFile = createTempFile();

        inputGannt();
        WebElement file = getDriver().findElement(By.id("file"));
        TestUtils.scroll(getDriver(), file);
        file.sendKeys(myFile.getAbsolutePath());
        saveGannt();
        getDriver().findElement(By.xpath("//ul[@class = 'pa-nav-pills-small nav nav-pills nav-pills-primary']//i[text() = 'list']")).click();

        final String[] expectedValues = {"", STRING, TEXT, INT, DECIMAL, DATA, DATA_TIME, myFile.getName(), USER, "menu"};
        List<WebElement> actualList = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));

        Assert.assertEquals(actualList.size(), expectedValues.length);
        for (int i = 0; i < actualList.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedValues[i]);
        }
    }

    @Ignore
    @Test
    public void testViewRecord() throws InterruptedException {
        final String STRING_FIELD = "First Gantt";
        final String TEXT = "Testing";
        final String INNITIAL_INT = "1";
        final String DECIMAL = "01";
        final String ID_BUTTON_SAVE = "pa-entity-form-save-btn";
        final String STRING_INPUT = "//input[@id='string']";
        final String TEXT_INPUT = "//textarea[@id='text']";
        final String INT_INPUT = "//input[@id='int']";
        final String DECIMAL_INPUT = "//input[@id='decimal']";
        final String DATA = "10/11/2021";
        final String DATA_TIME = "10/11/2021 16:40:48";
        final String LIST_BUTTON = "//a[@href='index.php?action=action_list&list_type=table&entity_id=35']";
        final String ACTIONS_BUTTON = "//i[text()='menu']";
        final String VIEW_MODE_BOTTON = "//a[text()='view']";

        final String expectedResult = "First Gantt";
        WebElement GanttButton = getDriver().findElement(By.xpath("//p[text()= ' Gantt ']"));
        TestUtils.jsClick(getDriver(), GanttButton);
        getDriver().findElement(By.className("card-icon")).click();
        getDriver().findElement(By.xpath(STRING_INPUT)).sendKeys(STRING_FIELD);
        getDriver().findElement(By.xpath(TEXT_INPUT)).sendKeys(TEXT);
        getDriver().findElement(By.xpath(INT_INPUT)).sendKeys(INNITIAL_INT);
        getDriver().findElement(By.xpath(DECIMAL_INPUT)).sendKeys(DECIMAL);
        WebElement dat = getDriver().findElement(By.id("date"));
        dat.sendKeys(Keys.DELETE);
        dat.sendKeys(DATA);
        WebElement datTime = getDriver().findElement(By.id("datetime"));
        datTime.sendKeys(Keys.DELETE);
        datTime.sendKeys(DATA_TIME);
        getDriver().findElement(By.id(ID_BUTTON_SAVE)).click();
        getDriver().findElement(By.xpath(LIST_BUTTON)).click();
        getDriver().findElement(By.xpath(ACTIONS_BUTTON)).click();
        getDriver().findElement(By.xpath(VIEW_MODE_BOTTON)).click();
        String actualResult = getDriver().findElement(By.xpath("//span[@class='pa-view-field']")).getText();

        Assert.assertEquals(actualResult, expectedResult);

    }

    @Test
    public void testCreateNewRecordSaveButtons() {
        String inputString = "Test_string";
        String inputDate = "1/1/2021";

        inputNewGanttData(inputString, inputDate);
        TestUtils.scrollClick(getDriver(), By.xpath("//button[@id= 'pa-entity-form-save-btn']"));

        String actualString = getDriver().findElement(By.xpath("//td[@aria-label = 'Test_string column header Task']")).getText();
        String actualDate = getDriver().findElement(By.xpath("//td[@aria-label = '1/1/2021 column header Start']")).getText();

        Assert.assertEquals(actualString, inputString);
        Assert.assertEquals(actualDate, inputDate);
        getDriver().findElement(By.xpath("//*[@aria-label = 'Name Test_string Start Date 1/1/2021 End Date 1/2/2021 Duration 2 days']"));
    }

    @Test
    public void testEntityGanttInput013_01_03() {

        final String s = "ss";

        TestUtils.scrollClick(getDriver(), By.xpath("//div[@id='menu-list-parent']/ul/li[12]/a"));
        getDriver().findElement(By.xpath("//*[contains(text(), 'create_new')]")).click();

        getDriver().findElement(By.id("string")).sendKeys(s);
        getDriver().findElement(By.id("date")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("date")).sendKeys("13.12.1901");
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        String dataShowed = getDriver().findElement(By.xpath("//table[@class='e-timeline-header-table-container'][1]/thead/tr//th[1]/div")).getText();
        Assert.assertEquals(dataShowed, "Dec 08, 1901");

        String lineData = getDriver().findElement(By.xpath("//table[@id='GanttTaskTableGanttContainer']//td/div[2]")).getAttribute("aria-label");
        String expectedString = "Name " + s + " Start Date 12/13/1901 End Date 12/14/1901 Duration 2 days";
        Assert.assertEquals(lineData, expectedString);
    }

    @Test
    public void testEntityGanttEdit() {
        final String newString = "New string";
        final String newData = "02/07/2021";
        final String subData = "7/2/2021";

        inputGannt();
        saveGannt();
        clickOnList();
        WebElement edit = getDriver().findElement(By.xpath("//a[text()='edit']"));
        selectAction(edit);

        WebElement string = getDriver().findElement(By.id("string"));
        string.sendKeys(Keys.CONTROL, "a");
        string.sendKeys(Keys.BACK_SPACE);
        string.sendKeys(newString);
        WebElement data = getDriver().findElement(By.id("date"));
        data.sendKeys(Keys.DELETE);
        data.sendKeys(newData);

        saveGannt();
        clickOnGantt();

        String blueElement = getDriver()
                .findElement(By.xpath("//div[@aria-label = 'Name New string Start Date 7/2/2021 End Date 7/3/2021 Duration 2 days']"))
                .getAttribute("aria-label");

        Assert.assertEquals(getDriver().findElement
                (By.xpath("//td[@aria-label = 'New string column header Task']")).getText(), newString);
        Assert.assertTrue(blueElement.contains(subData));
    }

    @Ignore
    @Test
    public void testEntityGanttEdit2() {
        final String changedStringValues = "String1";
        final String changedDataValues = "11/03/2020\n";
        final String savedDataValues = "3/12/2020";

        inputGannt();
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getDriver().findElement(By.xpath("//ul[contains(@class, 'nav-pills')]//i[text() = 'list']")).click();
        getDriver().findElement(By.xpath("//div[@class = 'dropdown pull-left']//button")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']"))));
        getDriver().findElement(By.xpath("//a[text()='edit']")).click();

        WebElement string = getDriver().findElement(By.id("string"));
        string.sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        string.sendKeys(changedStringValues);
        WebElement data = getDriver().findElement(By.id("date"));
        data.sendKeys(Keys.DELETE);
        data.sendKeys(changedDataValues);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        getDriver().findElement(By.xpath("//ul[contains(@class, 'nav-pills')]//i[text() = 'bar_chart']")).click();

        Assert.assertEquals(getDriver().findElement
                (By.xpath("//td[@aria-label = 'String1 column header Task']")).getText(), changedStringValues);
        Assert.assertTrue(getDriver().findElement
                        (By.xpath("//div[@aria-label = 'Name String1 Start Date 3/11/2020 End Date 3/12/2020 Duration 2 days']"))
                .getAttribute("aria-label")
                .contains(savedDataValues));
    }

    @Test
    public void testEntityGanttDelete() {
        final String expectedString1 = "No records to display";
        final String expectedString2 = "String: StringText: Text GanttInt: 2Decimal: 2.20Date: 2021-01-01Datetime: 2021-01-01 02:27:54User: 305";

        inputGannt();
        saveGannt();
        clickOnList();
        WebElement delete = getDriver().findElement(By.xpath("//a[text()='delete']"));
        selectAction(delete);
        clickOnGantt();

        Assert.assertEquals(getDriver().findElement(By.id("treeGridGanttContainer_gridcontrol_content_table")).getText(), expectedString1);

        getDriver().findElement(By.xpath("//ul[@class = 'navbar-nav']//i[text() = 'delete_outline']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td[@class = 'pa-recycle-col']")).getText(), expectedString2);
    }

    @Test
    public void testCreateNewRecordSaveDraftButtons() {
        String string = "Save_Draft_Button";
        String text = RandomStringUtils.randomAlphabetic(10);
        String intt = String.valueOf(RandomUtils.nextInt(1, 99));
        String decimal = RandomStringUtils.randomNumeric(1, 2)+"."+RandomStringUtils.randomNumeric(2);
        String date = randomDate();
        String datetime = date + " " + randomTime();
        String user = "tester6@tester.test";

        createDraftGanttData(string, text, intt, decimal, date, datetime, user);
        getDriver().findElement(By.xpath("//li[@class='nav-item']//i[text() ='list']")).click();
        Assert.assertTrue(isElementPresent(By.xpath("//a[contains(text(), 'Save_Draft_Button')]/../../td/i[@class='fa fa-pencil']")));
        final String[] expectedValues = {"", string, text, intt, decimal, date, datetime, "", user, "menu"};
        List<WebElement> actualList = getDriver().findElements(By.xpath("//a[contains(text(), 'Save_Draft_Button')]/../../td"));

        Assert.assertEquals(actualList.size(), expectedValues.length);
        for (int i = 0; i < actualList.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedValues[i]);
        }
    }
}
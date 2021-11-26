import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static utils.EntityUtils.*;

public class EntityPlaceholderTest extends BaseTest {
    private final String TITLE_VALUE = "TestString";
    private final String COMMENT_VALUE = "TestText";
    private final String INT_VALUE = "27";
    private final String DECIMAL_VALUE = "27.00";
    private final String DATE_VALUE = "25/05/2017";
    //dateValue must be in format [day/month/year]
    private final String TIME_VALUE = "05:05:05";
    //dateTimeValue must be in format [hour:minute:seconds]

    private final String FILE_NAME = "doneTXT.txt";
    private final String FILE_IMG_NAME = "doneJPG.jpg";
    private final String USER_NAME = "apptester1@tester.test";
    private final String[] EXPECTED_VALUES = {TITLE_VALUE, COMMENT_VALUE, INT_VALUE, DECIMAL_VALUE, DATE_VALUE, DATE_VALUE + " " + TIME_VALUE, FILE_NAME, FILE_IMG_NAME, USER_NAME};

    private void setDate(String id, String date) throws InterruptedException {
        if (date != null) {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(id))).click();
            String[] arrDate = date.split("/");
            int monthNumber = Integer.parseInt(arrDate[1]);
            //без слипа не работает ,после 2 запуска метода (через метод setDateAndTime) иногда падает тест
            sleep(1000);

            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[@title = 'Select Month']"))).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[@title = 'Select Year']"))).click();

            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-action = 'selectYear'][contains(text()," + arrDate[2] + ")]"))).click();
            List<WebElement> listMonth = new ArrayList<>(getDriver().findElements(By.xpath("//span[@data-action = 'selectMonth']")));
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-action = 'selectMonth'][contains(text(),'" + listMonth.get(monthNumber - 1).getText() + "')]"))).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-action = 'selectDay']/div[contains(text(),'" + arrDate[0] + "')]/.."))).click();
        }
    }

    private void setDateAndTime(String id, String date, String time) throws InterruptedException {
        if (time != null) {
            setDate(id, date);
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(id))).click();

            String[] arrTime = time.split(":");
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-action = 'togglePicker']"))).click();

            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-time-component = 'hours']"))).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-action = 'selectHour']//div[contains(text() ,'" + arrTime[0] + "')]"))).click();

            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-time-component = 'minutes']"))).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-action = 'selectMinute']//div[contains(text() ,'" + arrTime[1] + "')]"))).click();

            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-time-component = 'seconds']"))).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-action = 'selectSecond']//div[contains(text() ,'" + arrTime[2] + "')]"))).click();
        }
    }

    private void fillTheFields(String title, String comments, String integer, String decimal, String fileName, String fileNameImg) {
        getDriver().findElement(By.id("string")).sendKeys(title);
        getDriver().findElement(By.id("text")).sendKeys(comments);
        getDriver().findElement(By.id("int")).sendKeys(integer);
        getDriver().findElement(By.id("decimal")).sendKeys(decimal);
        getDriver().findElement(By.id("file")).sendKeys(new File("src/test/resources/" + fileName).getAbsolutePath());
        getDriver().findElement(By.id("file_image")).sendKeys(new File("src/test/resources/" + fileNameImg).getAbsolutePath());
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    @Test
    public void testCreateRecord() throws InterruptedException {
        openTestClassByName(getDriver(),"Placeholder");
        clickAddCard(getDriver());
        setDate("date", DATE_VALUE);
        setDateAndTime("datetime", DATE_VALUE, TIME_VALUE);
        fillTheFields(TITLE_VALUE, COMMENT_VALUE, INT_VALUE, DECIMAL_VALUE, FILE_NAME, FILE_IMG_NAME);
        List<WebElement> actualResult = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));
        assertFields(EXPECTED_VALUES,actualResult,true);
    }

    @Test
    public void testDeleteRecord(){
        TestUtils.scrollClick(getDriver(), getDriver().
                findElement(By.xpath("//p[text()=' Placeholder ']")));

        By createButtonLocator = By.xpath("//i[text() = 'create_new_folder']");
        WebElement createButton = getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(createButtonLocator)));
        List<WebElement> rowsBeforeCreated = getTableRows();
        createButton.click();

        TestUtils.scrollClick(getDriver(),getDriver().
                findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(createButtonLocator)));
        List<WebElement> rowsAfterCreated = getTableRows();

        TestUtils.scrollClick(getDriver(),getDriver().
                findElement(By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']"))));
        getDriver().findElement(By.xpath("//a[text()='delete']")).click();
        List<WebElement> rowsAfterDeleted = getTableRows();

        Assert.assertEquals(rowsBeforeCreated.size(), rowsAfterDeleted.size());
        Assert.assertEquals(rowsBeforeCreated.size()+1, rowsAfterCreated.size());
    }

    private List<WebElement> getTableRows(){
        return getDriver().findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr"));
    }
}

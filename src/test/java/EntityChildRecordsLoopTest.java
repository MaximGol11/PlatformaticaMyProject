import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.ArrayList;
import java.util.List;

public class EntityChildRecordsLoopTest extends BaseTest {

    public static final By START_BALANCE_FIELD = By.xpath("//input[@data-field_name = 'start_balance']");
    public static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");

    private void chooseChildRecordsLoop (){
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[.= ' Child records loop ']/..")));
    }

    private void fillOneRowChildRecordsLoop(Double startBalance, Double amountCost){

        chooseChildRecordsLoop ();
        WebDriver driver=getDriver();
        driver.findElement(By.xpath("//div[@class='card-icon']")).click();
        driver.findElement(By.xpath("//button[@data-table_id = '68']")).click();
        driver.findElement(By.xpath("//input[@data-field_name='start_balance']")).sendKeys(startBalance.toString());
        WebElement amount = driver.findElement(By.xpath("//textarea[@data-field_name='amount'][@data-row]"));
        amount.clear();
        amount.sendKeys(amountCost.toString());
        getWait().until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON)).click();
    }



    public void createChildRecordsLoopWithNoRecords(){
        WebElement childRecordsLoopMenuOption = getDriver().findElement(By.xpath("//li//p[.= ' Child records loop ']/.."));
        TestUtils.scrollClick(getDriver(), childRecordsLoopMenuOption);
        WebElement newChildRecordsLoopIcon = getDriver().findElement(By.className("card-icon"));
        newChildRecordsLoopIcon.click();
        getDriver().findElement(START_BALANCE_FIELD).sendKeys("50");
        implicitWait(100);
        getDriver().findElement(SAVE_BUTTON).click();
    }

    public void createNewRecordsWithRows(int numberOfRows) {

            WebElement newChildRecordsLoopIcon = getDriver().findElement(By.className("card-icon"));
            newChildRecordsLoopIcon.click();
            getDriver().findElement(START_BALANCE_FIELD).sendKeys("50");

            while (numberOfRows > 0) {
                getDriver().findElement(By.xpath("//button[@data-table_id = '68']")).click();
                numberOfRows--;
            }

            getWait().until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
            getDriver().findElement(SAVE_BUTTON).click();
    }

    private void implicitWait(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void selectFromTheDropList(String option){
        WebElement actionsIcon = getDriver().findElement(By.xpath("//button//div[@class='ripple-container']/.."));
        actionsIcon.click();
        List<WebElement> actionsDropDown = getDriver().findElements(By.xpath("//ul[@class = 'dropdown-menu dropdown-menu-right show']/li/a"));
        for(WebElement element: actionsDropDown){
            if(element.getText().equals(option)){
                element.click();
                break;
            }
        }
    }

    @Ignore
    @Test
    public void testEditChildRecordsLoopWithNoRecords(){
        createChildRecordsLoopWithNoRecords();

        selectFromTheDropList("edit");

        final String NEW_START_BALANCE_VALUE = "60.00";
        final String SUB_RECORD_AMOUNT_VALUE = "3.00";
        final String SUB_RECORD_ITEM_VALUE = "10000";
        final String newEndBalanceValue = "63.00";

        getDriver().findElement(START_BALANCE_FIELD).sendKeys(Keys.CONTROL + "a");
        getDriver().findElement(START_BALANCE_FIELD).sendKeys(NEW_START_BALANCE_VALUE);
        WebElement newSubRecordIcon = getDriver().findElement(By.xpath("//td[@class = 'pa-add-row-btn-col']/button"));
        newSubRecordIcon.click();
        WebElement subRecordAmountField = getDriver().findElement(By.xpath("//tbody/tr[last()-1]//textarea[@data-field_name = 'amount']"));
        WebElement subRecordItemField = getDriver().findElement(By.xpath("//tbody/tr[last()-1]//textarea[@data-field_name = 'item']"));
        subRecordAmountField.sendKeys(Keys.CONTROL + "a");
        subRecordAmountField.sendKeys(SUB_RECORD_AMOUNT_VALUE);
        subRecordItemField.sendKeys(SUB_RECORD_ITEM_VALUE);
        implicitWait(100);
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//tr/td[2][@class = 'pa-list-table-th']/a")).getText(), NEW_START_BALANCE_VALUE);

        selectFromTheDropList("view");
        WebElement startBalanceOnViewPage = getDriver().findElement(By.xpath("//div[2]/div/span[@class = 'pa-view-field']"));
        WebElement subRecordsAmountOnViewPage = getDriver().findElement(By.xpath("//tbody/tr[1]/td[2]"));
        WebElement subRecordsItemsOnViewPage = getDriver().findElement(By.xpath("//tbody/tr[1]/td[3]"));

        Assert.assertEquals(startBalanceOnViewPage.getText(), NEW_START_BALANCE_VALUE);
        Assert.assertEquals(subRecordsAmountOnViewPage.getText(), SUB_RECORD_AMOUNT_VALUE);
        Assert.assertEquals(subRecordsItemsOnViewPage.getText(), SUB_RECORD_ITEM_VALUE);
    }

    @Test
    public void testEditChildRecordsLoopDeleteRow() {
        WebElement childRecordsLoopMenu = getDriver().findElement(By.xpath("//li//p[.= ' Child records loop ']/.."));
        TestUtils.scrollClick(getDriver(), childRecordsLoopMenu);

        createNewRecordsWithRows(3);
        selectFromTheDropList("edit");

        getDriver().findElement(By.xpath("//tr[@id='row-68-3']/td[last()-1]")).click();
        getDriver().findElement(By.xpath("//tr[@id='row-68-2']/td[last()-1]")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON));
        getDriver().findElement(SAVE_BUTTON).click();

        selectFromTheDropList("view");

        List<WebElement> listOfRows = new ArrayList<>(getDriver().findElements(By.xpath("//tbody/tr")));

        Assert.assertEquals(listOfRows.size(), 2);
    }

    /** ** Precondition: **
     *       For the logged in user, there is one entry in the Child Records Loop, with the following attributes:
     *       Start balance: -22.30
     *       End balance: 18.30
     * ** Steps to reproduce: **
     *       1. In the left menu, find and select the "Child Records Loop" item.
     * *** Expected result ***:
     * - "toggle" switch in "off" position.
     * - The record is displayed in the form of a table
     * - The columns of the table are named: "Start balance"; "End balance"; "Actions".
     * - Values in a single line, respectively: -22.30; 18.30; menu.
     */
    @Test
    public void testToggleChildRecordsLoopDefaultPosition(){
        WebDriver driver = getDriver();
        Double startBalance = -22.30;
        Double amountCost = 40.60;
        fillOneRowChildRecordsLoop(startBalance, amountCost);
        chooseChildRecordsLoop ();
        WebElement toggle = driver.findElement(By.xpath("//button[@name='toggle']"));
        Assert.assertEquals(toggle.findElement(By.xpath(".//i")).getAttribute("class"),"fa fa-toggle-off");
        WebElement tableContaner = driver.findElement(By.xpath("//table[@id = 'pa-all-entities-table']"));
        Assert.assertEquals(tableContaner.findElement(By.xpath("./thead")).getAttribute("style"),"");
        Assert.assertEquals(tableContaner.findElement(By.xpath("./thead/tr")).getText(),
                "Start balance\nEnd balance\nActions");
        Assert.assertEquals(tableContaner.findElements(By.xpath("./tbody/tr")).size(),1);
        Assert.assertEquals(tableContaner.findElement(By.xpath("./tbody/tr")).getText(),
                String.format("%.2f\n%.2f\nmenu",startBalance, startBalance+amountCost));
    }
}

import base.BaseTest;
import base.BaseUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityArithmeticInline1Test extends BaseTest {

    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By SAVE_DRAFT_BUTTON = By.id("pa-entity-form-draft-btn");

    private void openArithmeticInlinePage(){
        Actions moveMouseOver = new Actions(getDriver());
        moveMouseOver.moveToElement(getDriver().findElement(By.xpath("//a[@href='#menu-list-parent']")));
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=54&mod=2']")));
    }

    private void createNewArithmeticInlineRecord(){
        getDriver().findElement(By.className("card-icon")).click();
    }

    private void fillInArithmeticInlineRecord(String f1, String f2) {
        getDriver().findElement(By.id("f1")).clear();
        getDriver().findElement(By.id("f1")).sendKeys(f1);
        getDriver().findElement(By.id("f2")).clear();
        getDriver().findElement(By.id("f2")).sendKeys(f2);
    }

    private void editRecord(){
        getDriver().findElement(By.xpath("//tr[@data-index='0']//div[@class='dropdown pull-left']")).click();

        JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        executor.executeScript("arguments[0].click();", getDriver().findElement(By.xpath("//a[text()=\"edit\"]")));
    }


    @Ignore("ignored because bug fixing is required https://trello.com/c/LaEtlsik")
    @Test
    public void testCheckTheCorrectCalculation() {
        openArithmeticInlinePage();
        createNewArithmeticInlineRecord();
        fillInArithmeticInlineRecord("6", "3");
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(SAVE_BUTTON))).click();

        String[] expectedResults = {"", "6", "3", "9", "3", "18", "2", ""};
        List<WebElement> actualResults = getDriver().findElements(By.xpath("//tr[1]/td"));
        Assert.assertEquals(actualResults.size(), expectedResults.length);
        for(int i = 0; i < expectedResults.length; i++) {
            Assert.assertEquals(actualResults.get(i).getText(), expectedResults[i]);
        }
    }

    @Ignore("ignored because bug fixing is required https://trello.com/c/LaEtlsik")
    @Test
    public void testCheckExistingRecordEdition() {
        openArithmeticInlinePage();
        createNewArithmeticInlineRecord();
        fillInArithmeticInlineRecord("6", "3");
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(SAVE_BUTTON))).click();
        editRecord();
        fillInArithmeticInlineRecord("8", "4");
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(SAVE_BUTTON))).click();

        String[] expectedResults = {"", "8", "4", "12","4", "32", "2", ""};
        List<WebElement> actualResults = getDriver().findElements(By.xpath("//tr[1]/td"));
        Assert.assertEquals(actualResults.size(), expectedResults.length);
        for(int i = 0; i < expectedResults.length; i++) {
            Assert.assertEquals(actualResults.get(i).getText(), expectedResults[i]);
        }
    }

    @Ignore("ignored because bug fixing is required https://trello.com/c/LaEtlsik")
    @Test
    public void testCheckSaveDraftButtonCreatesDraftRecord(){
        openArithmeticInlinePage();
        createNewArithmeticInlineRecord();
        fillInArithmeticInlineRecord("6", "3");
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(SAVE_DRAFT_BUTTON))).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-index='0']//i[@class='fa fa-pencil']")).isDisplayed());

        String[] expectedResults = {"", "6", "3", "9", "3", "18", "2", ""};
        List<WebElement> actualResults = getDriver().findElements(By.xpath("//tr[1]/td"));
        Assert.assertEquals(actualResults.size(), expectedResults.length);
        for(int i = 0; i < expectedResults.length; i++) {
            Assert.assertEquals(actualResults.get(i).getText(), expectedResults[i]);
        }
    }

    @Test
    public void testCheckCancelButtonDeletBeingCreatedRecord() {
        openArithmeticInlinePage();
        createNewArithmeticInlineRecord();
        fillInArithmeticInlineRecord("6","3");
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//button[text() = 'Cancel']")))).click();

        Assert.assertTrue(getDriver().findElements(By.xpath("//tr[@data-index = '0']")).size() == 0);
    }
}

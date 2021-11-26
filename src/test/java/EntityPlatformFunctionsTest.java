import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EntityPlatformFunctionsTest extends BaseTest {

    public void navigatePlatformFunctionsMenuItem() {
        Actions moveMouse = new Actions(getDriver());
        moveMouse.moveToElement(getDriver().findElement(By.className("sidebar"))).perform();
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Platform functions ']")));
    }

    public void createPlatformFunctionsMenuItem() {
        getDriver().findElement(By.className("card-icon")).click();
    }

    public void savePlatformFunctions() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    public void saveDraftPlatformFunctions() {
        getDriver().findElement(By.id("pa-entity-form-draft-btn")).click();
    }

    public void createNewRecord() {
        createPlatformFunctionsMenuItem();
        savePlatformFunctions();
    }

    public void createNewDraftRecord() {
        createPlatformFunctionsMenuItem();
        saveDraftPlatformFunctions();
    }

    public boolean[] deleteRecord() {
        boolean stateOfVisibleRow[] = new boolean[2];
        WebElement actionsButton = getDriver().findElement(By.xpath("//*[@id='pa-all-entities-table']//tr[1]//button"));
        stateOfVisibleRow[0] = actionsButton.isDisplayed();
        actionsButton.click();

        WebDriverWait myWait = new WebDriverWait(getDriver(), 5);
        myWait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//*[@id='pa-all-entities-table']/tbody/tr[1]//ul"))));
        WebElement deleteRowAction = getDriver().findElement(By.xpath("//*[@id='pa-all-entities-table']//tr[1]//li/a[text()='delete']"));
        myWait.until(ExpectedConditions.elementToBeClickable(deleteRowAction));
        deleteRowAction.click();

        getDriver().navigate().refresh();
        stateOfVisibleRow[1] = getDriver().equals(actionsButton);

        return stateOfVisibleRow;
    }

    @Test
    public void testCreateRecordInAdditionToExisting() {
        navigatePlatformFunctionsMenuItem();
        createNewRecord();
        createNewRecord();
        createNewDraftRecord();
        createNewDraftRecord();

        List<WebElement> lastIntArr = getDriver().findElements(By.xpath("//*[@id='pa-all-entities-table']/tbody/child::*/td[2]"));
        for (int i = 1; i < lastIntArr.size(); i++) {
            Assert.assertEquals(Integer.parseInt(lastIntArr.get(i).getText()), Integer.parseInt(lastIntArr.get(i - 1).getText()) + 1);
        }

        List<WebElement> lastStringArr = getDriver().findElements(By.xpath("//*[@id='pa-all-entities-table']/tbody/child::*/td[3]"));
        for (int i = 1; i < lastStringArr.size(); i++) {
            Assert.assertEquals(lastStringArr.get(i).getText(), lastStringArr.get(i - 1).getText() + " suffix");
        }

        List<WebElement> constantArr = getDriver().findElements(By.xpath("//*[@id='pa-all-entities-table']/tbody/child::*/td[4]"));
        for (int i = 0; i < constantArr.size(); i++) {
            Assert.assertEquals(constantArr.get(i).getText(), "contact@company.com");
        }
    }

    @Test
    public void testDeleteRecord() {
        navigatePlatformFunctionsMenuItem();
        createNewRecord();
        Assert.assertEquals(deleteRecord(), new boolean[]{true, false});
    }

    @Test
    public void testDeleteDraftRecord() {
        navigatePlatformFunctionsMenuItem();
        createNewDraftRecord();
        Assert.assertEquals(deleteRecord(), new boolean[]{true, false});
    }
}
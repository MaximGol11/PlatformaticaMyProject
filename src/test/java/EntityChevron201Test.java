import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityChevron201Test extends BaseTest {

    private void navigateToChevronPage()
    {
        scrollClick(getDriver(), By.xpath("//div[@id = 'menu-list-parent']//p[text() = ' Chevron ']"));
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
    }

    private void saveRecord(){
        scrollClick(getDriver(), By.xpath("//button[@id='pa-entity-form-save-btn']"));
    }

    private void createRecord(){
        saveRecord();
    }

    private void editRecord(String[] arguments){
        getDriver().findElement(By.xpath("//span[contains(@class,'bmd-form-group')]/textarea")).clear();
        getDriver().findElement(By.xpath("//span[contains(@class,'bmd-form-group')]/textarea")).sendKeys(arguments[1]);

        getDriver().findElement(By.id("int")).clear();
        getDriver().findElement(By.id("int")).sendKeys(arguments[2]);

        getDriver().findElement(By.xpath("//div[@id='_field_container-decimal']//span/input")).clear();
        getDriver().findElement(By.xpath("//div[@id='_field_container-decimal']//span/input")).sendKeys(arguments[3]);

        saveRecord();
    }

    @Test
    public void testEditRecordFillFields() throws InterruptedException{
        final String[] expectedResult = {"Pending","test record new","11", "2.06","","","","apptester1@tester.test"};

        navigateToChevronPage();
        createRecord();

        getDriver().findElement(By.xpath("//div[contains(@class,'dropdown')]/button")).click();
        TestUtils.jsClick(getDriver(),
                getWait().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//ul[contains(@class,'dropdown')]/li/a[text()='edit']"))));

        editRecord(expectedResult);

       List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td[contains(@class, 'pa-list-table-th')]"));

        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++){
            actualResult.add(i,elements.get(i).getText());
        }

        Assert.assertEquals(rows.size(),1);

        for (int i = 0; i < actualResult.size(); i++){
            Assert.assertEquals(actualResult.get(i),expectedResult[i]);
        }
    }
}

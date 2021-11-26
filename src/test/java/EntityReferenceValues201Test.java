import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityReferenceValues201Test extends BaseTest {

    private void navigateToReferenceValuesPage()
    {
        scrollClick(getDriver(), By.xpath("//div[@id = 'menu-list-parent']//p[text() = ' Reference values ']"));
     }

    private void fillTheField(WebElement element, String text, boolean clearBeforeSend) {
        if (clearBeforeSend) {
            element.clear();
        }
        element.sendKeys(text);
    }

    private void saveRecord(){
        scrollClick(getDriver(), By.xpath("//button[@id='pa-entity-form-save-btn']"));
    }

    private void createReferenceValuesRecord()
    {
        fillTheField(getDriver().findElement(By.id("label")),"label", true);
        fillTheField(getDriver().findElement(By.id("filter_1")),"filter 1",true);
        fillTheField(getDriver().findElement(By.id("filter_2")),"filter 2",true);

        saveRecord();
    }

    private void deleteReferenceValuesRecord() {
        getDriver().findElement(By.xpath("//div[contains(@class,'dropdown')]/button")).click();
        TestUtils.jsClick(getDriver(),
                getWait().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//ul[contains(@class,'dropdown')]/li/a[text()='delete']"))));
    }

    @Test
    public void testReferenceValuesRestoreAsDraft() {
        final String[] expectedResult = {"label","filter 1","filter 2"};

        navigateToReferenceValuesPage();
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();

        createReferenceValuesRecord();
        deleteReferenceValuesRecord();

        getDriver().findElement(By.xpath("//i[text()='delete_outline']")).click();

        getDriver().findElement(By.xpath("//a[text()='restore as draft']")).click();

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),0);

        navigateToReferenceValuesPage();

        rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),1);

        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td[contains(@class, 'pa-list-table-th')]"));

        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++){
            actualResult.add(i,elements.get(i).getText());
        }

        for (int i = 0; i < actualResult.size(); i++){
            Assert.assertEquals(actualResult.get(i),expectedResult[i]);
        }
    }
}

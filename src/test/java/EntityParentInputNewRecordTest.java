import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import static utils.TestUtils.*;
public class EntityParentInputNewRecordTest extends BaseTest {
    @Test
    public void testCreateNewRecord() throws InterruptedException {
        final String[] expectedValues = new String[]{"String", "Text", "1", "10.1", " ", " "," "};
        final String integer = "1";
        final String decimal = "10.1";
        final String date = " ";
        final String datetime = " ";
        scrollClick(getDriver(), By.xpath("//p[contains(text( ), 'Parent')]"));
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        getDriver().findElement(By.id("string")).sendKeys("String");
        getDriver().findElement(By.id("text")).sendKeys("Text");
        getDriver().findElement(By.id("int")).sendKeys(integer);
        getDriver().findElement(By.id("decimal")).sendKeys(decimal);
        getDriver().findElement(By.id("date")).sendKeys(date);
        getDriver().findElement(By.id("datetime")).sendKeys(datetime);
        getDriver().findElement(By.xpath("//div[@class='filter-option-inner-inner']"));
        getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();

        List<WebElement> rows = getDriver().findElements(By.xpath("//tr[@data-index='0']"));
        List<WebElement> elements= getDriver().findElements(By.xpath("//tbody/tr/td"));
        List<String> actualValues = new ArrayList<>();
        for(int i=0; i <elements.size();i++){
            actualValues.add(i,elements.get(i).getText());
        }
        Assert.assertEquals(rows.size(), 1);
        Thread.sleep(1000);
    }
}
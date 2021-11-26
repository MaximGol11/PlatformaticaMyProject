import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.*;

public class EntityEventsChain207Test extends BaseTest {

   @Test
    public void testCreateNewRecord() throws InterruptedException {
       final String f1Value = "1";
       final String [] expectedValues = new String[] { "1", "0", "0", "0", "0","0","0", "0", "0","0"};
       List<String >actualValues = new ArrayList<>();

       scrollClick(getDriver(),By.xpath("//p[text() = ' Events Chain 2 ']"));
       getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
       getDriver().findElement(By.id("f1")).sendKeys(f1Value);
       getWait().until(ExpectedConditions.elementToBeClickable(By.id("pa-entity-form-save-btn")));
       scrollClick(getDriver(),(By.id("pa-entity-form-save-btn")));

       List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
       List<WebElement>elements = getDriver().findElements(By.xpath("//tbody/tr/td/a"));

       for(int i = 0; i<elements.size(); i++){
          actualValues.add(i, elements.get(i).getText());
       }

       Assert.assertEquals(rows.size(), 1);
       Assert.assertEquals(elements.size(),expectedValues.length);

       for(int i = 0; i<expectedValues.length; i++){
           Assert.assertEquals(actualValues.get(i), expectedValues[i]);
       }
    }
}

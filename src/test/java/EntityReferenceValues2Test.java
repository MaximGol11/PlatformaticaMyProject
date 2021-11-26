import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityReferenceValues2Test extends BaseTest {

    private void inputRecord() {
        inputNewRecord("John Doe", "French", "France");
        inputNewRecord("Anna Belle", "Italian", "Italy");
        inputNewRecord("Juan Carlos", "Spanish", "Spain");
    }

    private void inputNewRecord(String name, String filter1, String filter2) {
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        getDriver().findElement(By.xpath("//input[@id='label']")).sendKeys(name);
        getDriver().findElement(By.xpath("//input[@id='filter_1']")).sendKeys(filter1);
        getDriver().findElement(By.xpath("//input[@id='filter_2']")).sendKeys(filter2);
        getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();
    }

    @Test
    public void testReferenceValuesInput() {
        final String[] expectedResult = new String[]{"John Doe", "French", "France", "Anna Belle", "Italian", "Italy", "Juan Carlos", "Spanish", "Spain"};
        List<String> actualValues = new ArrayList<>();

        getDriver().findElement(By.xpath("//div[@class='logo d-flex']"));
        getDriver().findElement(By.xpath("//i[contains(text(),'list')]")).click();

        getDriver().findElement(By.xpath("//div[@id = 'menu-list-parent']//p[contains(text(), 'Reference values' )]")).click();
        inputRecord();

        List<WebElement> row = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        for (int i = 0; i < elements.size(); i++) {
            actualValues.add(i, elements.get(i).getText());
        }
        Assert.assertEquals(actualValues.size(), expectedResult.length);
        Assert.assertEquals(row.size(), 3);
        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(actualValues.get(i), expectedResult[i]);
        }
    }
}





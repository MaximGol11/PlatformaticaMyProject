import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;

public class EntityDefaultTestAnnaG extends BaseTest {

    @Test
    public void testExpectedStringValue() throws InterruptedException {

        String[] expectedResult = new String[]{"One", "Two", "100", "345.56"};


        TestUtils.scrollClick(getDriver(),getDriver().findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=7&mod=2']")));
        getDriver().findElement(By.className("card-icon")).click();



        WebElement stringTextField = getDriver().findElement(By.xpath("//input[@id = 'string']"));
        WebElement textTextField = getDriver(). findElement(By.xpath("//textarea[@id='text']"));
        WebElement intTextField = getDriver().findElement(By.xpath("//input[@id= 'int']"));
        WebElement decimalTextField = getDriver().findElement(By.xpath("//input[@id= 'decimal']"));
        WebElement dateField = getDriver().findElement(By.xpath("//input[@id= 'date']"));
        WebElement dateTimeField = getDriver().findElement(By.xpath("//input[@id= 'datetime']"));

        stringTextField.clear();
        textTextField.clear();
        intTextField.clear();
        decimalTextField.clear();
        dateField.clear();
        dateTimeField.clear();

        stringTextField.sendKeys("One");
        textTextField.sendKeys("Two");
        intTextField.sendKeys("100");
        decimalTextField.sendKeys("345.56" );
        dateField.click();
        dateTimeField.click();


        WebElement plusButton = getDriver().findElement(By.xpath("//button[@data-table_id=\"11\"]"));


        TestUtils.jsClick(getDriver(), plusButton);



        WebElement stringEmbedD = getDriver().findElement(By.xpath("//textarea[@id= \"t-11-r-1-string\"]"));
        WebElement textEmbedD = getDriver().findElement(By.xpath("//textarea[@id= \"t-11-r-1-text\"]"));
        WebElement intEmbedD = getDriver().findElement(By.xpath("//textarea[@id= \"t-11-r-1-int\"]"));
        WebElement decimalEmbedD =getDriver().findElement(By.xpath("//textarea[@id= \"t-11-r-1-decimal\"]"));

        stringEmbedD.clear();
        textEmbedD.clear();
        intEmbedD.clear();
        decimalEmbedD.clear();



        stringEmbedD.sendKeys("One");
        textEmbedD.sendKeys("Two");
        intEmbedD.sendKeys("100");
        decimalEmbedD.sendKeys("345.56");



        getDriver().findElement(By.xpath("//input[@id='t-11-r-1-date']")).click();
        getDriver().findElement(By.xpath("//input[@id='t-11-r-1-datetime']")).click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));


        TestUtils.jsClick(getDriver(),saveButton);


        List<WebElement> actualResult = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        for (int i = 0; i < expectedResult.length; i++){
            Assert.assertEquals(actualResult.get(i).getText(),expectedResult[i]);
        }


    }
}
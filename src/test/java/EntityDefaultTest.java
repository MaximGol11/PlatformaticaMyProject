import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;

public class EntityDefaultTest extends BaseTest {

    private void setClickSendKeys(WebElement element, String str){
        element.clear();
        element.sendKeys(str);
    }

    @Test
    public void testNewRecord(){
        final String[] expectedArray = new String[]{
                "new String",
                "new Text",
                "25688",
                "100.55"};
        final String dataString= "new String";
        final String dataText= "new Text";
        final String dataInt= "25688";
        final String dataDecimal= "100.55";

        getDriver().findElement(By.xpath("//div[@class='logo d-flex']"));
        getDriver().findElement(By.xpath("//i[contains(text(),'list')]")).click();
        getDriver().findElement(By.xpath("//p[contains(text(),' Default ')]")).click();
        getDriver().findElement(By.xpath("//div[@class='card-icon']//i[@class='material-icons']")).click();

        WebElement stringTextField = getDriver().findElement(By.xpath("//div[@id='_field_container-string']//input[@id='string']"));
        WebElement textTextField = getDriver().findElement(By.xpath("//textarea[@id='text']"));
        WebElement intTextField = getDriver().findElement(By.xpath("//input[@id='int']"));
        WebElement decimalTextField = getDriver().findElement(By.xpath("//input[@id='decimal']"));
        WebElement dateField = getDriver().findElement(By.xpath("//input[@id='date']"));
        WebElement datetimeTextField = getDriver().findElement(By.xpath("//input[@id='datetime']"));

        setClickSendKeys(stringTextField,dataString);
        setClickSendKeys(textTextField,dataText);
        setClickSendKeys(intTextField,dataInt);
        setClickSendKeys(decimalTextField,dataDecimal);

        dateField.click();
        datetimeTextField.click();

        WebElement plusButton = getDriver().findElement(By.xpath("//tr[@id='add-row-11']//button"));

        TestUtils.jsClick(getDriver(),plusButton);

        WebElement stringEmbedD = getDriver().findElement(By.xpath("//textarea[@id='t-11-r-1-string']"));
        WebElement testEmbedD = getDriver().findElement(By.xpath("//textarea[@id='t-11-r-1-text']"));
        WebElement intEmbedD = getDriver().findElement(By.xpath("//textarea[@id='t-11-r-1-int']"));
        WebElement decimalEmbedD = getDriver().findElement(By.xpath("//textarea[@id='t-11-r-1-decimal']"));

        setClickSendKeys(stringEmbedD,dataString);
        setClickSendKeys(testEmbedD,dataText);
        setClickSendKeys(intEmbedD,dataInt);
        setClickSendKeys(decimalEmbedD,dataDecimal);

        getDriver().findElement(By.xpath("//input[@id='t-11-r-1-date']")).click();
        getDriver().findElement(By.xpath("//input[@id='t-11-r-1-datetime']")).click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));

        TestUtils.jsClick(getDriver(),saveButton);

        List<WebElement> result = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        for (int i = 0; i < expectedArray.length; i++) {
            Assert.assertEquals(result.get(i).getText(), expectedArray[i]);
        }
    }
}

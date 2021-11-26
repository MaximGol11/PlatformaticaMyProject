import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EntityDefault2Test extends BaseTest {

    @Test
    public void testDefaultData(){

        getDriver().findElement(By.xpath("//p[contains(text(),'Default')]")).click();
        getDriver().findElement(By.xpath("//i[contains(text(), 'create_new_folder')]")).click();

        String defaultString  = getDriver().findElement(By.xpath("//input[@data-field_name='string']")).getAttribute("value");
        String defaultText  = getDriver().findElement(By.xpath("//textarea[@name='entity_form_data[text]']")).getText();
        String defaultInt  = getDriver().findElement(By.xpath("//input[@id='int']")).getAttribute("value");
        String defaultDecimal  = getDriver().findElement(By.xpath("//input[@id='decimal']")).getAttribute("value");
        String defaultDate  = getDriver().findElement(By.xpath("//input[@id='date']")).getAttribute("value");
        String defaultDatetime  = getDriver().findElement(By.xpath("//input[@id='datetime']")).getAttribute("value");

        Assert.assertEquals(defaultString,"DEFAULT STRING VALUE");
        Assert.assertEquals(defaultText,"DEFAULT TEXT VALUE");
        Assert.assertEquals(defaultInt,"55");
        Assert.assertEquals(defaultDecimal,"110.32");
        Assert.assertEquals(defaultDate,"01/01/1970");
        Assert.assertEquals(defaultDatetime,"01/01/1970 00:00:00");
    }
}

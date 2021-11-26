import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;

public class EntityChevron1Test extends BaseTest {
    @Ignore
    @Test
    public void testDeleteRecord() throws InterruptedException {
        final String intValue = "5";
        final String decimalValue = "10.00";
        final String dateValue = "16/11/2021";
        final String dateTimeValue = "16/11/2021 01:03:32";

        TestUtils.scrollClick(getDriver(), By.xpath("//div[@id = 'menu-list-parent']//p[text() = ' Chevron ']"));
        getDriver().findElement(By.xpath("//div[@class = 'card-icon']//i[@class = 'material-icons']")).click();
        getDriver().findElement(By.id("text")).sendKeys("Azur");
        getDriver().findElement(By.id("int")).sendKeys(intValue);
        getDriver().findElement(By.id("decimal")).sendKeys(decimalValue);
        getDriver().findElement(By.id("date")).sendKeys(dateValue);
        getDriver().findElement(By.id("datetime")).sendKeys(dateTimeValue);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getDriver().findElement(By.xpath("//button [@type = 'button']//i[@class = 'material-icons']")).click();
        getDriver().findElement(By.xpath("//ul[@class = 'dropdown-menu dropdown-menu-right show']//a[text() = 'delete']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class = 'content']")).isDisplayed());

        Thread.sleep(2000);
    }
}

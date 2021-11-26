import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

public class EntityChevronTest2 extends BaseTest {

    @Test
    public void testChevronEditRecord() {
        TestUtils.scrollClick(getDriver(), getDriver().
                findElement(By.xpath("//p[text()=' Chevron ']")));

        By createButtonLocator = By.xpath("//i[text() = 'create_new_folder']");
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(createButtonLocator))).click();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(createButtonLocator)));

        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']")));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']"))));
        getDriver().findElement(By.xpath("//a[text()='edit']")).click();

        TestUtils.scrollClick(getDriver(),getDriver().findElement(By.xpath("//div[text()='Pending']")));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//div[@class='dropdown-menu show']"))));
        getDriver().findElement(By.xpath("//span[text()='Fulfillment']")).click();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(createButtonLocator)));

        String value = getDriver().findElement(By.xpath("//table/tbody/tr/td[2]/a")).getText();
        Assert.assertEquals(value, "Fulfillment");

        getDriver().findElement(By.xpath("//a[text()='Pending']")).click();

        String noMatchingRecordsMessage = getDriver().findElement(By.xpath("//table/tbody/tr/td")).getText();
        Assert.assertEquals(noMatchingRecordsMessage, "No matching records found");

        getDriver().findElement(By.xpath("//a[text()='Sent']")).click();

        String noMatchingRecordsMessageSent = getDriver().findElement(By.xpath("//table/tbody/tr/td")).getText();
        Assert.assertEquals(noMatchingRecordsMessageSent, "No matching records found");
    }
}

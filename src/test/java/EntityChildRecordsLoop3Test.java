import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.concurrent.ThreadLocalRandom;

public class EntityChildRecordsLoop3Test extends BaseTest {

    private static final int RANDOM_NUMBER = ThreadLocalRandom.current().nextInt(2000, 10000);
    String RANDOM_INT_TO_STRING = Integer.toString(RANDOM_NUMBER);

    private static final int RANDOM_NUMBER2 = ThreadLocalRandom.current().nextInt(2000, 10000);
    String RANDOM_INT_TO_STRING2 = Integer.toString(RANDOM_NUMBER2);

    @Ignore
    @Test
    public void testEditRecord() {

        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[contains(text(),' Child records loop ')]/..")));
        getDriver().findElement(By.xpath("//div[@class='card-icon']")).click();

        getDriver().findElement(By.id("start_balance")).sendKeys("69");
        getWait().until(ExpectedConditions.textToBePresentInElementValue(getDriver().findElement(By.id("end_balance")),"69"));
        getDriver().findElement(By.id("end_balance")).sendKeys(Keys.CONTROL, "a");
        getDriver().findElement(By.id("end_balance")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("end_balance")).sendKeys("690");
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        getDriver().findElement(By.xpath("//*[@class=\"dropdown pull-left\"]")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'edit')]")));
        getDriver().findElement(By.xpath("//a[contains(text(),'edit')]")).click();

        getDriver().findElement(By.id("start_balance")).sendKeys(Keys.CONTROL, "a");
        getDriver().findElement(By.id("start_balance")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("start_balance")).sendKeys(RANDOM_INT_TO_STRING);

        getWait().until(ExpectedConditions.textToBePresentInElementValue(getDriver().findElement(By.id("end_balance")), RANDOM_INT_TO_STRING));

        getDriver().findElement(By.id("end_balance")).sendKeys(Keys.CONTROL, "a");
        getDriver().findElement(By.id("end_balance")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("end_balance")).sendKeys(RANDOM_INT_TO_STRING2);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        getDriver().findElement(By.xpath("//*[@class=\"dropdown pull-left\"]")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'view')]")));
        getDriver().findElement(By.xpath("//a[contains(text(),'view')]")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div/div[2]/div[1]/div/div/div[2]/div/span"))
                .getText(), RANDOM_INT_TO_STRING + ".00");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div/div[2]/div[1]/div/div/div[4]/div/span"))
                .getText(), RANDOM_INT_TO_STRING2 + ".00");

    }
}
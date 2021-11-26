import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

public class EntityImportTest extends BaseTest {

    private static final String STRING = "Some string";
    private static final String TEXT = "Some text";
    private static final String INT = "34";
    private static final String DECIMAL = "3.40";

    @Test
    public void inputImportEntity() {

        Actions getEntity = new Actions(getDriver());
        getEntity.moveToElement(getDriver().findElement(By.xpath("//a[@class='simple-text logo-mini']"))).perform();
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[normalize-space()='Import']")));
        getDriver().findElement(By.xpath("//i[normalize-space()='create_new_folder']")).click();

       getDriver().findElement(By.xpath("//input[@id='string']")).sendKeys(STRING);
       getDriver().findElement(By.xpath("//textarea[@id='text']")).sendKeys(TEXT);
       getDriver().findElement(By.xpath("//input[@id='int']")).sendKeys(INT);
       getDriver().findElement(By.xpath("//input[@id='decimal']")).sendKeys(DECIMAL);
        jsClick(getDriver(),getDriver().findElement (By.id("pa-entity-form-save-btn")));

       String stringId = getDriver().findElement(By.xpath("//a[normalize-space()='Some string']")).getText();
       String textId = getDriver().findElement(By.xpath("//a[normalize-space()='Some text']")).getText();
       String intId  = getDriver().findElement(By.xpath("//a[normalize-space()='34']")).getText();
       String decimalId = getDriver().findElement(By.xpath("//a[normalize-space()='3.40']")).getText();

        Assert.assertEquals(stringId, STRING);
        Assert.assertEquals(textId, TEXT);
        Assert.assertEquals(intId, INT);
        Assert.assertEquals(decimalId,DECIMAL);
    }
}



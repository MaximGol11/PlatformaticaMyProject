import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class EntityReferenceValues1Test extends BaseTest {
    private static final String [] expectedResult = new String[]{
            "label",
            "filter1",
            "filter2"};

    public void createNewCardAndFilling(String[] inputs) {
        getDriver().findElement(By.className("card-icon")).click();
        List<WebElement> sendElement = getDriver().findElements(By.xpath("//input[@type='text']"));
        for (int i = 0; i < inputs.length; i++){
            sendElement.get(i).sendKeys(inputs[i]);
        }
    }

    @Test
    public void testNewInput () {
        getDriver().findElement(By.xpath("//div[@id = 'menu-list-parent']//p[contains(text(), 'Reference values' )]")).click();
        createNewCardAndFilling(expectedResult);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        List<WebElement> resultWithSaveBtn = getDriver().findElements(By.xpath("//tr[@data-index='0']/td[@class]"));
        Assert.assertEquals(resultWithSaveBtn.size(),expectedResult.length);
        for (int i = 0; i < expectedResult.length; i++) {
            Assert.assertEquals(resultWithSaveBtn.get(i).getText(),expectedResult[i]);
        }
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-check-square-o']")).isDisplayed());

        createNewCardAndFilling(expectedResult);
        getDriver().findElement(By.id("pa-entity-form-draft-btn")).click();

        List<WebElement> resultWithSaveDraft = getDriver().findElements(By.xpath("//tr[@data-index='1']/td[@class]"));
        Assert.assertEquals(resultWithSaveDraft.size(),expectedResult.length);
        for (int i = 0; i < expectedResult.length; i++) {
            Assert.assertEquals(resultWithSaveDraft.get(i).getText(),expectedResult[i]);
        }
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-pencil']")).isDisplayed());

        createNewCardAndFilling(expectedResult);
        getDriver().findElement(By.xpath("//button[@class='btn btn-dark']")).click();
        String actualWithCancelBtn = getDriver().findElement(By.xpath("//span[contains(text(),'Showing 1 to 2 of 2 rows')]")).getText();

        Assert.assertEquals(actualWithCancelBtn, "Showing 1 to 2 of 2 rows");
    }
}


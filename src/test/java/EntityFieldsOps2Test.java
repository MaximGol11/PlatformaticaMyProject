import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityFieldsOps2Test extends BaseTest {

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

    private void menuList() {
        getDriver().findElement(By.xpath("//div[@class='logo d-flex']"));
        getDriver().findElement(By.xpath("//i[contains(text(),'list')]")).click();
    }

    private void redFolder() {
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
    }

    private void saveButton() {
        getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();
    }

    private void sleepTime() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFieldsOpsInput() {
        final String[] expectedResult = new String[]{"", "Pending", "John Doe", "John Doe, Anna Belle, Juan Carlos", "Juan Carlos", "contact@company.com"};
        List<String> actualValues = new ArrayList<>();
        Actions act = new Actions(getDriver());

        menuList();
        getDriver().findElement(By.xpath("//div[@id = 'menu-list-parent']//p[contains(text(), 'Reference values' )]")).click();
        inputRecord();

        menuList();
        getDriver().findElement(By.xpath("//div[@id = 'menu-list-parent']//p[contains(text(), ' Fields Ops ' )]")).click();
        redFolder();

        getDriver().findElement(By.className("toggle")).click(); //On

        getDriver().findElement(By.xpath("//button[@data-id ='dropdown']")).click();
        sleepTime();
        getDriver().findElement(By.xpath("//a[@role = 'option']/span[text()='Pending']")).click();

        getDriver().findElement(By.xpath("//button[@data-id ='reference']")).click();
        sleepTime();
        getDriver().findElement(By.xpath("//a[@role = 'option']/span[text()='John Doe']")).click();

        List<WebElement> checkboxes = getDriver().findElements(By.xpath("//span[@class='check']"));
        checkboxes.forEach(c -> c.click());

        getDriver().findElement(By.xpath("//div[@id='_field_container-reference_with_filter']//div[contains(text(),'...')]")).click();
        sleepTime();
        act.moveToElement(getDriver().findElement(By.xpath("//div[@class='dropdown-menu show']//span[contains(text(),'Juan Carlos')]"))).perform();
        act.click(getDriver().findElement(By.xpath("//div[@class='dropdown-menu show']//span[contains(text(),'Juan Carlos')]"))).perform();

        scrollClick(getDriver(), By.xpath("//td[@class='pa-add-row-btn-col']"));
        saveButton();

        List<WebElement> actualValuesMainPage = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(actualValuesMainPage.size(), expectedResult.length);
        for (int i = 0; i < actualValuesMainPage.size(); i++) {
            Assert.assertEquals(actualValuesMainPage.get(i).getText(), expectedResult[i]);
        }
    }
}






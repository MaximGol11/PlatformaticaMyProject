import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

public class EntityParentTest extends BaseTest {

    public void goToParentPage() {
        TestUtils.scrollClick(getDriver(), By.xpath("//p[text()=' Parent ']"));
    }

    public void clickCreateNewEntity() {
        getDriver().findElement(By.xpath("//*[contains(text(), 'create_new')]")).click();
    }

    public void clickSave() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    public void enterValues(String[] arr) {
        getDriver().findElement(By.id("string")).sendKeys(arr[0]);
        getDriver().findElement(By.id("text")).sendKeys(arr[1]);
        getDriver().findElement(By.id("int")).sendKeys(arr[2]);
        getDriver().findElement(By.id("decimal")).sendKeys(arr[3]);
        getDriver().findElement(By.id("date")).sendKeys(Keys.DELETE);
        getDriver().findElement(By.id("date")).sendKeys(arr[4]);
    }

    public void createEntity(String[] arr) {
        clickCreateNewEntity();
        enterValues(arr);
        clickSave();
    }

    public void assertValues (int n, String[] arr) {
        String stringString1 = getDriver().findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + n + "]/td[2]/a[1]")).getText();
        Assert.assertEquals(stringString1, arr[0]);
        String stringText1 = getDriver().findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + n + "]/td[3]/a[1]")).getText();
        Assert.assertEquals(stringText1, arr[1]);
        String stringInt1 = getDriver().findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + n + "]/td[4]/a[1]")).getText();
        Assert.assertEquals(stringInt1, arr[2]);
        String stringDec1 = getDriver().findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + n + "]/td[5]/a[1]")).getText();
        Assert.assertEquals(stringDec1, arr[3]);
        String stringData = getDriver().findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + n + "]/td[6]/a[1]")).getText();
        Assert.assertEquals(stringData, arr[4]);
    }

    @Test
    public void testParentView() {

        final String string_ = "New String";
        final String text_ = "New Text";
        final String int_ = "10";
        final String decimal_ = "20.30";
        final String decimal2_ = "21.30";
        final String date_ = "20/11/2021";
        final String date2_ = "21/11/2021";

        final String[] entityData1 = {string_, text_, int_, decimal_, date_};

        final int n = 1;
        final String[] entityData2 = {entityData1[0] + n, entityData1[1] + n, entityData1[2] + n, decimal2_, date2_};

        goToParentPage();

        createEntity(entityData1);
        createEntity(entityData2);

        assertValues(1, entityData1);
        assertValues(2, entityData2);
    }
}
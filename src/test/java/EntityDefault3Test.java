import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

public class EntityDefault3Test extends BaseTest {

    private static final String[] EXPECTED_DEFAULT_VALUES = {
            "DEFAULT STRING VALUE",
            "DEFAULT TEXT VALUE",
            "55",
            "110.32",
            "01/01/1970",
            "01/01/1970 00:00:00"};

    private static final By[] FIELDS_LOCATORS_DEFAULT_FORM = {
            By.id("string"),
            By.id("text"),
            By.id("int"),
            By.id("decimal"),
            By.id("date"),
            By.id("datetime")
    };

    private void  openDefaultForm() {
        TestUtils.scrollClick(getDriver(), By.xpath("//a[@href='index.php?action=action_list&entity_id=7&mod=2']"));
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
    }

    @Test
    public void testVerifyDefaultValuesInDefaultForm() {
        openDefaultForm();

        for(int i = 0; i < FIELDS_LOCATORS_DEFAULT_FORM.length; i++) {
            Assert.assertEquals(getDriver()
                            .findElement(FIELDS_LOCATORS_DEFAULT_FORM[i]).getAttribute("value")
                    , EXPECTED_DEFAULT_VALUES[i]);
        }
    }
}
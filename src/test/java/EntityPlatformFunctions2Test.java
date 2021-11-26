import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.EntityUtils;
import utils.TestUtils;

public class EntityPlatformFunctions2Test extends BaseTest {
    private static final String DEFAULT_LAST_INT = "1";
    private static final String DEFAULT_LAST_STRING = "null suffix";
    private static final String DEFAULT_CONSTANT = "contact@company.com";

    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");

    private void saveRecord(By button) {
        getDriver().findElement(button).click();
    }

    private void fillTheField(WebElement element, String text, boolean clearBeforeSend) {
        if (clearBeforeSend) {
            element.clear();
        }
        element.sendKeys(text);
    }

    private void fillTheFields(String lastInt, String lastString, String constant, By button, boolean cleanField) {
        fillTheField(getDriver().findElement(By.id("last_int")), lastInt, cleanField);
        fillTheField(getDriver().findElement(By.id("last_string")), lastString, cleanField);
        fillTheField(getDriver().findElement(By.id("constant")), constant, cleanField);
        saveRecord(button);
    }

    private void assertRecord(String lastInt, String lastString, String constant, int row) {
        String stRow = String.valueOf(row);
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@data-index = '" + stRow + "']/td[2]/a")).getText(), lastInt);
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@data-index = '" + stRow + "']/td[3]/a")).getText(), lastString);
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@data-index = '" + stRow + "']/td[4]/a")).getText(), constant);
    }

    private void assertIntField(String lastInt, String newInt) {
        Assert.assertEquals(Integer.parseInt(newInt), Integer.parseInt(lastInt) + 1);
    }

    private void assertStringField(String lastString, String newString) {
        Assert.assertEquals(newString, lastString + " suffix");
    }

    private String getValue(int row, String columnName) {
        final String stRow = String.valueOf(row);
        switch (columnName) {
            case "Last int":
                try {
                    return getDriver().findElement(By.xpath("//tr[@data-index = '" + stRow + "']/td[2]/a")).getText();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            case "Last string":
                try {
                    return getDriver().findElement(By.xpath("//tr[@data-index = '" + stRow + "']/td[3]/a")).getText();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            default:
                return "error: invalid column name";
        }
    }

    @BeforeMethod
    private void openEntity() {
        EntityUtils.openTestClassByName(getDriver(), "Platform functions");
    }

    @Test
    public void testCreateFirstRecord() {
        EntityUtils.clickAddCard(getDriver());
        saveRecord(SAVE_BUTTON);
        assertRecord(DEFAULT_LAST_INT, DEFAULT_LAST_STRING, DEFAULT_CONSTANT, 0);
    }

    @Test
    public void testCreateFirstRecordDraft() {
        EntityUtils.clickAddCard(getDriver());
        saveRecord(By.id("pa-entity-form-draft-btn"));
        assertRecord(DEFAULT_LAST_INT, DEFAULT_LAST_STRING, DEFAULT_CONSTANT, 0);

        WebElement fieldIcon = getDriver().findElement(By.xpath("//tr[@data-index = '0']/td[1]/i"));
        Assert.assertEquals(fieldIcon.getAttribute("class"), "fa fa-pencil");
    }

    @Test
    public void testCreateRecordWithUserValues() {
        final String CUSTOM_LAST_INT = "10";
        final String CUSTOM_LAST_STRING = "some string";
        final String CUSTOM_CONSTANT = "some constant";

        EntityUtils.clickAddCard(getDriver());
        fillTheFields(CUSTOM_LAST_INT, CUSTOM_LAST_STRING, CUSTOM_CONSTANT, SAVE_BUTTON, true);
        assertRecord(CUSTOM_LAST_INT, CUSTOM_LAST_STRING, CUSTOM_CONSTANT, 0);
    }

    @Test
    public void testCreateAdditionalRecord() {
        String lastInt = null;
        String lastString = null;

        for (int i = 0; i < 4; i++) {
            EntityUtils.clickAddCard(getDriver());
            saveRecord(SAVE_BUTTON);
            if (i == 0) {
                lastInt = getValue(i, "Last int");
                lastString = getValue(i, "Last string");
            } else {
                String newInt = getValue(i, "Last int");
                String newString = getValue(i, "Last string");

                assertIntField(lastInt, newInt);
                assertStringField(lastString, newString);

                lastInt = newInt;
                lastString = newString;
            }
        }
    }
}
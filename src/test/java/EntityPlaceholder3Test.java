import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.List;

public class EntityPlaceholder3Test extends BaseTest {

    private final String STRING = "String value";
    private final String TEXT = "Text value";
    private final String INT = "42";
    private final String DECIMAL = "0.42";
    private final String DATE = "01/08/1982";
    private final String DATE_TIME = "01/08/1982 00:42:00";
    private final String USER = "apptester1@tester.test";

    private final String[] EXPECTED_VALUES_VIEW_FORM = {
            "String", STRING, "Text", TEXT, "Int", INT, "Decimal", DECIMAL, "Date", DATE, "Datetime", DATE_TIME, "User\n" + USER, ""};

    private final String[] EXPECTED_VALUES_EMBED_VIEW_FORM = {
            "1", STRING, TEXT, INT, DECIMAL, DATE, DATE_TIME, "", "", ""};

    private void openPlaceholderPage() {
        Actions mainMenuNavigate = new Actions(getDriver());
        mainMenuNavigate.moveToElement(getDriver().findElement(By.className("nav-link"))).perform();
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Placeholder ']")));
    }

    private void createRecord() {
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.id("string")).sendKeys(STRING);
        getDriver().findElement(By.id("text")).sendKeys(TEXT);
        getDriver().findElement(By.id("int")).sendKeys(INT);
        getDriver().findElement(By.id("decimal")).sendKeys(DECIMAL);

        WebElement date = getDriver().findElement(By.id("date"));
        date.click();
        date.clear();
        date.sendKeys(DATE);

        WebElement dateTime = getDriver().findElement(By.id("datetime"));
        dateTime.click();
        dateTime.clear();
        dateTime.sendKeys(DATE_TIME);
    }

    private void createEmbedRecord() {
        TestUtils.scrollClick(getDriver(), By.xpath("//button[contains(text(), '+')]"));
        getDriver().findElement(By.id("t-12-r-1-string")).sendKeys(STRING);
        getDriver().findElement(By.id("t-12-r-1-text")).sendKeys(TEXT);
        getDriver().findElement(By.id("t-12-r-1-int")).sendKeys(INT);
        getDriver().findElement(By.id("t-12-r-1-decimal")).sendKeys(DECIMAL);
        getDriver().findElement(By.id("t-12-r-1-date")).sendKeys(DATE);
        getDriver().findElement(By.id("t-12-r-1-datetime")).sendKeys(DATE_TIME);

        WebElement date = getDriver().findElement(By.id("t-12-r-1-date"));
        date.click();
        date.clear();
        date.sendKeys(DATE);

        WebElement dateTime = getDriver().findElement(By.id("t-12-r-1-datetime"));
        dateTime.click();
        dateTime.clear();
        dateTime.sendKeys(DATE_TIME);
    }

    private void addRecord() {
        openPlaceholderPage();
        createRecord();
        createEmbedRecord();
        TestUtils.scrollClick(getDriver(), By.id("pa-entity-form-save-btn"));
    }

    private void assertFormValues(String[] expectedValues, String tableOrViewOrEmbed) {
        if (expectedValues != null) {
            if (tableOrViewOrEmbed.toLowerCase() == "table") {
                List<WebElement> actualList = getDriver().findElements(By.xpath("//tr[@data-index = '0']/td"));

                Assert.assertEquals(actualList.size(), expectedValues.length);
                for (int i = 0; i < actualList.size(); i++) {
                    Assert.assertEquals(actualList.get(i).getText(), expectedValues[i]);
                }
            }
            if (tableOrViewOrEmbed.toLowerCase() == "view") {
                List<WebElement> actualList = getDriver().findElements(By.xpath("//div[@style = 'text-align: left;'][1]/div"));

                Assert.assertEquals(actualList.size(), expectedValues.length);
                for (int i = 0; i < actualList.size() - 1; i++) {
                    Assert.assertEquals(actualList.get(i).getText(), expectedValues[i]);
                }
            }
            if (tableOrViewOrEmbed.toLowerCase() == "embed") {
                List<WebElement> actualList = getDriver().findElements(By.xpath("//tbody/tr[(td>1)and(td<11)]/td"));

                Assert.assertEquals(actualList.size(), expectedValues.length);
                for (int i = 0; i < actualList.size(); i++) {
                    Assert.assertEquals(actualList.get(i).getText(), expectedValues[i]);
                }
            }
        }
    }

    @Test
    public void testRecordViewPage() {
        addRecord();

        getDriver().findElement(By.xpath("//tr/td[2]")).click();

        assertFormValues(EXPECTED_VALUES_VIEW_FORM, "view");
        assertFormValues(EXPECTED_VALUES_EMBED_VIEW_FORM, "embed");
    }
}

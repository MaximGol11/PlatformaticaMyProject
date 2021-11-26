import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.EntityUtils;
import java.util.List;
import java.util.Objects;
import static org.openqa.selenium.Keys.DELETE;
import static org.openqa.selenium.Keys.TAB;
import static utils.TestUtils.jsClick;

public class EntityCalendarTest extends BaseTest {

    private static final String STRING = "Stringgg";
    private static final String TEXT = "Texttt";
    private static final String INT = "222";
    private static final String DECIMAL = "2.20";
    private static final String DATA = "22/12/2021";
    private static final String DATA_TIME = "22/12/2021 02:22:22";
    private static final String USER = "tester42@tester.test";
    private static final String FILE_NAME = "doneTXT.txt";

    private void clickOnCalendar(){
        Actions act = new Actions(getDriver());
        act.moveToElement(getDriver().findElement(By.xpath("//p[text()=' Calendar ']//ancestor::a"))).click().build().perform();
    }

    private void createRecord(String string, String text, String number, String decimal, String data, String dataTime, String fileName ){
        clickOnCalendar();
        EntityUtils.createNew(getDriver());
        getDriver().findElement(By.xpath("//input[@id='string']")).sendKeys(string + TAB +
                text + TAB +
                number + TAB +
                decimal + TAB +
                DELETE + data + TAB +
                DELETE + dataTime);
        WebElement selectFile = getDriver().findElement(By.id("file"));
        selectFile.sendKeys(Objects.requireNonNull(getClass().getResource(fileName)).getPath());
        new Select(getDriver().findElement(By.id("user"))).selectByVisibleText(USER);
        jsClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
    }

    @Test
    public void testCreateRecord() {
        createRecord(STRING, TEXT, INT,DECIMAL, DATA, DATA_TIME, FILE_NAME);
        String [] expectedResult  = {STRING, TEXT, INT, DECIMAL, DATA, DATA_TIME, FILE_NAME, USER};

        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=32']"))).click();
        List <WebElement> actualResult = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td[@class='pa-list-table-th']")));
        Assert.assertEquals(expectedResult.length, actualResult.size());
        for(int i = 0; i<expectedResult.length; i++){
            Assert.assertEquals(expectedResult[i], actualResult.get(i).getText());
        }
    }
}
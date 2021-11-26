package Model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class ReferenceValuesPage extends MainPage {

    @FindBy (xpath = "//i[normalize-space(text())='create_new_folder']")
    private WebElement buttonNewRecord;

    @FindBy (xpath = "//td[@class='pa-list-table-th']" )
    private List<WebElement> tableRows;

    public ReferenceValuesPage(WebDriver driver) {
        super(driver);
    }

    public ReferenceValuesFields gotoNewRecord () {
        buttonNewRecord.click();
        return new ReferenceValuesFields(getDriver());
    }

    public void assertCheckingTableRows (String[] expectedResult) {
        for (int i = 0; i < tableRows.size(); i++) {
            Assert.assertEquals(tableRows.get(i).getText(), expectedResult[i]);
        }
    }
}

package Model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUtils;


public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @FindBy(xpath = "//p[text()=' Reference values ']")
    private WebElement referenceValues;

    public MainPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait () {
        if (wait == null) {
            wait = new WebDriverWait(driver, 10);
        }
        return wait;
    }
    public ReferenceValuesPage gotoReferenceValues () {
        TestUtils.scrollClick(getDriver(), referenceValues);
        return new ReferenceValuesPage(getDriver());
    }

}

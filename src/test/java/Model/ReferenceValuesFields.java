package Model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReferenceValuesFields extends MainPage {

    @FindBy(id = "label")
    private WebElement label;

    @FindBy(id = "filter_1")
    private WebElement filter1;

    @FindBy(id = "filter_2")
    private WebElement filter2;

    @FindBy(xpath = "//button[normalize-space(text())='Save']")
    private WebElement saveButton;

    public ReferenceValuesFields(WebDriver driver) {
        super(driver);
    }

    public void fillLabel(String value) {
        label.sendKeys(value);
    }

    public ReferenceValuesFields fillFilter1(String value) {
        filter1.sendKeys(value);
        return this;
    }
    public void fillFilter2(String value) {
        filter2.sendKeys(value);
    }
    public ReferenceValuesPage clickSaveButton() {
        saveButton.click();
        return new ReferenceValuesPage(getDriver());
    }
}

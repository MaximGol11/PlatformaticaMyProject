import base.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import utils.EntityUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EntityFieldsOps3Test extends BaseTest {

    private final String[] DATA1 = {"John Doe", "French", "France"};
    private final String[] DATA2 = {"Anna Belle", "Italian", "Italy"};
    private final String[] DATA3 = {"Juan Carlos", "Spanish", "Spain"};

    private final By DROPDOWN_MENU = By.id("dropdown");
    private final By REFERENCE_MENU = By.id("reference");
    private final By REFERENCE_FILTER_MENU = By.id("reference_with_filter");
    private final By DROPDOWN_MENU_EMBED = By.id("t-15-r-1-dropdown");
    private final By REFERENCE_MENU_EMBED = By.id("t-15-r-1-reference");
    private final By REFERENCE_FILTER_MENU_EMBED = By.id("t-15-r-1-reference_with_filter");
    private final By SAVE_CARD = By.id("pa-entity-form-save-btn");

    private void createPreconditions(String[] data) {
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text() = ' Reference values ']")));
        EntityUtils.createNew(getDriver());
        getDriver().findElement(By.id("label")).sendKeys(data[0]);
        getDriver().findElement(By.id("filter_1")).sendKeys(data[1]);
        getDriver().findElement(By.id("filter_2")).sendKeys(data[2]);
        getDriver().findElement(SAVE_CARD).click();
    }

    private void menuMultiReferenceCheckValue(boolean check1, boolean check2, boolean check3 ) {
        List<WebElement> list = getDriver().findElements(By.xpath("//div[@class='form-check']/label"));
        if (check1) {
            list.get(0).click();
        }
        if (check2) {
            list.get(1).click();
        }
        if (check3) {
            list.get(2).click();
        }
    }

    private void clickButton(By button) {
        TestUtils.scrollClick(getDriver(), getDriver().findElement(button));
    }

    private void menuDropdownSelectValue(String textValue) {
        WebElement dropdownElement = getDriver().findElement(DROPDOWN_MENU);
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(textValue);
    }

    private void menuReferenceSelectValue(String textValue) {
        WebElement dropdownElement = getDriver().findElement(REFERENCE_MENU);
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(textValue);
    }

    private void menuReferenceFilterSelectValue(String textValue) {
        WebElement dropdownElement = getDriver().findElement(REFERENCE_FILTER_MENU);
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(textValue);
    }

    private void menuEmbedDropdownSelectValue(String textValue) {
        WebElement dropdownElement = getDriver().findElement(DROPDOWN_MENU_EMBED);
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(textValue);
    }

    private void menuEmbedReferenceFilterSelectValue(String textValue) {
        WebElement dropdownElement = getDriver().findElement(REFERENCE_FILTER_MENU_EMBED);
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(textValue);
    }

    private void menuEmbedReferenceSelectValue(String textValue) {
        WebElement dropdownElement = getDriver().findElement(REFERENCE_MENU_EMBED);
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(textValue);
    }

    private void menuActionSelectValue(String textValue) {
        getDriver().findElement(By.xpath("//div[@class='dropdown pull-left']")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='" + textValue.toLowerCase(Locale.ROOT) + "']"))).click();
    }

    @Test
    public void testEntityFieldsOpsInput() {
        final String[] expectedDataRecord1 ={"", "Pending", DATA1[0], DATA1[0] + ", " + DATA2[0] + ", " + DATA3[0], DATA3[0], "contact@company.com"};
        final StringBuilder expectedDataRecord2 = new StringBuilder();
        expectedDataRecord2
                .append("Switch\n")
                .append("Dropdown\n")
                .append("Pending\n")
                .append("Reference\n")
                .append("John Doe\n")
                .append("Multireference\n")
                .append("John Doe\n")
                .append("Anna Belle\n")
                .append("Juan Carlos\n")
                .append("Reference with filter\n")
                .append("Juan Carlos\n")
                .append("contact@company.com");
        final StringBuilder expectedDataRecord3 = new StringBuilder();
        expectedDataRecord3
                .append("EmbedFO\n")
                .append("# Switch Dropdown Reference Reference with filter Reference constant Multireference\n")
                .append("1 Plan John Doe Juan Carlos contact@company.com");

        String[] actualDataRecord1 = new String[6];
        Arrays.fill(actualDataRecord1, "");

        createPreconditions(DATA1);
        createPreconditions(DATA2);
        createPreconditions(DATA3);

        EntityUtils.openTestClassByName(getDriver(), "Fields Ops");
        EntityUtils.clickAddCard(getDriver());

        menuDropdownSelectValue("Pending");
        menuReferenceSelectValue(DATA1[0]);
        menuMultiReferenceCheckValue(true, true, true);
        menuReferenceFilterSelectValue(DATA3[0]);
        getDriver().findElement(By.xpath("//tr[@id='add-row-15']/td/button")).click();
        menuEmbedDropdownSelectValue("Plan");
        menuEmbedReferenceSelectValue(DATA1[0]);
        menuEmbedReferenceFilterSelectValue(DATA3[0]);

        Assert.assertEquals(getDriver().findElement(By.id("t-15-r-1-multireference")).getText(), "");
        Assert.assertEquals(getDriver().findElement(By.id("t-15-r-1-reference_constant")).getText().trim(), "contact@company.com");

        clickButton(SAVE_CARD);

        List<WebElement> elements1 = getDriver().findElements(By.xpath("//tbody/tr/td[@class]"));
        for (int i = 0 ; i < elements1.size(); i++) {
            actualDataRecord1[i] = elements1.get(i).getText();
        }
        for (int i = 0 ; i < actualDataRecord1.length; i++) {
            Assert.assertEquals(actualDataRecord1, expectedDataRecord1);
        }

        menuActionSelectValue("View");

        WebElement actualDataRecord2 = getDriver().findElement(By.xpath("(//div[@class='  col-md-12']/div[@style='text-align: left;'])[1]"));
        Assert.assertEquals(actualDataRecord2.getText(), expectedDataRecord2.toString());

        WebElement actualDataRecord3 = getDriver().findElement(By.xpath("(//div[@class='  col-md-12']/div[@style='text-align: left;'])[2]"));
        Assert.assertEquals(actualDataRecord3.getText(), expectedDataRecord3.toString());
    }

    @Ignore
    @Test
    public void testEntityFieldsOpsView() {
        final String[] expectedDataRecord = {DATA1[0], DATA1[0], DATA2[0], DATA3[0], DATA3[0]};

        String[] actualDataRecord = new String[6];
        Arrays.fill(actualDataRecord, "");

        createPreconditions(DATA1);
        createPreconditions(DATA2);
        createPreconditions(DATA3);

        EntityUtils.openTestClassByName(getDriver(), "Fields Ops");
        EntityUtils.clickAddCard(getDriver());

        menuDropdownSelectValue("Pending");
        menuReferenceSelectValue(DATA1[0]);
        menuMultiReferenceCheckValue(true, true, true);
        menuReferenceFilterSelectValue(DATA3[0]);
        getDriver().findElement(By.xpath("//tr[@id='add-row-15']/td/button")).click();
        menuEmbedDropdownSelectValue("Plan");
        menuEmbedReferenceSelectValue(DATA1[0]);
        menuEmbedReferenceFilterSelectValue(DATA3[0]);
        clickButton(SAVE_CARD);

        menuActionSelectValue("View");

        List<WebElement> elements = getDriver().findElements(By.xpath("//div[@class='form-group']/p"));
        for (int i = 0; i < elements.size(); i++) {
            Assert.assertEquals(elements.get(i).getText(), expectedDataRecord[i]);
        }
    }

    @Test
    public void testEntityFieldsOpsDelete() {
        String value = "";
        String[] expectedResult = {"Pending", DATA1[0], value, "contact@company.com"};

        createPreconditions(DATA1);

        EntityUtils.openTestClassByName(getDriver(), "Fields Ops");
        EntityUtils.clickAddCard(getDriver());

        boolean checkBoxSwitch = getDriver().findElement(By.id("switch")).isSelected();
        if (checkBoxSwitch) {
            getDriver().findElement(By.id("switch")).click();
        }

        menuDropdownSelectValue("Pending");
        menuReferenceSelectValue(DATA1[0]);

        value = getDriver().findElement(By.xpath("//div[@class='form-check']/label/input")).getAttribute("value");
        expectedResult[2] = value;

        menuMultiReferenceCheckValue(true, false, false);
        clickButton(SAVE_CARD);

        menuActionSelectValue("Delete");
        getDriver().findElement(By.xpath("//i[text()='delete_outline']")).click();

        WebElement elementTable = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));
        Assert.assertTrue(elementTable.isDisplayed());

        List<WebElement> elements = getDriver().findElements(By.xpath("//tbody/tr/td/a/span/b"));

        Assert.assertEquals(elements.size(), expectedResult.length);

        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            actualResult.add(i, elements.get(i).getText());
        }

        for (int i = 0; i < actualResult.size(); i++) {
            Assert.assertEquals(actualResult.get(i), expectedResult[i]);
        }
    }
}
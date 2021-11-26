import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;

public class EntityFieldsOpsViewTest extends BaseTest {

    private void createRecord(String name, String filter1, String filter2) {
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(text(),'create_new_folder')]"))).click();
        getDriver().findElement(By.xpath("//input[@id='label']")).sendKeys(name);
        getDriver().findElement(By.xpath("//input[@id='filter_1']")).sendKeys(filter1);
        getDriver().findElement(By.xpath("//input[@id='filter_2']")).sendKeys(filter2);
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='pa-entity-form-save-btn']"))).click();
    }

    private WebElement findFirsVisibleElement(String xPath){
        List<WebElement> list = getDriver().findElements(By.xpath(xPath));
        for (WebElement el : list) {
            if(el.isDisplayed()){
                return el;
            }
        }
        return null;
    }

    private void navigateToSideMenuItem(String itemName) {
        Actions act = new Actions(getDriver());
        act.moveToElement(getDriver().findElement(By.className("nav-link"))).perform();
        TestUtils.scrollClick(getDriver(), By.xpath(String.format("//p[contains(text(),'%s')]", itemName)));
    }

    private void clickOnViewEditDelete(String xPath){
        Actions actions = new Actions(getDriver());
        getDriver().findElement(By.xpath("//button/i[@class='material-icons']")).click();
        actions.moveToElement(getDriver().findElement(By.xpath(xPath))).perform();
        actions.click(getDriver().findElement(By.xpath(xPath))).perform();
    }

    private void setUp(){
        Actions act = new Actions(getDriver());
        getDriver().findElement(By.xpath("//div[@class='logo d-flex']"));

        act.moveToElement(getDriver().findElement(By.className("nav-link"))).perform();
        if(!getDriver().findElement(By.xpath("//p[contains(text(),'Reference values')]")).isDisplayed()){
            getDriver().findElement(By.xpath("//p[contains(text(),'Entities')]")).click();
        }
        navigateToSideMenuItem("Reference values");

        createRecord("John Doe", "French", "France");
        createRecord("Anna Belle", "Italian", "Italy");
        createRecord("Juan Carlos", "Spanish", "Spain");

        navigateToSideMenuItem(" Fields Ops");

        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();

        getDriver().findElement(By.xpath("//div[@class='d-flex']/div[@class='togglebutton']/label/span[@class='toggle']")).click();
        getDriver().findElement(By.xpath("//button[@title='Pending']//div[@class='filter-option-inner-inner']")).click();

        act.moveToElement(getDriver().findElement(
                By.xpath("//div[@id='_field_container-reference']//div[contains(text(), '...')]"))).click().perform();
        act.moveToElement(getDriver().findElement(By.xpath("//div[@class='dropdown-menu show']//span[contains(text(),'John Doe')]"))).perform();
        act.click(getDriver().findElement(By.xpath("//div[@class='dropdown-menu show']//span[contains(text(),'John Doe')]"))).perform();

        List<WebElement> checkboxes = getDriver().findElements(By.xpath("//span[@class='check']"));
        checkboxes.forEach(c->c.click());

        act.moveToElement(getDriver().findElement(
                By.xpath("//div[@id='_field_container-reference_with_filter']//div[contains(text(),'...')]"))).click().perform();
        act.moveToElement(getDriver().findElement(By.xpath("//div[@class='dropdown-menu show']//span[contains(text(),'Juan Carlos')]"))).perform();
        act.click(getDriver().findElement(By.xpath("//div[@class='dropdown-menu show']//span[contains(text(),'Juan Carlos')]"))).perform();

        TestUtils.scrollClick(getDriver(),getDriver().findElement(By.xpath("//button[contains(text(),'+')]")));
        findFirsVisibleElement(
                "//div[@class='card-body mc-entity-table']//div[@class='togglebutton']//span[@class='toggle']").click();

        WebElement multireferenceField = findFirsVisibleElement("//div[@class='card ']//td[@valign='top']//textarea[@rows='1']");
        multireferenceField.click();
        multireferenceField.sendKeys("Anna Belle");

        TestUtils.scrollClick(getDriver(),getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));
    }

    @Test
    public void testViewExistingRecords() {
        final String dropdownColumn = "Pending";
        final String referenceColumn = "John Doe";
        final String multireferenceColumn = "John Doe, Anna Belle, Juan Carlos";
        final String referenceWithFilter= "Juan Carlos";
        final String referenceConstant = "contact@company.com";

        final String[] expectedValuesMainPage = {"",dropdownColumn, referenceColumn, multireferenceColumn, referenceWithFilter, referenceConstant};
        final String expextdValuesViewPage = "Switch\n" +
                "Dropdown\n" +
                "Pending\n" +
                "Reference\n" +
                "John Doe\n" +
                "Multireference\n" +
                "John Doe\n" +
                "Anna Belle\n" +
                "Juan Carlos\n" +
                "Reference with filter\n" +
                "Juan Carlos\n" +
                "contact@company.com";
        final String[] expectedEmbedFO = new String[]{"Plan", "John Doe", "Juan Carlos", "contact@company.com"};

        setUp();

        List <WebElement> actualValuesMainPage = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(actualValuesMainPage.size(),expectedValuesMainPage.length);
        for (int i = 0; i < actualValuesMainPage.size(); i++) {
            Assert.assertEquals(actualValuesMainPage.get(i).getText(),expectedValuesMainPage[i]);
        }

        Assert.assertNotNull(findFirsVisibleElement("//i[@class='fa fa-check-square-o']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//button/i[@class='material-icons']"))).click().perform();
        actions.moveToElement(getDriver().findElement(By.xpath("//a[contains(text(),'view')]"))).perform();
        actions.click(getDriver().findElement(By.xpath("//a[contains(text(),'view')]"))).perform();

        Assert.assertNotNull(findFirsVisibleElement("//div[@style='background-color:white']//i[@class='fa fa-check-square-o']"));
        Assert.assertNotNull(findFirsVisibleElement("//table[@id='pa-all-entities-table']//i[@class='fa fa-check-square-o']"));

        String actualValuesViewPage = getDriver().findElement(By.xpath("//div[@style='text-align: left;'][1]")).getText();
        Assert.assertEquals(actualValuesViewPage,expextdValuesViewPage);

        List<WebElement> actualEmbedD = getDriver().findElements(By.xpath("//table[@id='pa-all-entities-table']//td"));
        for (int i = 1; i < expectedEmbedFO.length; i++) {
            Assert.assertEquals(actualEmbedD.get(i+2).getText(), expectedEmbedFO[i]);
        }
    }

    @Test
    public void testEditExistingRecord(){
        final String editOption = "//a[contains(text(),'edit')]";
        final String dropdownDoneOption ="//div[@class='dropdown-menu show']//span[contains(text(),'Done')]";
        final String referenceOption = "//span[contains(text(),'Anna Belle')]";
        final String[] expectedExistingRecordManePage = new String[]{"", "Done", "Anna Belle", "John Doe, Juan Carlos", "Juan Carlos", "contact@company.com"};
        final String expectedValuesViewPage = "Switch\n" +
                "Dropdown\n" +
                "Done\n" +
                "Reference\n" +
                "Anna Belle\n" +
                "Multireference\n" +
                "John Doe\n" +
                "Juan Carlos\n" +
                "Reference with filter\n" +
                "Juan Carlos\n" +
                "contact@company.com";
        final String[] expectedEmbedFO = new String[]{"Done", "John Doe", "Juan Carlos", "contact@company.com"};

        setUp();

        Actions actions = new Actions(getDriver());
        getDriver().findElement(By.xpath("//button/i[@class='material-icons']")).click();
        actions.moveToElement(getDriver().findElement(By.xpath(editOption))).perform();
        actions.click(getDriver().findElement(By.xpath(editOption))).perform();

        Assert.assertNotNull(getDriver().findElement(By.id("pa-entity-form")));

        getDriver().findElement(By.xpath("//div[@class='d-flex']//span[@class='toggle']")).click();

        getDriver().findElement(By.xpath("//div[contains(text(),'Pending')]")).click();
        actions.moveToElement(getDriver().findElement(By.xpath(dropdownDoneOption))).perform();
        actions.click(getDriver().findElement(By.xpath(dropdownDoneOption))).perform();

        getDriver().findElement(By.xpath("//div[@id='_field_container-reference']//div[@class='filter-option-inner']")).click();
        actions.moveToElement(getDriver().findElement(By.xpath(referenceOption))).perform();
        actions.click(getDriver().findElement(By.xpath(referenceOption))).perform();

        getDriver().findElement(By.xpath("//label[contains(text(),'Anna Belle')]//span[@class='check']")).click();

        TestUtils.scrollClick(getDriver(),findFirsVisibleElement("//div[@id='mc-entity-table-container-15']//span[@class='toggle']"));

        Select objSelect =new Select(getDriver().findElement(By.id("t-15-r-1-dropdown")));
        objSelect.selectByVisibleText("Done");

        WebElement fieldEmbedFOTableMultireference = getDriver().findElement(By.id("t-15-r-1-multireference"));
        fieldEmbedFOTableMultireference.clear();
        fieldEmbedFOTableMultireference.sendKeys("Juan Carlos, AnnaBelle");

        TestUtils.scrollClick(getDriver(),getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));

        List <WebElement> actualValuesMainPage = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(actualValuesMainPage.size(),expectedExistingRecordManePage.length);
        for (int i = 0; i < actualValuesMainPage.size(); i++) {
            Assert.assertEquals(actualValuesMainPage.get(i).getText(),expectedExistingRecordManePage[i]);
        }

        getDriver().findElement(By.xpath("//button/i[@class='material-icons']")).click();
        actions.moveToElement(getDriver().findElement(By.xpath("//a[contains(text(),'view')]"))).perform();
        actions.click(getDriver().findElement(By.xpath("//a[contains(text(),'view')]"))).perform();

        String actualValuesViewPage = getDriver().findElement(By.xpath("//div[@style='text-align: left;'][1]")).getText();
        Assert.assertEquals(actualValuesViewPage,expectedValuesViewPage);

        List<WebElement> actualEmbedD = getDriver().findElements(By.xpath("//table[@id='pa-all-entities-table']//td"));
        for (int i = 1; i < expectedEmbedFO.length; i++) {
            Assert.assertEquals(actualEmbedD.get(i+2).getText(), expectedEmbedFO[i]);
        }
    }

    @Ignore
    @Test
    public void testDeleteExistingRecord() {
        final String recycleBin = "//span[@class='notification']/b";
        final String expectedResult = "Switch\n" +
                "Dropdown\n" +
                "Pending\n" +
                "Reference\n" +
                "John Doe\n" +
                "Multireference\n" +
                "John Doe\n" +
                "Anna Belle\n" +
                "Juan Carlos\n" +
                "Reference with filter\n" +
                "Juan Carlos\n" +
                "contact@company.com\n" +
                "EmbedFO\n" +
                "# Switch Dropdown Reference Reference with filter Reference constant Multireference\n" +
                "1 Plan John Doe Juan Carlos contact@company.com";

        setUp();
        clickOnViewEditDelete("//a[contains(text(),'delete')]");

        Assert.assertNull(findFirsVisibleElement("//table[@id='pa-all-entities-table'] "));
        Assert.assertNotNull(getDriver().findElement(By.xpath(recycleBin)));

        getDriver().findElement(By.xpath(recycleBin)).click();

        Assert.assertEquals("Recycle Bin",getDriver().findElement(By.xpath("//b[contains(text(),'Recycle Bin')]")).getText());
        Assert.assertNotNull(getDriver().findElement(By.xpath("//tbody//tr")));

        getDriver().findElement(By.xpath("//tbody//tr//td[@class='pa-recycle-col']//a")).click();

        Assert.assertEquals("DELETED",getDriver().findElement(By.xpath("//b[contains(text(),'DELETED')]")).getText());
        Assert.assertEquals(expectedResult,getDriver().findElement(By.xpath("//div[@class='card-body']")).getText());
    }
}

package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityUtils {

    public static void createNew(WebDriver driver) {
        driver.findElement(By.xpath("//i[text()='create_new_folder']")).click();
    }

    public static void openTestClassByName(WebDriver driver, String nameClass) {
        scrollClick(driver, driver.findElement(By.xpath("//div[@id = 'menu-list-parent']//p[contains(text(), '" + nameClass + "' )]")));
    }

    public static void clickAddCard(WebDriver driver) {
        driver.findElement(By.className("card-icon")).click();
    }

    public static void assertFields(String[] expectedValues, List<WebElement> actualValues, boolean addTextToArr) {
        if (expectedValues != null) {
            List<String> expectedList = new ArrayList<>(Arrays.asList(expectedValues));
            if (addTextToArr) {
                expectedList.add(0, "");
                expectedList.add("menu");
            }
            Assert.assertEquals(actualValues.size(), expectedList.size());
            for (int i = 0; i < actualValues.size(); i++) {
                Assert.assertEquals(actualValues.get(i).getText(), expectedList.get(i));
            }
        }
    }

}

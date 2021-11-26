import Model.MainPage;
import Model.ReferenceValuesFields;
import Model.ReferenceValuesPage;
import base.BaseTest;
import org.testng.annotations.Test;

public class EntityReferenceValuesNew5Test extends BaseTest {

    private final static String[] BASE_VALUES = {"test label", "test filter1", "test filter2"};

    @Test
    public void createNewRecord() {
        MainPage mainPage = new MainPage(getDriver());
        ReferenceValuesPage referenceValuesPage = mainPage.gotoReferenceValues();
        ReferenceValuesFields referenceValuesFields = referenceValuesPage.gotoNewRecord();
        referenceValuesFields.fillLabel(BASE_VALUES[0]);
        referenceValuesFields.fillFilter1(BASE_VALUES[1]);
        referenceValuesFields.fillFilter2(BASE_VALUES[2]);
        referenceValuesFields.clickSaveButton();
        referenceValuesPage.assertCheckingTableRows(BASE_VALUES);
    }
}

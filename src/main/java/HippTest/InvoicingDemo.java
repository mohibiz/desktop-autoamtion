package HippTest;
/**
 * Tests for HIPP Invoicing Page
 *
 * @author Mohit Gupta
 */

import HIPPUtils.CommonSteps;
import HIPPUtils.User;
import automation.base.HippTest;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.HIPPHomePage;
import pageobjects.HIPPInvoicingPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvoicingDemo extends HippTest {
    String ToggleOnMessage = "Showing Unassigned Cases";
    String ToggleOffMessage = "Showing All Cases";
    CommonSteps commonSteps = new CommonSteps();


    @Test(testName = "CheckToggleCase")
    public void CheckToggleCase() {
        WiniumDriver winiumDriver = navigateToInvoicingPage();
        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);

        when("clicked on Case Toggle Button");
        hippInvoicingPage.CaseToggleButton.click();

        then("Validating Case Toggle Text again");
        Assert.assertEquals(hippInvoicingPage.CaseVisibilityTypeText.getAttribute("Name"), ToggleOnMessage);

        commonSteps.CustomWait(2000);
        when("clicked on Case Toggle Button again");
        hippInvoicingPage.CaseToggleButton.click();

        then("Validating Case Toggle Text again");
        Assert.assertEquals(hippInvoicingPage.CaseVisibilityTypeText.getAttribute("Name"), ToggleOffMessage);
    }

    @Test(testName = "checkClearFilterFunction")
    public void checkClearFilterFunction() {
        Map<String, String> row = null;
        WiniumDriver winiumDriver = navigateToInvoicingPage();
        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);

        given("Navigated to Invoicing Page");
        hippInvoicingPage.CaseToggleButton.click();

        when("Clicked on Case Toggle Button to turn it on");
        hippInvoicingPage.EditVendorSearchFilter("G");

        and("Searched for vendors with starting name G");
        commonSteps.CustomWait(3000);

        then("clicked on the clear filter button");
        hippInvoicingPage.ClearFilter.click();

        and("Validate if the  Filter has been cleared or not");
        Assert.assertEquals(hippInvoicingPage.VendorSearch.getAttribute("Name"), "");
        Assert.assertEquals(hippInvoicingPage.CaseVisibilityTypeText.getAttribute("Name"), ToggleOffMessage);

    }

    @Test(testName = "RunThroughOneVendorCase")
    public void RunThroughOneVendorCase() {
        WiniumDriver winiumDriver = ActionInvoices();
        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);
        hippInvoicingPage.CloseBatch.click();
        SaveAndCloseHIPPInvoicing(hippInvoicingPage, winiumDriver);
    }


    @Test(testName = "CheckVendorSearchFilterFunction")
    public void CheckVendorSearchFilterFunction() {
        WiniumDriver winiumDriver = navigateToInvoicingPage();
        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);
        hippInvoicingPage.VendorSearch.clear();
        hippInvoicingPage.VendorSearch.sendKeys("G");
    }


    @Test(testName = "checkFreshProvider")
    public void CheckoutFreshProviderTest() {
        CheckOutFreshProvider();
    }

    @Test(testName = "CheckInvoiceActions")
    public void checkActionInvoiceFunction() {
        ActionInvoices();
    }


    public WiniumDriver ActionInvoices() {

        WiniumDriver winiumDriver = CheckOutFreshProvider();
        given("Vendor is checked out");

        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);

        then("Action All invoices");
        for (int i = 0; i < hippInvoicingPage.InvoiceDataGrid.getRowCount(); i++) {

            when("Invoice line is selected for row number " + i);
            hippInvoicingPage.InvoiceDataGrid.find(i, 0).click();

            then("click on hold check box");
            hippInvoicingPage.HoldCheckBox.click();

            and("click on decline check box");
            hippInvoicingPage.DeclineCheckBox.click();
        }

        return winiumDriver;
    }


    public void SaveAndCloseHIPPInvoicing(HIPPInvoicingPage hippInvoicingPage, WiniumDriver winiumDriver) {
        then("Save the Changes");
        hippInvoicingPage.SaveChanges.click();

        and("Close Application");
        winiumDriver.close();
        /*hippInvoicingPage.closeButton.click();
        HIPPSaveChangesPage hippSaveChangesPage = new HIPPSaveChangesPage(winiumDriver);
        commonSteps.CustomWait(1000);
        hippSaveChangesPage.Yes.click();*/

    }


    public WiniumDriver CheckOutFreshProvider() {
        WiniumDriver winiumDriver = navigateToInvoicingPage();
        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);
        Map<String, String> row = null;
        commonSteps.CustomWait(1000);
        ArrayList row_key = selectVendorFromProviderGrid(hippInvoicingPage);
        then("check out vendor" + row_key.get(1));
        hippInvoicingPage.CheckOutCase.click();
        then("Save the changes");
        hippInvoicingPage.SaveChanges.click();
        return winiumDriver;
    }


    @Test
    public void FilterDataGrid() {
        WiniumDriver winiumDriver = navigateToInvoicingPage();
        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);
        hippInvoicingPage.getDataGrid(hippInvoicingPage.ProviderDataGrid, -1);
    }


    @Test(testName = "CheckProviderData")
    public void CheckProviderData() {
        WiniumDriver winiumDriver = navigateToInvoicingPage();
        HIPPInvoicingPage hippInvoicingPage = new HIPPInvoicingPage(winiumDriver);
        Map<String, String> FilteredData = FilterDataGrid(hippInvoicingPage.getDataGrid(hippInvoicingPage.ProviderDataGrid, -1), "column2", "Double Billing");
        System.out.println(FilteredData);
        hippInvoicingPage.MaximizeButton.click();
        hippInvoicingPage.closeButton.click();
        winiumDriver.close();
    }


    public ArrayList selectVendorFromProviderGrid(HIPPInvoicingPage hippInvoicingPage) {
        Map<String, String> row = null;
        ArrayList row_key = null;
        hippInvoicingPage.CaseToggleButton.click();
        for (int i = 0; i <= hippInvoicingPage.ProviderDataGrid.getRowCount(); i++) {
            row_key = new ArrayList();
            row = hippInvoicingPage.getDataGrid(hippInvoicingPage.ProviderDataGrid, i + 2).get(i);
            String column0 = row.get("column0");
            String column1 = row.get("column1");
            if (column0.equals("Unallocated")) {
                hippInvoicingPage.ProviderDataGrid.find(i, 0).click();
                System.out.println("selected the vendor");
                given("Selected Vendor for Checkout " + column1);

                row_key.add(i);
                row_key.add(column1);
                break;
            } else {
                System.out.println("coud not select row for " + column1);
            }


        }
        return row_key;
    }

    public Map<String, String> FilterDataGrid(ArrayList<HashMap<String, String>> TableData, String FilterColumn, String FilterValue) {

        for (Map<String, String> row : TableData) {
            if (row.get(FilterColumn).contains(FilterValue)) {
                return row;
            }
        }
        return null;
    }

    public WiniumDriver navigateToInvoicingPage() {
        given("login successful");
        WiniumDriver winiumDriver = commonSteps.SignIn(User.INOVOICER1);
        then("Click on Invoicing Button ");
        HIPPHomePage hippHomePage = new HIPPHomePage(winiumDriver);
        hippHomePage.InvoicingButton.click();
        return winiumDriver;
    }


}

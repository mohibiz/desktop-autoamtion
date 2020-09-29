package pageobjects;
/**
 * @author Mohit Gupta
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.WiniumDriver;
import winium.elements.desktop.ComboBox;
import winium.elements.desktop.DataGrid;
import winium.elements.desktop.extensions.WebElementExtensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HIPPInvoicingPage extends HIPPHomePage {

    //Invoicing Header Elements
    public WebElement UserNameText = super.getElementById(driver, "usernamedisplayTxt");
    public WebElement DateUpdateDateTxt = super.getElementById(driver, "dateUpdatedDisplayTxt");
    public WebElement EnableAutoRefresh = super.getElementById(driver, "autorefreshcheckbox");

    //Filter Elements
    public WebElement ScenarioFilter = super.getElementById(driver, "ScenarioFilter");
    public WebElement VendorSearch = super.getElementById(driver, "VendorSearchTextbox");
    public WebElement ClearFilter = super.getElementByName(driver, "Clear Filters");
    public WebElement CaseToggleButton = super.getElementById(driver, "Casestoggle");
    public WebElement CaseVisibilityTypeText = super.getElementById(driver, "CaseVisibilityTypeTxt");

    //Provider Grid element
    public DataGrid ProviderDataGrid = WebElementExtensions.toDataGrid(super.getElementById(driver, "Vendor_DGV"));
    public WebElement ProviderDataGridHeader = driver.findElement(By.id("PART_ColumnHeadersPresenter"));

    // Invoice Grid Elements
    public DataGrid InvoiceDataGrid = WebElementExtensions.toDataGrid(super.getElementById(driver, "ScheduleBlocks_DGV"));
    public WebElement InvoiceDataGridHeader = driver.findElement(By.id("PART_ColumnHeadersPresenter"));
    public WebElement HoldCheckBox = super.getElementById(driver, "holdCheckBox");
    public WebElement DeclineCheckBox = super.getElementById(driver, "DeclineCheckBox");
    public WebElement EscalateCheckBox = super.getElementById(driver, "EscalateCheckBox");
    public WebElement ApproveCheckBox = super.getElementById(driver, "ApproveCheckBox");

    //Checked Out Grid
    public WebElement CheckedOutGrid = super.getElementByName(driver, "CheckedOutListView");

    //Footer elements
    public WebElement Refresh = super.getElementById(driver, "RefreshBtn");
    public WebElement CheckOutCase = super.getElementById(driver, "CheckOutCaseBtn");
    public WebElement CloseBatch = super.getElementById(driver, "CloseCaseBtn");
    public WebElement SaveChanges = super.getElementById(driver, "SaveChangesBtn");


    public HIPPInvoicingPage(WiniumDriver driver) {
        super(driver);
    }

    public boolean exists() {
        return true;
    }


    public void selectScenarioFilter(int scenario_number) {
        ComboBox combo = new ComboBox(ScenarioFilter);
        combo.expand();
        List<WebElement> lis = ScenarioFilter.findElements(By.className("ListBoxItem"));
        lis.get(scenario_number).click();

    }

    public void EditVendorSearchFilter(String searchString) {
        VendorSearch.sendKeys(searchString);
    }


    /*public ArrayList<HashMap<String, String>> getDataGrid() {

        int ScrollJump = 0;

        int TableRowCount = ProviderDataGrid.getRowCount();
        int TableColumnCount = ProviderDataGrid.getColumnCount();
        System.out.println("Total number of rows " + TableRowCount);
        System.out.println("Total number of columns " + TableColumnCount);
        ArrayList<HashMap<String, String>> row_data = new ArrayList();
        for (int RowIndex = 0; RowIndex <= TableRowCount; RowIndex++) {
            HashMap<String, String> column_data = new HashMap<String, String>();
            if (RowIndex != TableRowCount) {
                for (int ColumnIndex = 0; ColumnIndex <= TableColumnCount; ColumnIndex++) {
                    String ColumnName = "column"+Integer.toString(ColumnIndex);

                }
                WebElement element0 = ProviderDataGrid.find(RowIndex, 0);
                WebElement element1 = ProviderDataGrid.find(RowIndex, 1);
                WebElement element2 = ProviderDataGrid.find(RowIndex, 2);
                WebElement element3 = ProviderDataGrid.find(RowIndex, 3);
                if (element1.isDisplayed()) {

                    column_data.put("AssignedTo", element0.getText());
                    column_data.put("VendorNumber", element1.getText());
                    column_data.put("Scenarios", element2.getText());
                    column_data.put("Pending Invoices", element3.getText());
                    row_data.add(column_data);

                    if (RowIndex != TableRowCount - 1) {
                        ScrollJump = RowIndex + 1;
                        ProviderDataGrid.scrollTo(ScrollJump, 1);
                    }
                }

            }

        }

        return row_data;
    }*/


    public ArrayList<HashMap<String, String>> getDataGrid(DataGrid dataGrid, int NumberOfRows) {
        int ScrollJump = 0;
        int TableRowCount = 0;
        if (NumberOfRows == -1) {
            TableRowCount = dataGrid.getRowCount();
        } else {
            TableRowCount = NumberOfRows;
        }
//        int TableRowCount = dataGrid.getRowCount();
        int TableColumnCount = dataGrid.getColumnCount();
        System.out.println("Total number of rows " + TableRowCount);
        System.out.println("Total number of columns " + TableColumnCount);
        ArrayList<HashMap<String, String>> row_data = new ArrayList();
        for (int RowIndex = 0; RowIndex <= TableRowCount; RowIndex++) {
            HashMap<String, String> column_data = new HashMap<String, String>();
            if (RowIndex != TableRowCount - 1) {
                ScrollJump = RowIndex + 1;
                dataGrid.scrollTo(ScrollJump, 1);
            }
            if (RowIndex != TableRowCount) {
                for (int ColumnIndex = 0; ColumnIndex < TableColumnCount; ColumnIndex++) {
                    String ColumnName = "column" + Integer.toString(ColumnIndex);
                    column_data.put(ColumnName, dataGrid.find(RowIndex, ColumnIndex).getText());
                }
                row_data.add(column_data);

            }
        }
        return row_data;
    }


    public ArrayList getDataGridHeader() {
        ArrayList Header = new ArrayList();
//        int TableRowCount =ProviderDataGrid.getColumnCount();
//        System.out.println("Total number of columns " + TableColumnCount);

//        ProviderDataGrid.fin
        return Header;
    }

    public HashMap<String, String> getDataGridData() {
        HashMap<String, String> ProvidersData = new HashMap<String, String>();
        for (int ProviderRowcount = 0; ProviderRowcount <= ProviderDataGrid.getRowCount(); ProviderRowcount++) {

            for (int ProviderColumnCount = 0; ProviderColumnCount <= ProviderDataGrid.getColumnCount(); ProviderColumnCount++) {
                ProvidersData.put(ProviderDataGrid.find(1, ProviderColumnCount).getText(), ProviderDataGrid.find(ProviderColumnCount, ProviderColumnCount).getText());
            }
            return ProvidersData;
        }
/*        // Now get all the TR elements from the table
        List<WebElement> allRows = invoicingDataGrid.findElements(By.tagName("tr"));
// And iterate over them, getting the cells
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                System.out.println("content >>   " + cell.getText());
            }
        }*/
        return ProvidersData;
    }
}

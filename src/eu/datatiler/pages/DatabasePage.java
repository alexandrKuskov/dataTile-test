package eu.datatiler.pages;

import com.qatestlab.base.BasePage;
import com.qatestlab.base.Locator;
import com.qatestlab.base.LocatorTypes;

public class DatabasePage extends BasePage {
    private Locator dviVariablePanel = new Locator(LocatorTypes.CSS, "dvi-variable-panel.dvi-panel-left");
    private Locator table = new Locator(LocatorTypes.CSS, "div.dvi-grid-component");
    private Locator genderVariable = new Locator(LocatorTypes.CSS, "span[title='Gender']");
    private Locator columnCell = new Locator(LocatorTypes.CSS, "div.columns-cells canvas[class='upper-canvas  ui-draggable-handle']");
    private Locator educationVariable = new Locator(LocatorTypes.CSS, "span[title='Education']");
    private Locator rowCell = new Locator(LocatorTypes.CSS, "div.rows-cells canvas[class='upper-canvas  ui-draggable-handle']");
    private Locator exportToDashboardButton = new Locator(LocatorTypes.CSS, "button[title='Export to dashboard']");
    private Locator exportToDashboardPopUp = new Locator(LocatorTypes.CSS, "div.export2dashboards");
    private Locator createNewDashboardButton = new Locator(LocatorTypes.CSS, "div[class='col-sm-6 buttonsBox'] button[tabindex='0']");
    private Locator dashboardNameField = new Locator(LocatorTypes.CSS, "div.text-center input[type=text]");
    private Locator exportToNewDashboardButton = new Locator(LocatorTypes.CSS, "div[class='buttonsBox right'] > div > button");
    private Locator tableAddedMessage = new Locator(LocatorTypes.CSS, "#growlContainer > div");
    private Locator mySpaceLink = new Locator(LocatorTypes.CSS, "a.md-accent");
    private Locator resetStateOfTableButton = new Locator(LocatorTypes.CSS,"button[class='btn btn-default btn-xs reset-button ng-scope']");

    public void waitForDviVariablePanel(){
        waitForVisibility("Waiting for DVI variable panel", dviVariablePanel);
    }

    public void waitForTable(){
        waitForVisibility("Waiting for table", table);
    }

    public boolean tableIsVisible(){
        return isVisible("Check table for visibility", table);
    }

    public void dragGenderVariable(){
        dragAndDrop("Drag gender variable to the column cell", genderVariable, columnCell);
    }

    public void dragEducationVariable(){
        dragAndDrop("Drag education variable to the row cell", educationVariable, rowCell);
    }

    public boolean checkTableWasBuilt(){
        waitForVisibility("Wait for reset state button visibility", resetStateOfTableButton);
        return isVisible("Table was built", resetStateOfTableButton);
    }

    public void clickOnExportToDashboardButton(){
        click("Click on Export To Dashboard button", exportToDashboardButton);
    }

    public void waitForExportToDashboardPopUp(){
        waitForVisibility("Waititng for Export To Dashboard pop up", exportToDashboardPopUp);
    }

    public boolean isExportToDashboardPopUpVisible(){
       return isVisible("Check that 'Export to dashboard' pop up is visible", exportToDashboardPopUp);
    }

    public void clickOnCreateButton(){
        waitForVisibility("Wait for 'CREATE NEW DASHBOARD' button", createNewDashboardButton);
        click("Click on 'CREATE NEW DASHBOARD' button", createNewDashboardButton);
    }

    public void waitForDashboardNameField(){
        waitForVisibility("Waiting for Dashboard name field", dashboardNameField);
    }

    public boolean isExportToDashboardWithNameFieldVisible(){
        return isVisible("Check 'Export to Dashboard' pop up is visible", dashboardNameField);
    }

    public void inputDashboardName(String value){
        type("Inputing Dashboard name", value, dashboardNameField);
    }

    public void clickOnExportToNewDashboardButton(){
        waitForVisibility("Wait for 'EXPORT' button", exportToNewDashboardButton);
        click("Click on 'EXPORT' button", exportToNewDashboardButton);
    }

    public boolean isTableExportedToDashboard(){
        waitForVisibility("Wait for notification with name of dashboard", tableAddedMessage);
        return isVisible("Table was exported to dashboard", tableAddedMessage);
    }

    public void waitMySpaceLinkToBeClickable(){
        waitToBeClickable("Wait for folder to be clickable", mySpaceLink);
    }

    public void clickOnMySpaceLink(){
        click("Click on My Space link", mySpaceLink);
    }
}

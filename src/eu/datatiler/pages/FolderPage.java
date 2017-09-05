package eu.datatiler.pages;

import com.qatestlab.base.BasePage;
import com.qatestlab.base.Locator;
import com.qatestlab.base.LocatorTypes;

public class FolderPage extends BasePage {
    private Locator createButton = new Locator(LocatorTypes.XPATH, "//button[@class='md-raised md-primary uat_creator_btn md-button md-ink-ripple']");
    private Locator dropDownList = new Locator(LocatorTypes.ID, "menu_container_0");
    private Locator createMenuItemByNamePattern = new Locator(LocatorTypes.XPATH,"//span[text()='%s']");
    private Locator createItemByNamePattern = new Locator(LocatorTypes.XPATH,"//strong[text()='%s']");
    private Locator databaseItem = new Locator(LocatorTypes.CSS, "md-menu-item md-icon[md-svg-icon='icon-project']");
    private Locator createDatabasePopUp = new Locator(LocatorTypes.CSS, "div.modal-content");
    private Locator databaseNameField = new Locator(LocatorTypes.CSS, "div.form-group>input");
    private Locator uploadDatabaseButton = new Locator(LocatorTypes.CSS, "input.uat_project_file");
    private Locator createDatabaseButton = new Locator(LocatorTypes.CSS, "button[class='btn btn-primary uat_btn_create']");
    private Locator textAboutFolderEmpty = new Locator(LocatorTypes.CSS, "div[class='text-center message']");
    private Locator database = new Locator(LocatorTypes.XPATH, "//*[./text()='%s']");
    private Locator dashboard = new Locator(LocatorTypes.XPATH, "//*[./text()='%s']");

    public void waitForTextAboutFolderEmpty(){
        waitForVisibility("Wait for text about folder empty", textAboutFolderEmpty);
    }

    public void clickOnCreateButton(){
        waitForVisibility("Wait for 'CREATE' button", createButton);
        click("Click on 'CREATE' button", createButton);
    }

    public void waitForDropDownList(){
        waitForVisibility("Waiting for drop down list", dropDownList);
    }

    public boolean isMenuItemPresent(String itemName){
        waitForVisibility("Wait for visibility of "+itemName, createMenuItemByNamePattern, itemName);
        return isVisible("Check menu item is present: " + itemName, createMenuItemByNamePattern, itemName);
    }

    public void waitDatabaseItemToBeClickable(){
        waitToBeClickable("Wait for 'Database' option to be clickable", databaseItem);
    }

    public void selectDatabaseItem(){
        click("Click on 'Database' item", databaseItem);
    }

    public void waitForDatabasePopUp(){
        waitForVisibility("Wait for 'Creating a database' pop up", createDatabasePopUp);
    }

    public void waitForDatabaseNameField(){
        waitForVisibility("Wait for Database name field", databaseNameField);
    }

    public void inputDatabaseName(String value){
        type("Input Folder name in the field", value, databaseNameField);
    }

    public void uploadDatabase(){
        uploadFile("Upload database from Disk", "C:\\Users\\user\\IdeaProjects\\datatiler-uat\\upload\\Mayor Elections 2013, ENG - weeks encoded, MR-groups.zip", uploadDatabaseButton);
    }

    public void clickOnCreateDatabaseButton(){
        click("Click on 'Create' button", createDatabaseButton);
    }

    public boolean isFileUploaded(String fileName) {
        return getText("Get uploaded file's path", uploadDatabaseButton).contains(fileName);
    }

    public boolean isCreatedItemPresent(String itemName){
        waitForVisibility("Wait for visibility of "+itemName, createItemByNamePattern, itemName);
        return isVisible("Check menu item is present: " + itemName, createItemByNamePattern, itemName);
    }

    public void waitDatabaseToBeClickable(String databaseName){
        waitToBeClickable("Wait for database to be clickable", database, databaseName);
    }

    public void doubleClickOnDatabase(String databaseName){
        click("First click on database", database, databaseName);
        click("Second click on database", database, databaseName);
    }

    public void waitDashboardToBeClickable(String dashboardName){
        waitToBeClickable("Wait for dashboard to be clickable", dashboard, dashboardName);
    }

    public void doubleClickOnDashboard(String dashboardName){
        click("First click on dashboard", dashboard, dashboardName);
        click("Second click on dashboard", dashboard, dashboardName);
    }
}

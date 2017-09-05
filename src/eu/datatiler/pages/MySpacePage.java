package eu.datatiler.pages;

import com.qatestlab.base.BasePage;
import com.qatestlab.base.Locator;
import com.qatestlab.base.LocatorTypes;

public class MySpacePage extends BasePage {
    private Locator createButton = new Locator(LocatorTypes.XPATH, "//button[@class='md-raised md-primary uat_creator_btn md-button md-ink-ripple']");
    private Locator dropDownList = new Locator(LocatorTypes.ID, "menu_container_0");
    private Locator createMenuItemByNamePattern = new Locator(LocatorTypes.XPATH,"//span[text()='%s']");
    private Locator folderItem = new Locator(LocatorTypes.CSS, "button>md-icon[md-svg-icon='icon-folder']");
    private Locator createPopUp = new Locator(LocatorTypes.CSS, "div.modal-content");
    private Locator сreatePopupHeader = new Locator(LocatorTypes.XPATH,"//div[@class='modal-header']//*[text()='%s']");
    private Locator folderNameField = new Locator(LocatorTypes.CSS, "div.form-group>input");
    private Locator createFolderButton = new Locator(LocatorTypes.CSS, "button[class='btn btn-primary uat_btn_create']");
    private Locator folder = new Locator(LocatorTypes.XPATH, "(//md-icon[@md-svg-icon='icon-folder']/../strong[./text()='%s'])[last()]");
    private Locator container = new Locator(LocatorTypes.CSS, "div[class='container']");

    public boolean isMySpacePageDisplayed(){
        waitForVisibility("Wait for My space page", container);
        return isVisible("Check My space page is visible", container);
    }

    public void clickOnCreateButton(){
        waitForVisibility("Waiting for 'CREATE' button", createButton);
        click("Click on 'CREATE' button", createButton);
    }

    public void waitForDropDownList(){
        waitForVisibility("Waiting for drop down list", dropDownList);
    }

    public boolean isCreateItemPresent(String itemName){
        waitForVisibility("Wait for visibility of "+itemName, createMenuItemByNamePattern, itemName);
        return isVisible("Check menu item is present: " + itemName, createMenuItemByNamePattern, itemName);
    }

    public void waitFolderItemToBeClickable(){
        waitToBeClickable("Wait for 'Folder' option to be clickable", folderItem);
    }

    public void selectFolderItem(){
        click("Click on 'Folder' item", folderItem);
    }

    public void waitForFolderPopUp(){
        waitForVisibility("Waiting for 'Creating a folder' pop up", createPopUp);
    }

    public boolean isCreatingPopupPresent(String title) {
        return isVisible("Check is creating dialog present", сreatePopupHeader, title);
    }

    public void waitForFolderNameField(){
        waitForVisibility("Waiting for folder name field", folderNameField);
    }

    public void inputFolderName(String value){
        type("Input Folder name in the field", value, folderNameField);
    }

    public void clickOnCreateFolderButton(){
        click("Click on 'Create' button", createFolderButton);
    }

    public boolean checkFolderIsCreated(String folderName){
       return isVisible("Check message about creation of folder is displayed", folder, folderName);
    }

    public void waitFolderToBeClickable(String folderName){
        waitToBeClickable("Wait for folder to be clickable", folder, folderName);
    }

    public void doubleClickOnFolder(String folderName){
        click("Click on folder", folder, folderName);
        click("Click on folder", folder, folderName);
    }

    public void deleteFolder(String folderName){
        Pages.databasePage().waitMySpaceLinkToBeClickable();
        Pages.databasePage().clickOnMySpaceLink();
        Pages.mySpacePage().waitFolderToBeClickable(folderName);
        click("Click on folder", folder, folderName);
        click("Click on basket", new Locator(LocatorTypes.CSS, "button[class='md-primary md-icon-button uat_remove md-button md-ink-ripple']"));
        waitForVisibility("Wait for pop up with delete", new Locator(LocatorTypes.CSS, "md-dialog[aria-label='Remove item?']"));
        waitToBeClickable("Wait 'DELETE' button to be clickable", new Locator(LocatorTypes.XPATH, "//span[contains(text(), 'Delete')]"));
        click("click on DELETE button", new Locator(LocatorTypes.XPATH, "//span[contains(text(), 'Delete')]"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

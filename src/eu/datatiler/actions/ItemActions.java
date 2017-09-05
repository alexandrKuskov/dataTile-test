package eu.datatiler.actions;

import com.qatestlab.base.BaseActions;
import com.qatestlab.reporting.Reporter;
import com.qatestlab.testrail.CustomStepResult;
import com.qatestlab.testrail.TestRailAssert;
import eu.datatiler.pages.Pages;

public class ItemActions extends BaseActions{

    public void openFolder(String value){
        Pages.mySpacePage().waitFolderToBeClickable(value);
        Pages.mySpacePage().doubleClickOnFolder(value);
    }

    public void createFolder(String itemName, String folderName) {
        Reporter.logAction(String.format("Opening creating dialog for new '%s':", itemName));
        Pages.mySpacePage().clickOnCreateButton();
        Pages.mySpacePage().waitForDropDownList();

        TestRailAssert.assertTrue(
                Pages.mySpacePage().isCreateItemPresent(itemName),
                new CustomStepResult(
                        "Дропдаун с опцией: '" + itemName + "' не отображается",
                        "Отображается дропдаун с опцией: '" + itemName + "'"
                )
        );

        Pages.mySpacePage().waitFolderItemToBeClickable();
        Pages.mySpacePage().selectFolderItem();
        Pages.mySpacePage().waitForFolderPopUp();

        TestRailAssert.assertTrue(
                Pages.mySpacePage().isCreatingPopupPresent(itemName),
                new CustomStepResult(
                        "Не отображается модальное окно'Creating a " + itemName.toLowerCase() + "'",
                        "Отображается модальное окно 'Creating a " + itemName.toLowerCase() + "'"
                )
        );

        Pages.mySpacePage().waitForFolderNameField();
        Pages.mySpacePage().inputFolderName(folderName);
        Pages.mySpacePage().clickOnCreateFolderButton();

        TestRailAssert.assertTrue(
                Pages.mySpacePage().checkFolderIsCreated(folderName),
                new CustomStepResult(
                        "Созданная папка не отображается на странице ",
                        String.format("Папка '%s' успешно создана и отображается на странице My space", folderName)
                )
        );
    }

    public void createDatabase(String itemName, String databaseName) {
        Pages.folderPage().waitForTextAboutFolderEmpty();
        Pages.folderPage().clickOnCreateButton();
        Pages.folderPage().waitForDropDownList();

        TestRailAssert.assertTrue(
                Pages.folderPage().isMenuItemPresent(itemName),
                new CustomStepResult(
                        "Дропдаун с опцией: '" + itemName + "' не отображается",
                        "Отображается дропдаун с опцией: '" + itemName + "'"
                )
        );

        Pages.folderPage().waitDatabaseItemToBeClickable();
        Pages.folderPage().selectDatabaseItem();
        Pages.folderPage().waitForDatabasePopUp();

        TestRailAssert.assertTrue(
                Pages.mySpacePage().isCreatingPopupPresent(itemName),
                new CustomStepResult(
                        "Не отображается модальное окно'Creating a " + itemName.toLowerCase() + "'",
                        "Отображается модальное окно 'Creating a " + itemName.toLowerCase() + "'"
                )
        );

        Pages.folderPage().waitForDatabaseNameField();
        Pages.folderPage().uploadDatabase();

        TestRailAssert.assertTrue(
                Pages.folderPage().isFileUploaded("Mayor Elections 2013, ENG - weeks encoded, MR-groups.zip"),
                new CustomStepResult(
                        "Архив с данными не подгружен",
                        "Архив с данными подгружен")
        );

        Pages.folderPage().inputDatabaseName(databaseName);
        Pages.folderPage().clickOnCreateDatabaseButton();

        TestRailAssert.assertTrue(
                Pages.folderPage().isCreatedItemPresent(databaseName),
                new CustomStepResult(
                        "Проект создан неуспешно или не отображается на странице",
                        "Проект успешно создан и отображается")
        );
    }

    public void openItemInFolder(String value){
        Pages.folderPage().waitDatabaseToBeClickable(value);
        Pages.folderPage().doubleClickOnDatabase(value);
    }

    public void checkItemsInFolder(String dashboardName, String databaseName){
        TestRailAssert.assertTrue(
                Pages.folderPage().isCreatedItemPresent(dashboardName) && Pages.folderPage().isCreatedItemPresent(databaseName),
                new CustomStepResult(
                        "Созданный проект и экспортированный дашбоард не отображаются",
                        "Отображается созданный проект и экспортированный дашбоард")
        );
    }
}

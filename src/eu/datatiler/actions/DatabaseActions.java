package eu.datatiler.actions;

import com.qatestlab.base.BaseActions;
import com.qatestlab.testrail.CustomStepResult;
import com.qatestlab.testrail.TestRailAssert;
import eu.datatiler.pages.Pages;

public class DatabaseActions extends BaseActions {

    public void createTable(String dashboardName){
        Pages.databasePage().waitForDviVariablePanel();
        Pages.databasePage().waitForTable();

        TestRailAssert.assertTrue(
                Pages.databasePage().tableIsVisible(),
                new CustomStepResult(
                        "DVI проекта не отображается",
                        "Отображается DVI проекта"
                )
        );

        Pages.databasePage().dragGenderVariable();
        Pages.databasePage().dragEducationVariable();

        TestRailAssert.assertTrue(
                Pages.databasePage().checkTableWasBuilt(),
                new CustomStepResult(
                        "Кросстаб не заполнен",
                        "Кросстаб успешно заполнен")
        );

        Pages.databasePage().clickOnExportToDashboardButton();
        Pages.databasePage().waitForExportToDashboardPopUp();

        TestRailAssert.assertTrue(
                Pages.databasePage().isExportToDashboardPopUpVisible(),
                new CustomStepResult(
                        "Окно 'Export to dashboard' не отображается",
                        "Отображается окно 'Export to dashboard'")
        );

        Pages.databasePage().clickOnCreateButton();

        TestRailAssert.assertTrue(
                Pages.databasePage().isExportToDashboardWithNameFieldVisible(),
                new CustomStepResult(
                        "Поп-ап 'Export to dashboard' не отображается",
                        "Отображается поп-ап 'Export to dashboard' с полем ввода имени (по дефолту Dashboard for @projectName)")
        );

        Pages.databasePage().waitForDashboardNameField();
        Pages.databasePage().inputDashboardName(dashboardName);
        Pages.databasePage().clickOnExportToNewDashboardButton();

        TestRailAssert.assertTrue(
                Pages.databasePage().isTableExportedToDashboard(),
                new CustomStepResult(
                        "Нотификейшн с именем дашборда не отображается",
                        "Отображается нотификейшн с именем дешборда. Ошибки в консоли отсутствуют.")
        );
    }

    public void openMySpace(){
        Pages.databasePage().waitMySpaceLinkToBeClickable();
        Pages.databasePage().clickOnMySpaceLink();

        TestRailAssert.assertTrue(
                Pages.mySpacePage().isMySpacePageDisplayed(),
                new CustomStepResult(
                        "Страница My space не отображается",
                        "Отображается страница My space")
        );
    }
}

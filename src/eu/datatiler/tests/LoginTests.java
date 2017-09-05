package eu.datatiler.tests;

import com.qatestlab.Random;
import com.qatestlab.base.BasePage;
import com.qatestlab.base.BaseTest;
import com.qatestlab.testrail.CustomStepResult;
import com.qatestlab.testrail.TestRailAssert;
import com.qatestlab.testrail.TestRailIssue;
import eu.datatiler.actions.Actions;
import eu.datatiler.pages.Pages;
import eu.datatiler.utils.DataProviderPool;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private String email = "uat_pm@datatiler.eu";
    private String password = "!F7A4ynJrFtTs@";
    private String folderName = "UAT Test User";
    private String databaseName = "Database for Mayor Elections 2013";
    private String dashboardName = "Dashboard for "+databaseName;

    @Test(description = "Check export of table to dashboard")
    public void test(){
        driver().get(BasePage.BASE_URL);
        Actions.loginActions().login(email, password);

        Actions.itemActions().createFolder("Folder", folderName);
        Actions.itemActions().openFolder(folderName);

        Pages.folderPage().waitForTextAboutFolderEmpty();

        Actions.itemActions().createDatabase("Database", databaseName);
        Actions.itemActions().openItemInFolder(databaseName);

        Actions.databaseActions().createTable(dashboardName);
        Actions.databaseActions().openMySpace();

        Actions.itemActions().openFolder(folderName);
        Actions.itemActions().checkItemsInFolder(dashboardName, databaseName);
        Actions.itemActions().openItemInFolder(dashboardName);
        Actions.dashboardActions().checkTableCreated();

        Pages.mySpacePage().deleteFolder(folderName);
    }
}

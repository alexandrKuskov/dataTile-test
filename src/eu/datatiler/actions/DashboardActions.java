package eu.datatiler.actions;

import com.qatestlab.base.BaseActions;
import com.qatestlab.testrail.CustomStepResult;
import com.qatestlab.testrail.TestRailAssert;
import eu.datatiler.pages.Pages;

public class DashboardActions extends BaseActions {

    public void checkTableCreated(){
        TestRailAssert.assertTrue(
                Pages.dashboardPage().checkTable(),
                new CustomStepResult(
                        "На странице дашбоарда экспортированная таблица не отображается",
                        "На странице дашбоарда отображается экспортированная таблица")
        );
    }
}

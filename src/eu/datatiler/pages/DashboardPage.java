package eu.datatiler.pages;

import com.qatestlab.base.BasePage;
import com.qatestlab.base.Locator;
import com.qatestlab.base.LocatorTypes;

public class DashboardPage extends BasePage {
    private Locator tableInDashboard = new Locator(LocatorTypes.CSS, "items > item");

    public boolean checkTable(){
        waitForVisibility("Wait for table", tableInDashboard);
        return isVisible("Table is in dashboard", tableInDashboard);
    }
}

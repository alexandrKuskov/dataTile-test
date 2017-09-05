package eu.datatiler.pages;

import com.qatestlab.base.BasePage;
import com.qatestlab.base.ObjectsCollection;

public final class Pages {
    private static ObjectsCollection<BasePage> pages = new ObjectsCollection<>();

    public static LoginPage loginPage() {
        return pages.getInstance(LoginPage.class);
    }

    public static MySpacePage mySpacePage() {
        return pages.getInstance(MySpacePage.class);
    }

    public static FolderPage folderPage() {
        return pages.getInstance(FolderPage.class);
    }

    public static DatabasePage databasePage() {
        return pages.getInstance(DatabasePage.class);
    }

    public static DashboardPage dashboardPage() {
        return pages.getInstance(DashboardPage.class);
    }

    public static void clear() {
        pages.clear();
    }
}

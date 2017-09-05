package eu.datatiler.actions;

import com.qatestlab.base.BaseActions;
import com.qatestlab.base.ObjectsCollection;

public final class Actions {
    private static ObjectsCollection<BaseActions> actions = new ObjectsCollection<>();

    public static void clear() {
        actions.clear();
    }

    public static LoginActions loginActions(){return actions.getInstance(LoginActions.class);}

    public static ItemActions itemActions(){return actions.getInstance(ItemActions.class);}

    public static DashboardActions dashboardActions(){return actions.getInstance(DashboardActions.class);}

    public static DatabaseActions databaseActions(){return actions.getInstance(DatabaseActions.class);}
}

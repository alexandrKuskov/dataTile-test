package eu.datatiler.utils;

import com.qatestlab.DataPoolReader;
import org.testng.annotations.DataProvider;

public  class DataProviderPool {
    public static final String USER_CREDENTIALS = "userCredentials";
    public static final String BLOCKED_USER_CREDENTIALS = "blockedUserCredentials";
    public static final String TEST_CREDENTIALS = "testCredentials";
    public static final String DASHBOARD_MANAGER_CREDENTIALS = "dashboardManagerCredentials";
    public static final String PROJECT_MANAGER_CREDENTIALS = "projectManagerCredentials";
    public static final String ENCODER_CREDENTIALS = "encoderCredentials";


    private static DataPoolReader reader = new DataPoolReader();

    /**
     * @return User's login and password.
     */
    @DataProvider(name = USER_CREDENTIALS)
    public static Object[][] getUserLoginData() {
        return reader.GetCredentials(USER_CREDENTIALS);
    }

    @DataProvider(name = BLOCKED_USER_CREDENTIALS)
    public static Object[][] getBlockedUserLoginData(){
        return reader.GetCredentials(BLOCKED_USER_CREDENTIALS);
    }

    @DataProvider(name = TEST_CREDENTIALS)
     public static Object[][] getTestUserLoginData(){
        return reader.GetCredentials(TEST_CREDENTIALS);
    }

    @DataProvider(name = DASHBOARD_MANAGER_CREDENTIALS)
     public static Object[][] getDashboardManagerLoginData(){
        return reader.GetCredentials(DASHBOARD_MANAGER_CREDENTIALS);
    }

    @DataProvider(name = PROJECT_MANAGER_CREDENTIALS)
    public static Object[][] getProjectManagerLoginData(){
        return reader.GetCredentials(PROJECT_MANAGER_CREDENTIALS);
    }

    @DataProvider(name = ENCODER_CREDENTIALS)
    public static Object[][] getEncoderLoginData(){
        return reader.GetCredentials(ENCODER_CREDENTIALS);
    }


}

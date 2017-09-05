package com.qatestlab.testrail;

import org.testng.Assert;

/**
 * Created by Asus on 16.09.2016.
 */
public class TestRailAssert {


    public static void assertTrue(boolean condition, CustomStepResult customStepResult) {
        TestRailIssue.customResults.add(customStepResult);
        Assert.assertTrue(condition,
                customStepResult.getExpected());
        TestRailIssue.customResults.get(TestRailIssue.customResults.size() - 1).setStatus_id(1);
        TestRailIssue.customResults.get(TestRailIssue.customResults.size() - 1)
                .setActual(TestRailIssue.customResults.get(TestRailIssue.customResults.size() - 1).getExpected());

    }

    public static void assertFalse(boolean condition, CustomStepResult customStepResult) {
        assertTrue(!condition, customStepResult);
    }

}

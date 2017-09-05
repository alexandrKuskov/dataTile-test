package com.qatestlab.testrail;

import com.qatestlab.properties.PropertiesNames;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petro on 14.01.2016.
 */
public class TestListener extends TestListenerAdapter implements IInvokedMethodListener {

    private Map<String, Long> cycleNames = new HashMap<>();

//    @Override
//    public void onTestSkipped(ITestResult tr) {
//        super.onTestSkipped(tr);
//        Method method = tr.getMethod().getConstructorOrMethod().getMethod();
//      //TODO adaptation for test rail
//        setUpTestCycle(method, TestResult.BLOCKED);
//    }

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        Method method = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod();
        setUpTestCycle(method);
    }

    private void setUpTestCycle(Method method) {
        for (Annotation a : method.getDeclaredAnnotations()) {
            if (a instanceof TestRailIssue) {
                TestRailIssue annotation = (TestRailIssue) a;
                String cycleName = annotation.testRunName();
                if (cycleNames.get(cycleName) == null) {
                    TestRailTool.createRun(cycleName, PropertiesNames.PROJECT_ID);
                    cycleNames.put(cycleName, TestRailTool.getIdRun());
                }
            }
        }

    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        Method method = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod();
        for (Annotation a : method.getDeclaredAnnotations()) {
            if (a instanceof TestRailIssue) {
                TestRailIssue annotation = (TestRailIssue) a;
                TestResult executionStatus;
                switch (iTestResult.getStatus()) {
                    case (ITestResult.SUCCESS):
                        executionStatus = TestResult.PASS;
                        break;
                    case (ITestResult.FAILURE):
                        executionStatus = TestResult.FAIL;
                        break;
                    case (ITestResult.SKIP):
                        executionStatus = TestResult.BLOCKED;
                        break;
                    default:
                        executionStatus = TestResult.UNTESTED;
                        break;
                }

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (annotation.customResults.size() > 0) {
                    TestRailTool.setTestExecutionStatus(TestRailTool.getIdRun(), annotation.issueID(), executionStatus.getValue(), annotation.customResults);
                } else {
                    TestRailTool.setTestExecutionStatus(TestRailTool.getIdRun(), annotation.issueID(), executionStatus.getValue());
                }

            }
        }

        TestRailIssue.customResults.clear();
    }

    public enum TestResult {
        PASS(1), BLOCKED(2), UNTESTED(3), RETEST(4), FAIL(5);

        private int value;

        TestResult(int statusExecution) {
            this.value = statusExecution;
        }

        public int getValue() {
            return value;
        }
    }

}

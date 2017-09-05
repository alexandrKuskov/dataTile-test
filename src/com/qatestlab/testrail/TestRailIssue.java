package com.qatestlab.testrail;

import com.qatestlab.properties.PropertiesNames;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * Created by Petro on 14.01.2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestRailIssue {

    int issueID();
    int projectID() default PropertiesNames.PROJECT_ID;
    String testRunName();
    ArrayList<CustomStepResult> customResults = new ArrayList<>();

}

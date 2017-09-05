package com.qatestlab.testrail;


import com.qatestlab.properties.PropertiesNames;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TestRailTool {

    // URL for creating new Run
    private static final String CREATE_RUN_URL = PropertiesNames.TEST_RAIL_URL + "/index.php?/api/v2/add_run/%s";

    // URL for setting test execution status
    private static final String SET_TEST_EXECUTION_STATUS = PropertiesNames.TEST_RAIL_URL +
            "/index.php?/api/v2/add_result_for_case/%s/%s";

    //URl for getting project's sections
    private static final String GET_PROJECT_SECTIONS_URL = PropertiesNames.TEST_RAIL_URL +
            "/index.php?/api/v2/get_sections/%s/&suite_id=%s";

    //URL for getting section's cases
    private static final String GET_CASES_SECTION_BY_ID_URL = PropertiesNames.TEST_RAIL_URL +
            "/index.php?/api/v2/get_cases/1/&suite_id=1&&section_id=%s";

    //URL for getting Run's tests
    private static final String GET_RUN_TEST_URL = PropertiesNames.TEST_RAIL_URL + "/index.php?/api/v2/get_tests/%s";

    // Body request URL CREATE_RUN_URL for create Run
    private static final String BODY_CREATE_TEST_CYCLE_REQUEST = "{\n" +
            "\"suite_id\": 1,\n" +
            "\"name\": \"%s\",\n" +
            "\"assignedto_id\": 4,\n" +
            "\"include_all\": false,\n" +
            "\"case_ids\": %s\n" +
            "}";

    // Body request URL SET_TEST_EXECUTION_STATUS for setting test execution status
    private static final String BODY_SET_TEST_EXECUTION_STATUS_REQUEST = "{\"status_id\": %s}";

    private static final String BODY_SET_TEST_EXECUTION_STATUS_WITH_CUSTOM_STEPS_REQUEST =
            "{\"status_id\": %s, \"custom_step_results\": [%s]}";

    private static final String BODY_CUSTOM_STEP =
            "{\"content\": \"Step %s\", \"expected\": \"%s\", \"actual\": \"%s\", \"status_id\": %s}";


    static Map<String, Long> listSections = new HashMap<String, Long>();

    private static long idRun = 0;

    private static WebResource resource;
    private static WebResource.Builder builder;

    /**
     * Initialization request (set url, request header, media type)
     *
     * @param requestURL URL to which request will be sent
     */
    private static void initRequest(String requestURL) {
        resource = Client.create(new DefaultClientConfig()).resource(requestURL);
        builder = resource.accept(MediaType.APPLICATION_JSON);
        builder.type(MediaType.APPLICATION_JSON);
        builder.header(HttpHeaders.AUTHORIZATION, PropertiesNames.AUTHORIZATION_DATA);
    }

    /**
     * The method returns id created Run which was created by method createRun. Default value equal - 0
     *
     * @return id test cycle
     */
    public static long getIdRun() {
        return idRun;
    }

    /**
     * The method sets status statusExecution to the test with id caseId
     * <p>
     * //     * @param caseId         case id
     *
     * @param statusExecution status execution (1 - PASS, 2 - BLOCKED, 3 - UNTESTED, 4 - RETEST, 5 - FAIL)
     */
    public static void setTestExecutionStatus(long idRun, int caseId, int statusExecution) {
        initRequest(String.format(SET_TEST_EXECUTION_STATUS, idRun, caseId));
        builder.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, String.format(BODY_SET_TEST_EXECUTION_STATUS_REQUEST, statusExecution));
    }

    public static void setTestExecutionStatus(long idRun, int caseId, int statusExecution, ArrayList<CustomStepResult> customStepResults) {

        String customStepResult = "";
        int i = 1;

        for (CustomStepResult customStep : customStepResults) {
            customStepResult += String.format(
                    BODY_CUSTOM_STEP,
                    i++,
                    customStep.getExpected(),
                    customStep.getActual(),
                    customStep.getStatus_id()
            ) + ",";
        }

        if (customStepResults.size() >= 1) {
            customStepResult = customStepResult.substring(0, customStepResult.length() - 1);
        }
        initRequest(String.format(SET_TEST_EXECUTION_STATUS, idRun, caseId));
        builder.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, String.format(
                        BODY_SET_TEST_EXECUTION_STATUS_WITH_CUSTOM_STEPS_REQUEST, statusExecution, customStepResult)
                );
    }

    /**
     * The method create Run by name "runName" in project with id is equal projectID
     *
     * @param runName   Run name
     * @param projectID project id
     */
    public static void createRun(String runName, int projectID) {


        JSONParser parser = new JSONParser();

        getProjectSections(projectID, PropertiesNames.PROJECT_ID);

        ArrayList<Long> listCases = TestRailTool.getCasesSectionById((Long) TestRailTool.listSections.get(runName));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initRequest(String.format(CREATE_RUN_URL, projectID));
        try {
            Object obj = parser.parse(builder.type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class,
                            String.format(BODY_CREATE_TEST_CYCLE_REQUEST, runName + " (" +
                                            System.getProperty(PropertiesNames.BROWSER.toString()) + ")".toUpperCase(),
                                    listCases.toString()))
                    .getEntity(String.class));

            JSONObject jsonObject = (JSONObject) obj;

            idRun = (Long) jsonObject.get("id");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void getProjectSections(int projectId, int suiteId) {

        initRequest(String.format(GET_PROJECT_SECTIONS_URL, projectId, suiteId));

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(builder.type(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class)
                    .getEntity(String.class));

            JSONArray jsonArray = (JSONArray) obj;
            for (Object jsonObject : jsonArray) {
                listSections.put((String) (((JSONObject) jsonObject).get("name")), (Long) (((JSONObject) jsonObject).get("id")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Long> getCasesSectionById(long sectionID) {
        initRequest(String.format(GET_CASES_SECTION_BY_ID_URL, sectionID));

        ArrayList<Long> casesId = new ArrayList<Long>();

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(builder.type(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class)
                    .getEntity(String.class));

            JSONArray jsonArray = (JSONArray) obj;
            for (Object jsonObject : jsonArray) {
                casesId.add((Long) (((JSONObject) jsonObject).get("id")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return casesId;
    }

    public static Map<Long, Long> getRunTests(int idRun) {
        initRequest(String.format(GET_RUN_TEST_URL, idRun));

        Map<Long, Long> listTestsId = new HashMap<Long, Long>();

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(builder.type(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class)
                    .getEntity(String.class));

            JSONArray jsonArray = (JSONArray) obj;
            for (Object jsonObject : jsonArray) {
                listTestsId.put((Long) ((JSONObject) jsonObject).get("case_id"),
                        (Long) ((JSONObject) jsonObject).get("id"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listTestsId;
    }
}
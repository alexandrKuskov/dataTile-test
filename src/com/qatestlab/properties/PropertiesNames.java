package com.qatestlab.properties;

public enum PropertiesNames {
    BROWSER("browser"),
    DRIVERS_DIR("drivers.dir"),
    CONFIG_DIR("config.dir"),
    UPLOAD_DIR("upload.dir"),
    REPORT_DIR("report.dir"),
    SCREENSHOT_DIR("screenshots.dir"),
    BASE_URL("base.url"),
    ADMIN_URL("admin.url"),
    BUILD_VERSION("build.version"),
    REFERENCE("Reporting  © Powered by DataTile"),
    TEST_RAIL_URL("https://datatile.testrail.net"),
    AUTHORIZATION_DATA("Basic cGV0ci5raXJ5c2hraW5AdGVzdG1hdGljay5jb206WmNsRzAyOHZQd0ZCZmpZZWU3TVI="),
    PROJECT_NAME("Mayor+Elections+2013%2C+ENG+-+weeks+encoded%2C+MR-groups.sav.zip"),
    ENCODER_NAME("Test_File_BIG.xlsx"),
    IMAGE_NAME("gender.png");

    // Mayor+Elections+2013%2C+ENG+-+weeks+encoded%2C+MR-groups.sav.zip

    public final static int PROJECT_ID = 1;


    private String value;

    private PropertiesNames(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}

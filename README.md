# README #

To run tests you need to install ant: http://ant.apache.org/bindownload.cgi

To install and configure Ant just extract it to the folder you want, for example, C:\ant\
Then you may set path to the Ant binaries in your operating system environment. These actions will allow you to run ant commands without specifying path to binary file (ant.exe)


Tests will run on following browsers:
-	Mozilla Firefox version- 42.0
-	Google Chorme / Chromium version - 46.0
-	Internet Explorer version -  v. 11


Tests execution

Download sources and extract them, for example to folder C:\DataTile\.

Open console and navigate to the root project folder:

cd /d C:\DataTile\

Then start command:

ant testDataTile -Dbrowser=firefox -Dbuild.version=v.0.19.4-305 -Dbase.url=https://demo.datatiler.eu/

If not specified, browser, build.version and base.url will be set to default values.

After this new Firefox browser window will be opened and the tests will run.
While command execution C:\DataTile\build directory will be created.
After the tests execution all test results will be saved in HTML format at C:\DataTile\build\report\html\

To navigate through the report start from index.html file, located in this directory. When all tests will be executed you can run target to generate PDF report:

ant makeReport

Or open html:

ant openReport

For pointing of the tests execution results to corresponding tasks in Jira it is necessary to add property -Dtest.send.status with the value true 

-Dtest.send.status=true

to the test executing launching command.
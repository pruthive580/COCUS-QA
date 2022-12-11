******* This Readme File helps to know requirements to run this project ********
Install JAVA - Version - 1.8.0_202
Install Maven - Version - 3.6.3
Install Android Studio and setup an emulator using SDK manager
Install Eclipse
Install NodeJS
Install TestNG
Install Appium via npm command using NODEJS command prompt as a administrator - npm install -g appium@1.14.2
Setup environment variables for JAVA_HOME,MAVEN_HOME,ANDROID_HOME as well as NPM, ADB and Platform-tools  under system variables
Once everything is setup , Import the project to eclipse via github URL
Make sure Emulator or a real device is connected
Update the config.properties as per required values as per the execution environment as well as platform to be executed
Execution can be done either as a batch using the testng.xml or via individual test scripts in the packages using testng
In case of individual execution from eclipse - Run as TestNG from the individual test script which you want to execute.
Reports can be found in TestResults folder under the project directory with Extent reporting
***********************************************************************************************
To Run the tests via command line please follow the below:
Package the project by using mvn clean package -DskipTests
Once you see a message BUILD SUCCESS, Then the project jar is been created with all the dependencies required to run
We can now use this jar to run in two ways via maven or testng
You can get the jar from the directory of project under "target" folder
copy the testng.xml which u want to run as well as config.prop to the target folder where the jar is been created
copy the app to target/src/test/resources/App/cocus.apk ( Make sure the name of the apk should be cocus)
Once the app , config and testng.xml is copied to target folder , trigger this command in the CMD of target directory to trigger the executions given in the testng.xml - **java -cp 0.0.1-0.0.1-SNAPSHOT-tests.jar;libs/* org.testng.TestNG testng.xml**
Once the executions are done you can find the reports in TestResults folder under target folder.
************************************************************************************************

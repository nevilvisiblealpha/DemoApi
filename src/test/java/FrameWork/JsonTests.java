package FrameWork;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class JsonTests {

    @Test(dataProvider ="Base Urls provider",threadPoolSize = 4)
    public  void ApiExecuter(String basePath) throws Throwable {
        try {
            Reporter.log("For BasePath : "+basePath.toString(),true);
            Reporter.log("*************Expected Response As Below :************************",true);
            Response ExpectedResponse = getQuery("http://uat-webdq.visiblealpha.com:81",basePath);
//"http://uat-webdq.visiblealpha.com:81

            Reporter.log("*************Actual Response As Below  :***************************",true);
            Response ActualResponse = getQuery("http://uat-webdq.visiblealpha.com:81",basePath);
            if(ExpectedResponse.getStatusCode()==200 && ActualResponse.getStatusCode()==200 ) {

                JSONAssert.assertEquals(ExpectedResponse.asString(), ActualResponse.asString(), JSONCompareMode.NON_EXTENSIBLE);
                Reporter.log("***************Passed -Json output Matched**********************************",true);
            }else{

                if(ExpectedResponse.getStatusCode()!=200)
                {
                    Reporter.log("Invalid ResponseCode found from main server : "+  ExpectedResponse.getStatusCode());
                }
                if(ActualResponse.getStatusCode()!=200){

                    Reporter.log("Invalid ResponseCode found from comparing server : "+  ExpectedResponse.getStatusCode());
                }
                throw new Exception("Not 200 request");
            }

        }catch(AssertionError e)
        {

            Reporter.log("*****************Failed -Json output MissMatched****************************",true);
            Reporter.log(e.getMessage(),true);
            String [] ErrorMessages = e.getMessage().split(";");
            String stringBuilder = "";
            StringBuffer HTMLTableString = new StringBuffer();
            for(String message:ErrorMessages)
            {
                if(message.contains("got")) {
                    String messageCaption = message.split("\n")[0];
                    String expectedValue = message.split("\n")[1].split(": ")[1];
                    String actualValue = message.split("\n")[2].split(": ")[1];

                    stringBuilder = "<table>\n" +
                            "  <caption>" + messageCaption + "</aption>\n" +
                            "  <tr>\n" +
                            "    <th>Expected</th>\n" +
                            "    <th>Actual Found</th>\n" +
                            "  </tr>\n" +
                            "  <tr>\n" +
                            "    <td>" + expectedValue + "</td>\n" +
                            "    <td>" + actualValue + "</td>\n" +
                            "  </tr>\n" +
                            "</table>\n";
                    HTMLTableString.append(stringBuilder);
                }

            }

            e.getMessage();

            String HtmlStart = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "table, th, td {\n" +
                    "    border: 1px solid black;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<table>";
             String HtmlEnd ="</body>\n" +
                     "</html>";

             Reporter.log(HtmlStart+HTMLTableString+HtmlEnd,true);


            org.testng.Assert.fail("Failed ..");

        }

    }


    public static Response getQuery(String URL,String basePath) throws Throwable {
        String basePaths = basePath.split("\\?")[0];
        String params = basePath.split("\\?")[1];
        RestAssured.baseURI = URL;
       RestAssured.basePath =basePaths;


            Response response = RestAssured.given().basePath(basePaths).config(RestAssured.config().sslConfig(
                    new SSLConfig().relaxedHTTPSValidation())).contentType(ContentType.JSON).
                    when()
                    .get("?"+params);
           if(response.getStatusCode()==200) {
               Reporter.log(response.asString(), true);
           }else{
               Reporter.log("Response is Not 200");
           }


        return response;
    }



    @DataProvider(name ="Base Urls provider",parallel = true)
    public Iterator<Object[]> getDataproviderValue()
    {
        LogFileReader logfile = new LogFileReader();
        return logfile.fileReader();
    }



}
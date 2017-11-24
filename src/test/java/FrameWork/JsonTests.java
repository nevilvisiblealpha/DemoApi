package FrameWork;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class JsonTests {

    @Test
    public  void ApiExecuter() throws Throwable {
        try {
            Reporter.log("*************Expected Response As Below :************************",true);
            Response ExpectedResponse = getQuery("http://date.jsontest.com/");


            Reporter.log("*************Actual Response As Below  :***************************",true);
            Response ActualResponse = getQuery("http://date.jsontest.com/");

            JSONAssert.assertEquals(ExpectedResponse.asString(), ActualResponse.asString(), JSONCompareMode.NON_EXTENSIBLE);
            Reporter.log("***************Passed -Json output Matched**********************************",true);
        }catch(AssertionError e)
        {

            Reporter.log("*****************Failed -Json output MissMatched****************************",true);
            Reporter.log(e.getMessage(),true);
            String[] ErrorMessages = e.getMessage().split(";");
            String stringBuilder = "";
            StringBuffer HTMLTableString = new StringBuffer();
            for(String message:ErrorMessages)
            {
                   String   messageCaption = message.split("\n")[0];
                   String expectedValue = message.split("\n")[1].split(": ")[1];
                String actualValue = message.split("\n")[2].split(": ")[1];

               stringBuilder = "<table>\n" +
                        "  <caption>"+messageCaption+"</aption>\n" +
                        "  <tr>\n" +
                        "    <th>Expected</th>\n" +
                        "    <th>Actual Found</th>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>"+expectedValue+"</td>\n" +
                        "    <td>"+actualValue+"</td>\n" +
                        "  </tr>\n" +
                        "</table>\n";
                 HTMLTableString.append(stringBuilder);


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


    public static Response getQuery(String URL) throws Throwable {

        RestAssured.baseURI = URL;
        // RestAssured.basePath ="/users/sign_in";


            Response response = RestAssured.given().config(RestAssured.config().sslConfig(
                    new SSLConfig().relaxedHTTPSValidation())).contentType(com.jayway.restassured.http.ContentType.JSON).
                    when()
                    .get();
           Reporter.log(response.asString(),true);


        return response;
    }
}
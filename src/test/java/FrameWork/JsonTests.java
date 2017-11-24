package FrameWork;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
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
           /* Login login = response.as(Login.class, ObjectMapperType.GSON);
            System.out.println(login.getIp());*/

        return response;
    }
}
package FrameWork;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.Config;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.path.xml.config.XmlPathConfig;
import com.jayway.restassured.response.Response;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import static javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType;

public class APImain {


    String baseUrl = null;






    public static void main(String [] args) throws Throwable {
        getQuery();
    }

    public void postQuery() throws Throwable {

        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = "";

        try {
            if ("post".equals("post")) {
                Response resp = RestAssured.given().config(RestAssured.config().sslConfig(
                        new SSLConfig().relaxedHTTPSValidation())).contentType(com.jayway.restassured.http.ContentType.XML).body("").
                        when()
                        .post("");


                resp.then().assertThat().statusCode(200);

            }

        } catch (AssertionError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void getQuery() throws Throwable
    {

        RestAssured.baseURI = "http://ip.jsontest.com/";
       // RestAssured.basePath ="/users/sign_in";

        try {
                   XmlPath xmlPath = RestAssured.given().config(RestAssured.config().sslConfig(
                        new SSLConfig().relaxedHTTPSValidation())).contentType(com.jayway.restassured.http.ContentType.HTML).
                        when()
                        .get().xmlPath().using(new XmlPathConfig().disableLoadingOfExternalDtd()).setRoot("html");

            Response response = RestAssured.given().config(RestAssured.config().sslConfig(
                    new SSLConfig().relaxedHTTPSValidation())).contentType(com.jayway.restassured.http.ContentType.JSON).
                    when()
                    .get();
      Login login =  response.as(Login.class, ObjectMapperType.GSON);
      System.out.println(login.getIp());

         //   System.out.println(xmlPath.getList("html.title"));
                Gson gson = new Gson();

            XmlParserCreator parserCreator = new XmlParserCreator() {

                public XmlPullParser createParser() {
                    try {
                            return XmlPullParserFactory.newInstance().newPullParser();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };

            GsonXml gsonXml = new GsonXmlBuilder()
                    .setXmlParserCreator(parserCreator)
                    .create();
          // Login login = gsonXml.fromXml(response.asString(),Login.class);

            // resp.getHeaders();


                   //Login login = resp.as(Login.class);
               /*System.out.println(login.getContent());
              resp.then().statusCode(200).extract().path("content[2]");*/
        //   XmlPath xmlPath = new XmlPath(resp.asString()).using(new XmlPathConfig().disableLoadingOfExternalDtd());
        // XmlPath xmlPath = new XmlPath(resp.asString()).setRoot("meta");
        // String test = xmlPath.getString("html.title");
        //  String tes = xmlPath.getNode("//meta[@name='csrf-token']").getAttribute("content");


           // System.out.println(tes);
               // System.out.println(resp.asString());


               // resp.then().assertThat().statusCode(200);
             } catch (AssertionError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }






    }



/*

    @Test(groups = {"api"},priority=0)
    public void API_TestCase_Execution_Status() throws Throwable
    {     Framework frameWork = new Framework();
        APIData    apiData  = frameWork.getData(APIData.class, "login");
        CustomSoftAssert CS = new CustomSoftAssert(APIAssertData);
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath =apiData.getPath();

        KeyStore keyStore = null;
        SSLConfig config = null;









        try {
            if(apiData.getMethod().equalsIgnoreCase("post"))
            {

                Response resp = RestAssured.given().config(RestAssured.config().sslConfig(
                        new SSLConfig().relaxedHTTPSValidation())).contentType(ContentType.URLENC).body(apiData.getParameter()).
                        when()
                        .post("");
                System.out.println(resp.asString());
                resp.then().assertThat().statusCode(200);
                Reporter.log(resp.asString());
                CS.assertTrue(resp.asString().contains(apiData.getExpected()),apiData.getTestcaseid());


                resp.then().assertThat().body(containsString(apiData.getExpected()));
                String responseBody = resp.getBody().asString();


                JsonPath jsonPath = new JsonPath(responseBody);
                Token = jsonPath.get("response.authentication_token");
                CustomerId = jsonPath.getString("response.customer_id");
                CS.assertAll();
            }
        } catch (AssertionError e) {
            // TODO Auto-generated catch block
            Assert.fail("Failed Testcase Please check log");
        }
    }




*/





}

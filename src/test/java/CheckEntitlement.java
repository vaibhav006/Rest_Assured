import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.text.Document;
import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.given;

public class CheckEntitlement {

    @BeforeClass
    public static void  setup(){
        RestAssured.baseURI = "https://staging.dreamfolks.in";
    }

   @Test(priority = 1)

    public void positiveCaseLong() {
       Map<String, Object> requestData = new HashMap<>();
       requestData.put("voucher_no", "2392345673793938");
       requestData.put("outlet_id", "455027250329");
       requestData.put("no_of_pax", "1");

       Response response = (Response) RestAssured.given().log().all()
               .header("key", "l3OFzy86j9r3E9EPcvc5")
               .header("secret", "g1SSb6Q32cbtAnrQeXU3")
               .header("Content-Type", "application/json")
               .body(requestData)
               .when()
               .post("api/v2/check-entitlement")
               .then().log().all().assertThat().statusCode(200).extract().response();

       String status = response.jsonPath().getString("status");
       String messagePass = response.jsonPath().getString("message");

       if (status.equalsIgnoreCase("true")) {
           System.out.println("Test case is pass");
       } else {
           System.err.println("Test case is failed");
       }
       System.out.println(messagePass);
   }

       @Test(priority = 2)

       public void positiveCaseShort(){
           Map<String, Object> requestData = new HashMap<>();
           requestData.put("voucher_no", "21763726");
           requestData.put("outlet_id", "455027250329");
//           requestData.put("no_of_pax", "1");

           Response response = (Response) RestAssured.given().log().all()
                   .header("key", "l3OFzy86j9r3E9EPcvc5")
                   .header("secret", "g1SSb6Q32cbtAnrQeXU3")
                   .header("Content-Type", "application/json")
                   .body(requestData)
                   .when()
                   .post("api/v2/check-entitlement")
                   .then().log().all().assertThat().statusCode(200).extract().response();

           String status = response.jsonPath().getString("status");
           String messagePass = response.jsonPath().getString("message");

           if(status.equalsIgnoreCase("true")){
               System.out.println("Test case is pass");
           }
           else {
               System.err.println("Test case is failed");
           }
           System.out.println(messagePass);

   }

   @Test(priority = 3)
    public void noPaxCount(){

       Map<String, Object> requestData = new HashMap<>();
       requestData.put("voucher_no", "2392345673793938");
       requestData.put("outlet_id", "455027250329");

       Response response = (Response) RestAssured.given().log().all()
               .header("key", "l3OFzy86j9r3E9EPcvc5")
               .header("secret", "g1SSb6Q32cbtAnrQeXU3")
               .header("Content-Type", "application/json")
               .body(requestData)
               .when()
               .post("api/v2/check-entitlement")
               .then().assertThat().statusCode(200).extract().response();

       String status = response.jsonPath().getString("status");
       String messagePass = response.jsonPath().getString("message");

       if(status.equalsIgnoreCase("false")){
           System.out.println("Test case is pass");
       }
       else {
           System.err.println("Test case is failed");
       }
       System.out.println(messagePass);
    }

    @Test(priority = 4)
    public void invalidPaxCount(){

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("voucher_no", "2392345673793938");
        requestData.put("outlet_id", "455027250329");
        requestData.put("no_of_pax", "20");

        Response response = (Response) RestAssured.given().log().all()
                .header("key", "l3OFzy86j9r3E9EPcvc5")
                .header("secret", "g1SSb6Q32cbtAnrQeXU3")
                .header("Content-Type", "application/json")
                .body(requestData)
                .when()
                .post("api/v2/check-entitlement")
                .then().assertThat().statusCode(200).extract().response();

        String status = response.jsonPath().getString("status");
        String messagePass = response.jsonPath().getString("message");

        if(status.equalsIgnoreCase("false")){
            System.out.println("Test case is pass");
        }
        else {
            System.err.println("Test case is failed");
        }
        System.out.println(messagePass);
    }

    @Test(priority = 5)
    public void shortInvalidVoucher(){
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("voucher_no", "239234");
        requestData.put("outlet_id", "455027250329");

        Response response = (Response) RestAssured.given().log().all()
                .header("key", "l3OFzy86j9r3E9EPcvc5")
                .header("secret", "g1SSb6Q32cbtAnrQeXU3")
                .header("Content-Type", "application/json")
                .body(requestData)
                .when()
                .post("api/v2/check-entitlement")
                .then().assertThat().statusCode(200).extract().response();

        String status = response.jsonPath().getString("status");
        String messagePass = response.jsonPath().getString("message");

        if(status.equalsIgnoreCase("false")){
            System.out.println("Test case is pass");
        }
        else {
            System.err.println("Test case is failed");
        }
        System.out.println(messagePass);

    }

    @Test(priority = 6)
    public void longInvalidVoucher(){
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("voucher_no", "23923423434552342435345");
        requestData.put("outlet_id", "455027250329");

        Response response = (Response) RestAssured.given().log().all()
                .header("key", "l3OFzy86j9r3E9EPcvc5")
                .header("secret", "g1SSb6Q32cbtAnrQeXU3")
                .header("Content-Type", "application/json")
                .body(requestData)
                .when()
                .post("api/v2/check-entitlement")
                .then().assertThat().statusCode(200).extract().response();

        String status = response.jsonPath().getString("status");
        String messagePass = response.jsonPath().getString("message");

        if(status.equalsIgnoreCase("false")){
            System.out.println("Test case is pass");
        }
        else {
            System.err.println("Test case is failed");
        }
        System.out.println(messagePass);

    }


}

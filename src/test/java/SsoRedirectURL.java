import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.Document;
import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.given;


public class SsoRedirectURL {
    Payload payload = new Payload();
    ChecksumGenerator checksumGenerator = new ChecksumGenerator();
    AESUtils aesUtils = new AESUtils();

    private static final String PASSPHRASE_SSO = "dS8SFdSbFUEzmuRw4hD9SpDk9U4mJwUC"; // 32 bytes
    private static final String IV_SSO = "fnbEncIvStaging1"; // 16 bytes

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://staging.dreamfolks.in";
    }


    @Test
    public void testGetUsers() throws Exception {
        Response response = (Response) RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(payload.requestBodyEnc)
                .when()
                .post("api/enc")
                .then()
                .statusCode(200).extract().response();// Assert status code
        // Validate JSON field

        System.out.println(response.asString());

        String data = response.jsonPath().getString("data");
        System.out.println("Data is: " + data);

        Map<String, Object> requestData1 = new HashMap<>();
        requestData1.put("encrypted_data", data);
        requestData1.put("sku_id", "1000001845");
        requestData1.put("partner_key", "11");
        requestData1.put("request_id", "ABC13456846456");
        requestData1.put("first_name", "Vaibhav");
        requestData1.put("last_name", "Aggarwal");


        given().log().all()
                .header("key", "rxSzGeCipHXBRqBdWsTt")
                .header("secret", "wQ3gIh690K4aA4k1kbbeR8FHPo1kNnvn")
                .header("Content-Type", "application/json")
                .body(requestData1)
                .when()
                .post("api/get-membership-id-pin")
                .then().log().all().assertThat().statusCode(200);

        String encryptedCustomerId = AESUtils.encrypt(payload.customerId, PASSPHRASE_SSO, IV_SSO);

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("client_id", "11");
        requestData.put("request_id", payload.randomId);
        requestData.put("card_identifier", "The Card Company");
        requestData.put("unique_customer_id", encryptedCustomerId);
        requestData.put("testing", "1");

        // Generate checksum dynamically
        String checksum = checksumGenerator.generateChecksum(requestData);

        Response responseSso = (Response) RestAssured.given().log().all().header("x-api-key", "dreamS#dKh0XUfg6sps")
                .header("x-checksum-value",checksum )
                .header("Content-Type", "application/json")
                .body(requestData)
                .when()
                .post("api/getSsoRedirectionURL")
                .then().log().all().assertThat().statusCode(200).extract().response();

//        String html = responseSso.toString();
//        System.out.println("HTML is; "+html);


    }

}
package tests.auth;

import base.BaseTest;
import body.auth.LoginBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class LoginTest extends BaseTest {

    @Test
    public void login() throws IOException {
        // Buat body login
        LoginBody loginBody = new LoginBody();

        // Kirim request POST ke endpoint login
        Response response = given()
                .header("Content-Type", "application/json") // set header
                .body(loginBody.loginValidData().toString()) // isi body
                .when()
                .post("/api/rest/login") // method POST + endpoint
                .then()
                .extract().response();  // ambil response

        // Print response ke console untuk debugging
        System.out.println("Response: " + response.asString());

        // Assert status code 200
        Assert.assertEquals(response.getStatusCode(), 200);

        // Validasi token
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
        System.out.println("Token: " + token);

        // Simpan token ke file resources/json/token.json
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token", token);

        try (FileWriter file = new FileWriter("src/resources/json/token.json")) {
            file.write(tokenJson.toString(4)); // 4 = indentation
            file.flush();
        }

        System.out.println("Token berhasil disimpan di resources/json/token.json");
    }

//    @Test
//    public void loginWithWrongPassword() {
//        // Buat body login
//        LoginBody loginBody = new LoginBody();
//
//        // Kirim request POST ke endpoint login
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(loginBody.loginInvalidPassData().toString()) // isi body
//                .when()
//                .post("/api/rest/login")
//                .then()
//                .extract().response();
//
//        System.out.println("Negative Case - Wrong Password: " + response.asString());
//
//        // Expect 401 Unauthorized or 400 Bad Request (depends on API implementation)
//        Assert.assertEquals(response.getStatusCode(), 401);
//
//        // Check error message
//        Assert.assertEquals(response.jsonPath().getString("error"), "Invalid credentials");
//    }
}

package tests.tickets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class DeleteTicketsTest {
    private String token;

    @BeforeClass
    public void setup() throws Exception {
        // Set base URI dari config.properties
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file src/test/resources/json/token.json
        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();

        System.out.println("Token loaded: " + token);
    }

    @Test
    public void deleteTicketsById() throws IOException {
        // Baca file JSON
        FileReader reader = new FileReader("src/resources/json/ticket_id.json");
        JSONObject json = new JSONObject(new JSONTokener(reader));
        reader.close();

        // Ambil activity_id dari JSON
        String ticketId = json.getString("ticket_id");

        System.out.println("Ticket ID: " + ticketId);

        Response response = given()
                .cookie("__Secure-next-auth.session-token", token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .queryParam("ticketId", ticketId)
                .when()
                .delete("/api/rest/deleteTicket")
                .then()
                .extract().response();

        // Print response
        System.out.println("Response: " + response.asString());

        // Validasi status code
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Status Code = " + response.getStatusCode() + "\nTicket Successfully Deleted.");

        // Validasi error = false
        Boolean success = response.jsonPath().getBoolean("success");
        Assert.assertTrue(success, "Expected success = true");

        // Validasi message
        Assert.assertEquals(response.jsonPath().getString("message"), "Ticket deleted successfully");
    }
}

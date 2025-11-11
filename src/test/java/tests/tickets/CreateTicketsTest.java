package tests.tickets;

import base.BaseTest;
import body.tickets.CreateTicketsBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateTicketsTest  {

    private String token;

    @BeforeClass
    public void setup() throws Exception {
        // Set base URI
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file token.json
        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();
    }

    @Test
    public void createTicket() throws IOException {
        // Buat body dari class
        CreateTicketsBody bodyObj = new CreateTicketsBody();
        JSONObject requestBody = bodyObj.getBodyCreateTickets(Utils.generateRandomTitle(), Utils.generateRandomDescription(),
                Utils.generateRandomattachment(), true);

        // Kirim request POST
        Response response = given()
                .cookie("__Secure-next-auth.session-token", token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/rest/createTicket")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());

        // Validasi status code
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Status Code = " + response.getStatusCode() + "\nTicket Successfully Created.");

        // Save ticket id
        String ticket_id = response.jsonPath().getString("id");
        System.out.println("Ticket ID: " + ticket_id);

        JSONObject tiketJson = new JSONObject();
        tiketJson.put("ticket_id", ticket_id);

        try (FileWriter file = new FileWriter("src/resources/json/ticket_id.json")) {
            file.write(tiketJson.toString(4)); // 4 = indentation
            file.flush();
        }
    }
}

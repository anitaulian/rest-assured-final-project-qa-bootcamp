package tests.tickets;

import body.tickets.UpdateTicketsBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.given;

public class UpdateTicketsTest {

    private String token;
    private String ticketId;

    @BeforeClass
    public void setup() throws Exception {
        // Base URL dari config.properties
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file token.json
        FileReader tokenReader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new JSONTokener(tokenReader));
        token = tokenJson.getString("token");
        tokenReader.close();

        // Baca ticket_id dari file ticket_id.json
        FileReader ticketReader = new FileReader("src/resources/json/ticket_id.json");
        JSONObject ticketJson = new JSONObject(new JSONTokener(ticketReader));
        ticketId = ticketJson.getString("ticket_id");
        ticketReader.close();

        System.out.println("Base URI  : " + RestAssured.baseURI);
        System.out.println("Token     : " + token);
        System.out.println("Ticket ID : " + ticketId);
    }

    @Test
    public void updateTicketStatus() throws Exception {
        // Inisialisasi body builder
        UpdateTicketsBody bodyBuilder = new UpdateTicketsBody();

        // Buat request body (pakai waktu sekarang)
        JSONObject requestBody = bodyBuilder.getBodyUpdateTicket(ticketId, false);

        // Kirim request PUT
        Response response = given()
                .cookie("__Secure-next-auth.session-token", token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .when()
                .put("/api/rest/updateStatusTicket")
                .then()
                .extract().response();

        // Print response
        System.out.println("Response:\n" + response.asPrettyString());

        // Validasi HTTP status
        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200 OK");

        // Validasi data utama
        Assert.assertEquals(response.jsonPath().getString("id"), ticketId, "Ticket ID harus sama");
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"), "updatedAt tidak boleh null");
        Assert.assertEquals(response.jsonPath().getBoolean("isPublic"), true, "isPublic harus true");

        // Validasi field user
        Assert.assertNotNull(response.jsonPath().getString("user.email"), "Email user tidak boleh null");
        Assert.assertNotNull(response.jsonPath().getString("user.username"), "Username user tidak boleh null");

        System.out.println("✅ Ticket " + ticketId + " berhasil diupdate!");
    }
}

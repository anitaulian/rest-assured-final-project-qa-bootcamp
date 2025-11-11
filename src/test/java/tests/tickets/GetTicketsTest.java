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

import static io.restassured.RestAssured.given;

public class GetTicketsTest {

    private String token;

    @BeforeClass
    public void setup() throws Exception {
        // Set base URI dari config.properties
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file src/main/resources/json/token.json
        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();

        System.out.println("✅ Token loaded: " + token);
    }

    @Test
    public void getTicketById() throws Exception {
        // Baca file ticket_id.json
        FileReader reader = new FileReader("src/resources/json/ticket_id.json");
        JSONObject json = new JSONObject(new JSONTokener(reader));
        reader.close();

        // Ambil ticket_id dari file JSON
        String ticket_id = json.getString("ticket_id");
        System.out.println("🎫 Ticket ID: " + ticket_id);

        // Kirim GET request dengan query parameter
        Response response = given()
                .cookie("__Secure-next-auth.session-token", token)
                .header("Accept", "application/json")
                .when()
                .get("/api/rest/ticketById?id=" + ticket_id)
                .then()
                .extract().response();

        // Print response ke console
        System.out.println("🧾 Response:\n" + response.asPrettyString());

        // Validasi status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validasi ID sesuai
        String id = response.jsonPath().getString("id");
        Assert.assertEquals(id, ticket_id, "Ticket ID tidak sesuai");

        // Validasi title & description tidak null
        String title = response.jsonPath().getString("title");
        String description = response.jsonPath().getString("description");

        Assert.assertNotNull(title, "Title should not be null");
        Assert.assertNotNull(description, "Description should not be null");

        // Validasi user object tidak kosong
        Assert.assertNotNull(response.jsonPath().getString("user.email"), "User email tidak boleh null");
        Assert.assertNotNull(response.jsonPath().getString("user.username"), "User username tidak boleh null");

        System.out.println("✅ Ticket detail berhasil diambil untuk ID: " + ticket_id);
    }
}

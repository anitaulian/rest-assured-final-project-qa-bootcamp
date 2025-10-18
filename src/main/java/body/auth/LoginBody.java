package body.auth;

import org.json.JSONObject;
import utils.ConfigReader;

public class LoginBody {

    public JSONObject loginValidData() {
        JSONObject body = new JSONObject();
        body.put("usernameOrEmail", ConfigReader.getProperty("usernameOrEmail"));
        body.put("password", ConfigReader.getProperty("password"));
        return body;
    }

    public JSONObject loginInvalidPassData() {
        JSONObject body = new JSONObject();
        body.put("usernameOrEmail", ConfigReader.getProperty("usernameOrEmail"));
        body.put("password", ConfigReader.getProperty("invalidPassword")); //pass salah
        return body;
    }
}


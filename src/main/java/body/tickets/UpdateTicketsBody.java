package body.tickets;

import org.json.JSONObject;
import java.time.Instant;

public class UpdateTicketsBody {

    /**
     * Membuat body request untuk update status ticket (static)
     * @param ticketId  ID tiket yang akan diupdate
     * @param solvedAt  waktu penyelesaian tiket dalam format ISO 8601
     * @param sendEmail true/false untuk mengirim email notifikasi
     * @return JSONObject body untuk request PUT
     */
    public JSONObject getBodyUpdateTicket(String ticketId, String solvedAt, boolean sendEmail) {
        JSONObject body = new JSONObject();
        body.put("ticketId", ticketId);
        body.put("solvedAt", solvedAt);
        body.put("sendEmail", sendEmail);
        return body;
    }

    /**
     * Membuat body request dinamis otomatis dengan waktu solvedAt = sekarang
     * @param ticketId ID tiket
     * @param sendEmail true/false
     * @return JSONObject
     */
    public JSONObject getBodyUpdateTicket(String ticketId, boolean sendEmail) {
        JSONObject body = new JSONObject();
        body.put("ticketId", ticketId);
        body.put("solvedAt", Instant.now().toString());
        body.put("sendEmail", sendEmail);
        return body;
    }
}

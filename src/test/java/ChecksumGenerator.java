import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class ChecksumGenerator {
    private static final String SECRET = "10c7z&PP}bLm)chQ8z-oCA8>IJHfRkW";

    public String generateChecksum(Map<String, Object> requestData) {
        try {
            // 1. Sort keys like PHP ksort
            Map<String, Object> sortedMap = new TreeMap<>(requestData);

            // 2. JSON encode without escaping slashes or Unicode
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            String jsonBody = mapper.writeValueAsString(sortedMap);

            // 3. Create hash string
            String hashString = SECRET + "|" + jsonBody;

            // 4. SHA-512 hashing
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digest.digest(hashString.getBytes(StandardCharsets.UTF_8));

            // 5. Convert to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating checksum", e);
        }
    }
}

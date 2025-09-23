import java.util.Random;

public class Payload {


    String fixedValue = "79517180-41dd-4b12-";
    String randomsuffix = "b" + (1000+ new Random().nextInt(900000));
    String finalValue = fixedValue + randomsuffix + "|wQ3gIh690K4aA4k1kbbeR8FHPo1kNnvn";
    String customerId = fixedValue + randomsuffix;
    String requestBodyEnc = "{\"enc\":\"" +finalValue+ "\"}";
    int randomId = 1000+ new Random().nextInt(90000000);


}

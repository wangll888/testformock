package java.json;

import java.net.URL;
import java.time.LocalDateTime;

public class Test {
}


class JsApiToken {
    String value;
    Long expiresIn;
    URL webPageUrl;
    LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(expiresIn);
}
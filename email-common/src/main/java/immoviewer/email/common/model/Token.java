package immoviewer.email.common.model;

public class Token {
    private final String token;
    private final String tokenSecret;

    public Token(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

}

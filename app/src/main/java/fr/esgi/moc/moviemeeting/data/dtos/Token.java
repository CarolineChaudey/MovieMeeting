package fr.esgi.moc.moviemeeting.data.dtos;

public class Token {
    private String encryptedToken;

    public Token(String encryptedToken) {
        this.encryptedToken = encryptedToken;
    }

    public String getEncryptedToken() {
        return encryptedToken;
    }

    public void setEncryptedToken(String encryptedToken) {
        this.encryptedToken = encryptedToken;
    }
}

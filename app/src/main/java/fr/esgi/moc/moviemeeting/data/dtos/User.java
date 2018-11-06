package fr.esgi.moc.moviemeeting.data.dtos;

public class User {
    
    private int id;
    private String pseudo;
    private String token;

    public User(int id, String pseudo, String token) {
        this.id = id;
        this.pseudo = pseudo;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

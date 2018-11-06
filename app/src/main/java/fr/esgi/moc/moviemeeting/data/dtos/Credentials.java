package fr.esgi.moc.moviemeeting.data.dtos;

public class Credentials {

    private String pseudo;
    private String password; // don't forget to crypt it !

    public Credentials(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

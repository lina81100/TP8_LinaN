package champollion;

public class Personne {

    private String nom;
    private String email;

    public Personne(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Nom: " + nom + ", Email: " + email;
    }
}

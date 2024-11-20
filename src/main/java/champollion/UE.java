package champollion;

public class UE {
    private String intitule;

    public UE(String intitule) {
        this.intitule = intitule;
    }

    public String getIntitule() {
        return intitule;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UE ue = (UE) obj;
        return intitule != null ? intitule.equals(ue.intitule) : ue.intitule == null;
    }

    @Override
    public int hashCode() {
        return intitule != null ? intitule.hashCode() : 0;
    }
}

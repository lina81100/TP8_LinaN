package champollion;

public class Intervention {
    private UE ue;
    private TypeIntervention type;
    private int duree;

    public Intervention(UE ue, TypeIntervention type, int duree) {
        this.ue = ue;
        this.type = type;
        this.duree = duree;
    }

    public UE getUe() {
        return ue;
    }

    public TypeIntervention getType() {
        return type;
    }

    public int getDuree() {
        return duree;
    }
}

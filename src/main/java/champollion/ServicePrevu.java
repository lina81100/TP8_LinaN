package champollion;

public class ServicePrevu {
    private int volumeCM;
    private int volumeTD;
    private int volumeTP;
    private UE ue;

    public ServicePrevu(int volumeCM, int volumeTD, int volumeTP, UE ue) {
        this.volumeCM = volumeCM;
        this.volumeTD = volumeTD;
        this.volumeTP = volumeTP;
        this.ue = ue;
    }

    public int getVolumeCM() {
        return volumeCM;
    }

    public int getVolumeTD() {
        return volumeTD;
    }

    public int getVolumeTP() {
        return volumeTP;
    }

    public UE getUe() {
        return ue;
    }

    public void addToCM(int volume) {
        this.volumeCM += volume;
    }

    public void addToTD(int volume) {
        this.volumeTD += volume;
    }

    public void addToTP(int volume) {
        this.volumeTP += volume;
    }

    public int getServiceType(TypeIntervention type) {
        switch (type) {
            case CM:
                return volumeCM;
            case TD:
                return volumeTD;
            case TP:
                return volumeTP;
            default:
                return 0;
        }
    }
}

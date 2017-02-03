package bd;

public class Localizacion {

    private float latitud;
    private float altitud;
    private String fecha;
    private String matricula;

    public Localizacion(float latitud, float altitud, String fecha, String matricula) {
        this.altitud = altitud;
        this.latitud = latitud;
        this.matricula = matricula;
        this.fecha = fecha;
    }
    
    public Localizacion(){
        
    }

    public float getLatitud() {
        return latitud;
    }

    public float getAltitud() {
        return altitud;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMatricula() {
        return matricula;
    }
    

}

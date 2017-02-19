package bd;

public class Localizacion {

    private double latitud;
    private double altitud;
    private String fecha;
    private String matricula;

    public Localizacion(double latitud, double altitud, String fecha, String matricula) {
        this.altitud = altitud;
        this.latitud = latitud;
        this.matricula = matricula;
        this.fecha = fecha;
    }
    
    public Localizacion(){
        
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }
    

}

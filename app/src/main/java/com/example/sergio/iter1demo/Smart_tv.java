package com.example.sergio.iter1demo;

public class Smart_tv {

    public String user;
    public String pass;
    public String auspiciador;
    public boolean activo;

    public Smart_tv(){

    }
    public Smart_tv(String user, String pass, String auspiciador){
        this.user = user;
        this.pass = pass;
        this.activo = false;
        this.auspiciador = "";
        for (Auspiciadores each_auspiciador: Auspiciadores.values()) {
            if( auspiciador.equals( each_auspiciador.getDescription() ) ) {
                this.auspiciador = auspiciador;
                this.activo = true;
                break;
            }
        }
    }

    public boolean es_igual(String user, String pass){
        if(!this.activo)
            return false;
        if( !this.user.equals(user) )
            return false;
        if( !this.pass.equals(pass) )
            return false;
        return true;
    }

    public String getAuspiciador() {
        return auspiciador;
    }
}

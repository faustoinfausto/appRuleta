package com.example.sergio.iter1demo;

import java.util.ArrayList;
import java.util.List;

public class Lista_Smart_tv {
    private List<Smart_tv> lista_Smart_tv;
    public Lista_Smart_tv(){
        lista_Smart_tv = new ArrayList<Smart_tv>();

        //Agregamos para este caso
        this.agregar_TV("tv1", "p1", "PLAZA SAN MIGUEL");
        this.agregar_TV("tv2", "p2", "COCA COLA");
        this.agregar_TV("tv3", "p3", "RIPLEY");
        this.agregar_TV("tv4", "p4", "COCA COLA");
        this.agregar_TV("tv5", "p5", "PLAZA SAN MIGUEL");
        this.agregar_TV("tv6", "p6", "WONG");
        this.agregar_TV("tv7", "p7", "WONG");

    }
    public void agregar_TV(String user, String pass, String auspiciador){
        Smart_tv new_Smart_tv = new Smart_tv(user, pass, auspiciador);
        this.lista_Smart_tv.add(new_Smart_tv);
    }

    public boolean usuario_valido(String user, String pass){
        for(Smart_tv aux_Smart_tv : lista_Smart_tv){
            if( aux_Smart_tv.es_igual(user, pass) )
                return true;
        }
        return  false;
    }

    public String getAuspiciador(String user){
        for(Smart_tv aux_Smart_tv : lista_Smart_tv){
            if( aux_Smart_tv.user.equals( user ) )
                return aux_Smart_tv.auspiciador;
        }
        return "";
    }
}

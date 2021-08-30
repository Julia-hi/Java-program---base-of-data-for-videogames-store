/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea_online_8_yulia;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author $Yulia Ogarkova
 */
public class Videojuego implements Serializable {

    private static int ID = 1;
    private int idVideojuego;
    private String tituloJuego, informacion, desarollador;

    private LocalDate fechaSalida;
    private double precio;

    private ArrayList<String> listaClaves;
    private ArrayList<Integer> listaID;
    private static String desarolladores[] = {"Nintendo", "UbiSoft", "Capcom", "Atari", "Konami", "Namco", "Midway", "Sierra", "Microsoft"};

    public static String[] getDesarolladores() {
        return desarolladores;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public void setListaClaves(ArrayList<String> listaClaves) {
        this.listaClaves = listaClaves;
    }

    public void setListaID(ArrayList<Integer> listaID) {
        this.listaID = listaID;
    }

    public ArrayList<Integer> getListaID() {
        return listaID;
    }

    public ArrayList<String> getListaClaves() {
        return listaClaves;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public String getInformacion() {
        return informacion;
    }

    public String getDesarollador() {
        return desarollador;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public double getPrecio() {
        return precio;
    }

    public Videojuego() {
        idVideojuego = 0;
        tituloJuego = "Desconocido";

    }

    public static void setID(int ID) {
        Videojuego.ID = ID;
    }

    public Videojuego(String informacion, String desarollador, LocalDate fechaSalida, double precio, ArrayList<Integer> listaID, ArrayList<String> listaClaves) {
        this.idVideojuego = ID++;
        this.tituloJuego = "Juego#" + idVideojuego;
        this.informacion = informacion;
        this.desarollador = desarollador;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.listaID = listaID;

        this.listaClaves = listaClaves;

    }

    public static Videojuego crearAleatorio() {
        String tipoJuego1[] = {"estrategia", "shooter", "arcade", "simulacion", "deportivo", "accion", "carrera", "battleroyale", "educacion"};
        String tipoJuego2[] = {"online", "offline", "multijugador"};
        String tipoJuego3[] = {"PEGI 3", "PEGI 7", "PEGI 12", "PEGI 16", "PEGI 18"};

        ArrayList<String> listaClaves = new ArrayList();
        ArrayList<Integer> listaID = new ArrayList();
        double precio;
        String informacion, desarollador;
        LocalDate fechaSalida;
        int dia, mes, anio;

        listaID.clear();
        listaClaves.clear();

        precio = (5 + Math.random() * 90);

        int aleatorio = (int) (Math.random() * tipoJuego1.length);
        String clave1 = tipoJuego1[aleatorio];
        aleatorio = (int) (Math.random() * tipoJuego2.length);
        String clave2 = tipoJuego2[aleatorio];
        aleatorio = (int) (Math.random() * tipoJuego3.length);
        String clave3 = tipoJuego3[aleatorio];

        listaClaves.add(clave1);
        listaClaves.add(clave2);
        listaClaves.add(clave3);

        switch (clave3) {
            case "PEGI 3":
                informacion = "Para todos";
                break;
            case "PEGI 7":
                informacion = "Edad min 7 años";
                break;
            case "PEGI 12":
                informacion = "Edad min 12 años";
                break;
            case "PEGI 16":
                informacion = "Edad min 16 años";
                break;
            case "PEGI 18":
                informacion = "Edad min 18 años";
                break;
            default:
                informacion = null;
                break;

        }

        aleatorio = (int) (Math.random() * desarolladores.length);
        desarollador = desarolladores[aleatorio];

        do {
            dia = 1 + (int) (Math.random() * 31);
            mes = 1 + (int) (Math.random() * 12);
            anio = 2020 + (int) (Math.random() * 2); //accepta solo años 2020 y 2021
            try {
                fechaSalida = LocalDate.of(anio, mes, dia);
            } catch (Exception DateTimeException) {
                System.err.println("La fecha invalida");
                // correcto = false;
                fechaSalida = null;
            }

        } while (fechaSalida == null);

        for (int i = 0; i < TiendaJuegos.listaJuegos.size(); i++) {
            if (TiendaJuegos.listaJuegos.get(i).getListaClaves().contains(clave1)
                    & TiendaJuegos.listaJuegos.get(i).getListaClaves().contains(clave3)) {  //seleccionamos videojuegos con iguales claves para recomendar
                Videojuego encontrado = TiendaJuegos.listaJuegos.get(i);
                listaID.add(encontrado.getIdVideojuego());

            }
        }

        Videojuego nuevo = new Videojuego(informacion, desarollador, fechaSalida, precio, listaID, listaClaves);

        return nuevo;
    }

    @Override
    public String toString() {
        DecimalFormat formato = new DecimalFormat("#.00");
        DecimalFormat formato1 = new DecimalFormat("00");

        String fecha = formato1.format(fechaSalida.getDayOfMonth()) + "/" + formato1.format(fechaSalida.getMonth().getValue()) + "/" + fechaSalida.getYear();

        return this.idVideojuego + "\t" + String.format("%-8s", tituloJuego) + "\t" + formato.format(precio) + " €\t\t"
                + fecha + "\t" + String.format("%-9s", desarollador) + "\t" + String.format("%-32s", listaClaves) + "\t"
                + obtenerEstado() + "\t" + String.format("%-18s", informacion) + "\t" + this.listaID;
    }

    public String obtenerEstado() {
        if (fechaSalida.compareTo(LocalDate.now()) > 0) {
            return "P";
        } else {
            return "V";
        }
    }

}

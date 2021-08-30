/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea_online_8_yulia;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author $Yulia Ogarkova
 */
public class TiendaJuegos implements Serializable {

    private int numJuegos = 0;
    protected static ArrayList<Videojuego> listaJuegos = new ArrayList<Videojuego>();
    protected String desarolladores[] = Videojuego.getDesarolladores();

    public boolean anadirJuego(Videojuego nuevo) {
        listaJuegos.add(nuevo);
        return true;

    }

    public void modificar() {
        numJuegos = listaJuegos.size();
        boolean salir = false;
        boolean correcto = false;

        int opcion;
        int posicion;
        Videojuego juegoElegido;

        Scanner entrada = new Scanner(System.in);
        System.out.println("El la tienda hay " + numJuegos + " videojuegos.\n\n--> Para modificar elige la posicion del juego [0 - "
                + (numJuegos - 1) + "]\n" + "--> para salir al menu [-1]\n");

        do {
            int elegida;
            try {
                elegida = entrada.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Error: Por favor, introduzca numero menor que " + numJuegos);
                elegida = -1;
                correcto = true;
            }

            if (elegida < listaJuegos.size() && elegida >= 0) {
                posicion = elegida;
                correcto = true;
                juegoElegido = listaJuegos.get(posicion);
                System.out.println("El juego elegido es: \n\n" + juegoElegido);
                
                do {
                    System.out.println("\nPara modificar los datos del juego elige una opcion:\n\n1. Modificar claves\n"
                            + "2. Modificar recomendaciones.\n3. Modificar(actualizar) estado (V-venta/P-preventa).\n"
                            + "4. Modificar informacion.\n0. Volver al menu principal.\n");
                    opcion = entrada.nextInt();
                    try {
                        switch (opcion) {
                            case 1:
                                modificarClaves(juegoElegido);
                                break;
                            case 2:
                                modificarRecomendados(posicion);
                                break;
                            case 3:
                                modificarEstado(posicion);
                                break;
                            case 4:
                                modificarInfo(posicion);
                                break;
                            case 0:
                                salir = true;
                                break;
                            default:
                                System.out.println("Error! Por favor, introduzca la opcion valida (0-4)");
                                salir = false;
                        }

                    } catch (InputMismatchException e) {
                        System.err.println("Error: Por favor, introduzca la opcion valida (0-4)");
                    }

                } while (salir != true);
            } else if (elegida == -1) {
                correcto = true;
            } else {
                System.err.println("Error: Por favor, introduzca numero menor que " + numJuegos);
                posicion = 0;
                correcto = false;
            }

        } while (correcto != true);

    }

    public void modificarClaves(Videojuego juego) {
        String nueva;
        boolean correcto = false;
        int numClaves;
        Scanner entrada = new Scanner(System.in);
        ArrayList<String> aux = new ArrayList<String>(); //guarda lista de claves actual (antes de modificacion)
        ArrayList<String> nuevasClaves = new ArrayList<String>();

        aux.clear();
        nuevasClaves.clear();
        aux.addAll(juego.getListaClaves());

        System.out.println("Introduzca numero de claves para insertar: ");
        numClaves = entrada.nextInt();

        for (int i = 0; i < numClaves; i++) {
            do {
                System.out.println("Introduzca una clave nueva: \n");
                nueva = entrada.next().toLowerCase();

                if (nuevasClaves.contains(nueva)) { //para evitar repetir claves para el mismo juego
                    System.err.println("La clave introducida ya existe en los datos del juego elegido. \n");
                    correcto = false;
                } else {
                    nuevasClaves.add(nueva); //inserta una clave nueva al final de la lista claves
                    correcto = true;
                }
            } while (correcto == false);
        }
        System.out.println("Claves nuevas son: " + juego.getListaClaves());
        System.out.println("Guardar cambios? S/N ");
        try {
            String opcion = entrada.next().toUpperCase();
            switch (opcion) {
                case "S":

                    juego.setListaClaves(nuevasClaves);
                    System.out.println("\nLas claves han modificado: " + juego.getListaClaves());
                    System.out.println("\n"+juego);
                    break;
                case "N":
                    juego.setListaClaves(aux);
                    System.out.println("\nLas claves quedan sin cambio: " + juego.getListaClaves());
                    break;
                default:
                    System.err.println("Error: Por favor, introduzca una opción válida S/N \n");
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Por favor, introduzca una opción válida S/N \n");
        }
    }

    public void modificarEstado(int posicion) {
        if (listaJuegos.isEmpty()) {
            System.err.println("La tienda esta´vacia todavia, tiene que dar de alta videojuegos primero.");
        } else {
            System.out.println("Estado de cada juego se actualiza automaticamente al momento de imprimir.");
        }

    }

    //modifica la lista de recomendados del juego elegido automaticamente (por claves)
    public void modificarRecomendados(int posicion) {
        Videojuego juego = listaJuegos.get(posicion);
        juego.getListaID().clear(); //vaciar la lista ID de juegos recomendados para rellenar de nuevo
        ArrayList<Integer> nuevaListaID = new ArrayList<Integer>();

        for (int i = 0; i < juego.getListaClaves().size(); i++) { //recorre la lista de claves del juego elegido
            String c = juego.getListaClaves().get(i);  //obtiene cada una clave (String) del juego elegido

            for (int j = 0; j < listaJuegos.size(); j++) {  //busca iguales claves en toda lista de juegos para relacionar recomendaciones (rellenar la lista de ID de nuevo)
                int b = listaJuegos.get(j).getIdVideojuego();
                if (listaJuegos.get(j).getListaClaves().contains(c) && j != posicion && juego.getListaID().contains(b) == false) {
                    nuevaListaID.add(b);
                }
            }
            listaJuegos.get(posicion).setListaID(nuevaListaID);
        }
        System.out.println("Recomendaciones estan modificados: \n" + juego);
    }

    public void modificarInfo(int posicion) {
        Videojuego juego = listaJuegos.get(posicion);
        String info;
        Scanner entrada = new Scanner(System.in);
        System.out.println("Tecla texto para cambiar la informacion: \n");
        info = entrada.nextLine();
        juego.setInformacion(info);
        System.out.println("Informacion esta modificada.\n");
        System.out.println(juego);
    }

//metodo para borrar juego elegido por su posicion y su ID de recomendaciones de todos juegos
    public void borrarJuego() {
        numJuegos = listaJuegos.size();
        int posicion, elegida;
        boolean correcto = false;

        Scanner entrada = new Scanner(System.in);
        System.out.println("en la tienda hay " + numJuegos);
        System.out.println("Elige un videojuego para borrar [0 - " + (numJuegos - 1) + "]: ");

        do {
            try {
                elegida = entrada.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Error: Por favor, introduzca un numero " + (numJuegos - 1) + "]: ");
                elegida = -1;
                correcto = false;
            }
            correcto = true;
        } while (correcto = false);

        if (elegida >= 0 || elegida < listaJuegos.size()) {
            posicion = elegida;
            Videojuego borrar = listaJuegos.get(posicion);
            int del = borrar.getIdVideojuego(); //obtiene ID del juego elegido que sea eliminado

            for (int i = 0; i < listaJuegos.size(); i++) {
                if (listaJuegos.get(i).getListaID().contains(del)) {
                    int posicionID = listaJuegos.get(i).getListaID().indexOf(del); // busca la posicion del ID para eliminar de la lista de recomendados
                    listaJuegos.get(i).getListaID().remove(posicionID);  //borra ID del juego elegido de la lista de recomendaciones de todos juegos que lo contienen

                }
            }
            listaJuegos.remove(posicion);                          //borra el juego elegido
            System.out.println("El juego con ID " + borrar.getIdVideojuego() + " esta eliminado.");
        }

    }

    //metodo para buscar todos juegos por desarollador
    public void buscarJuegos() {
        boolean salir = false;
        Scanner entrada = new Scanner(System.in);
        int opcion;
        String busco;
        int contador = 0;

        System.out.println("Compañías desarrolladoras disponibles: \n");
        for (int i = 0; i < desarolladores.length; i++) {
            System.out.println("[" + i + "] - " + desarolladores[i]);
        }
        System.out.println("Elige una compañía desarrolladora: ");
        do {
            try {
                opcion = entrada.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Error: Por favor, introduzca una opción válida (0-" + (desarolladores.length - 1) + ")");
                salir = false;
                opcion = -1;
            }
            salir = true;

        } while (salir = false);

        for (Videojuego valor : listaJuegos) {

            if (opcion >= 0) {
                busco = desarolladores[opcion];
                if (valor.getDesarollador().equals(busco)) {
                    System.out.println(valor);
                    contador++;
                }
            }
        }
        if (contador == 0) {
            System.out.println("\nNo existen videojuegos desarrollados por " + desarolladores[opcion] + "!\n");
        }
    }

    //metodo para ordenar lista de videojuegos por fecha de salida
    public void ordenarPorFecha() {
        Collections.sort(listaJuegos, new FechaComparador());
        System.out.println("Videojuegos han ordenado por fecha!");
    }

    public void ordenarPorID() {

        Collections.sort(listaJuegos, new ID_comparador());

    }

    //metodo para buscar juegos en preventa (queden menos de 14 días para su salida al mercado)
    public void preventa14dias() {
        int contador = 0;
        long dias;

        for (Videojuego juego : listaJuegos) {
            dias = LocalDate.now().until(juego.getFechaSalida(), ChronoUnit.DAYS); //calcula dias entre fecha de alta de cada juego y fecha actual
            if (dias < 14 && dias >= 0) {
                contador++;
                System.out.println(juego);
            }
        }
        if (contador == 0) {
            System.out.println("\nNo existen videojuegos de preventa con menos de 14 dias.");
        }
    }

    public void listar() {
        int posicion = 0;

        System.out.println("********************************************************************************************************************************************************************");
        System.out.println("*                                                                     VIDEOJUEGOS                                                                                  *");
        System.out.println("********************************************************************************************************************************************************************");
        System.out.println("№" + "\tCodigo" + "\tTitulo" + "\t\tPrecio" + "\t\tFecha_Salida" + "\tDesarollador" + "\tClaves" + "\t\t\t\t\tEstado" + "\tInformación" + "\t\tRecomendados");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Videojuego valor : listaJuegos) {
            System.out.println("" + posicion + "\t" + valor);
            posicion++;
        }
    }

}

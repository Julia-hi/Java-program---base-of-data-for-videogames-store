/*
 
 */
package tarea_online_8_yulia;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author julia
 */
public class Principal_online8 {

    private static TiendaJuegos tienda = new TiendaJuegos();

    public static void main(String[] args) {
        deserializar();
        menu();
    }

    public static void menu() {
        boolean salir = false;

        do {
            System.out.println("");
            System.out.println("\n*****************************************************************************");
            System.out.println("*                                   MENU                                    *");
            System.out.println("*****************************************************************************\n");
            System.out.println("1. Dar de alta un nuevo videojuego (aleatorio).");
            System.out.println("2. Modificar la información de un videojuego.");
            System.out.println("3. Eliminar un videojuego.");
            System.out.println("4. Buscar un videojuego a partir de su compañía desarrolladora.");
            System.out.println("5. Ordenar los videojuegos por su fecha de salida.");
            System.out.println("6. Mostrar videojuegos de Preventa y queden menos de 14 días para su salida al mercado.");
            System.out.println("7. Listar la información de todos los videojuegos almacenados en el sistema.");
            System.out.println("0. Salir.");
            System.out.println("*****************************************************************************\n");
            System.out.print("Por favor, introduzca una opción (0-7): \n");

            try {
                Scanner entrada = new Scanner(System.in);
                int opcion = entrada.nextInt();

                switch (opcion) {
                    case 0:
                        tienda.ordenarPorID(); //ordenar por ID antes de salir
                        serializar();          //serializar antes de salir
                        System.out.println("Hasta pronto!");
                        salir = true;
                        break;
                    case 1:
                        anadirNuevoJuego();
                        break;
                    case 2:
                        if (tienda.listaJuegos.isEmpty()) {
                            System.err.println("La tienda esta´vacia todavia, tienes que insertar videojuegos primero.");
                        } else {
                            tienda.ordenarPorID();
                            tienda.modificar();
                        }
                        break;

                    case 3:
                        if (tienda.listaJuegos.isEmpty()) {
                            System.err.println("La tienda esta´vacia todavia, tienes que insertar videojuegos primero.");
                        } else {
                            tienda.ordenarPorID();
                            tienda.borrarJuego();
                        }
                        break;
                    case 4:
                        if (tienda.listaJuegos.isEmpty()) {
                            System.err.println("La tienda esta´vacia todavia, tienes que insertar videojuegos primero.");
                        } else {
                            tienda.ordenarPorID();
                            tienda.buscarJuegos();
                        }
                        break;
                    case 5:
                        if (tienda.listaJuegos.isEmpty()) {
                            System.err.println("La tienda esta´vacia todavia, tienes que insertar videojuegos primero.");
                        } else {
                            tienda.ordenarPorFecha();
                            tienda.listar();
                        }
                        break;
                    case 6:
                        if (tienda.listaJuegos.isEmpty()) {
                            System.err.println("La tienda esta´vacia todavia, tienes que insertar videojuegos primero.");
                        } else {
                            tienda.ordenarPorID();
                            tienda.preventa14dias();
                        }
                        break;
                    case 7:
                        if (tienda.listaJuegos.isEmpty()) {
                            System.err.println("La tienda esta´vacia todavia, tienes que insertar videojuegos primero.");
                        } else {
                            tienda.listar();
                        }
                        break;
                    default:
                        System.err.println("Error: Por favor, introduzca una opción válida (0-7)");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Por favor, introduzca una opción válida (0-7)");
            }
        } while (salir != true);

    }

    public static void anadirNuevoJuego() {
        int nJuegos = 0;
        boolean correcto = false;
        System.out.print("\n¿Cuántos juegos quieres añadir a la tienda? ");
        do {
            try {
                Scanner entrada = new Scanner(System.in);
                nJuegos = entrada.nextInt();
                correcto = true;
            } catch (InputMismatchException e) {
                System.err.println("Error: debe introducir un número válido");
            }
        } while (correcto != true);
        Videojuego aux;
        for (int i = 0; i < nJuegos; i++) {
            aux = Videojuego.crearAleatorio();
          // TiendaJuegos.listaJuegos.add(aux);                                       // metodo mas comodo y rapido
            tienda.anadirJuego(aux);
            System.out.println("Dado de alta videojuego:\n" + aux.toString());
        }
    }

    public static void serializar() { // SERIALIZACIÓN juego a juego
        String nombreFichero = "tiendaJuegos.dat";
        try {
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreFichero));
            for (int i = 0; i < TiendaJuegos.listaJuegos.size(); i++) {
                salida.writeObject(TiendaJuegos.listaJuegos.get(i));
            }
            System.out.println("\nSe volcaron los datos al fichero " + nombreFichero);
        } catch (IOException e) {
            System.err.println("\nNo se pudo crear el fichero");
        }
    }

    public static void deserializar() {
        String nombreFichero = "tiendaJuegos.dat";
        Videojuego v;
        int juegosLeidos = 1;

        try { // DESERIALIZACIÓN juego a juego
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreFichero));
            while ((v = (Videojuego) entrada.readObject()) != null) {
                tienda.anadirJuego(v);
                juegosLeidos++;
            }

            System.out.println("\n\n Se han cargado correctamente los datos del fichero " + nombreFichero + "\n\n");
        } catch (EOFException e) {
            Videojuego.setID(juegosLeidos);

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            System.err.println("No existen datos de tipo Videojuego en el fichero "
                    + nombreFichero);
        }
    }

}//FIN

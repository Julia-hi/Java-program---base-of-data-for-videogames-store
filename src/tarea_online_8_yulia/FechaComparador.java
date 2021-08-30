package tarea_online_8_yulia;


import java.util.*;


/**
 *
 * @author $Yulia Ogarkova
 */
public class FechaComparador implements Comparator<Videojuego> {
  

  
    @Override
    public int compare(Videojuego o1, Videojuego o2) {
        if(o1.getFechaSalida()==o2.getFechaSalida()){
            return 0;
        }else if(o1.getFechaSalida().compareTo(o2.getFechaSalida())>0){
            return 1;
        }else
        
      return -1;
    }

}

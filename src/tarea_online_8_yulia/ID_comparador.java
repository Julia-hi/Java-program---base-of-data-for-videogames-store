

package tarea_online_8_yulia;

import java.util.Comparator;

/**
 *
 * @author $Yulia Ogarkova
 */
public class ID_comparador implements Comparator<Videojuego> {

    @Override
    public int compare(Videojuego o1, Videojuego o2) {
        if(o1.getIdVideojuego()==o2.getIdVideojuego()){
            return 0;
        }else if(o1.getIdVideojuego()>o2.getIdVideojuego()){
            return 1;
        }else
        
      return -1;
    }
    

}

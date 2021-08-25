package ProyectoTallerProg;

// Fig. 17.13: CrearArchivoAleatorio.java
// Crea un archivo de acceso aleatorio, escribiendo 100 registros vac�os en el disco.
import java.io.*;
import javax.swing.*;

//import com.deitel.cpej5.cap17.RegistroCuentasAccesoAleatorio;

public class CrearArchivo {
    
    private static final int NUMERO_REGISTROS = 100;

   private void crearArchivo()
   {
      JFileChooser selectorArchivo = new JFileChooser();
      selectorArchivo.setFileSelectionMode( JFileChooser.FILES_ONLY );

      int resultado = selectorArchivo.showSaveDialog(null);
   
      if ( resultado == JFileChooser.CANCEL_OPTION )
         return;

      File nombreArchivo = selectorArchivo.getSelectedFile(); 

      if ( nombreArchivo == null || nombreArchivo.getName().equals( "" ) )
         JOptionPane.showMessageDialog( null, "Nombre de archivo incorrecto", 
            "Nombre de archivo incorrecto", JOptionPane.ERROR_MESSAGE );

      else {

         try {           
            RandomAccessFile archivo = 
               new RandomAccessFile( nombreArchivo, "rw" );

            RegistroCuentasAccesoAleatorio registroEnBlanco = 
               new RegistroCuentasAccesoAleatorio();

            for ( int cuenta = 0; cuenta < NUMERO_REGISTROS; cuenta++ )
               registroEnBlanco.escribir( archivo );

            archivo.close(); // cerrar el archivo
            
            JOptionPane.showMessageDialog( null, "Se creo el archivo " + 
               nombreArchivo, "Estado", JOptionPane.INFORMATION_MESSAGE );
            System.exit(0);
         } catch ( IOException excepcionES ) {
            JOptionPane.showMessageDialog( null, "Error al procesar el archivo",
               "Error al procesar el archivo", JOptionPane.ERROR_MESSAGE );
            System.exit(1);
         }

      }

   }

   public static void main( String args[] )
   {
      CrearArchivo aplicacion = new CrearArchivo();
      aplicacion.crearArchivo();
   }   

}

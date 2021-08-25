package ProyectoTallerProg;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import javax.swing.*;

public class LeerArchivo extends JFrame {
   private IUBanco interfazUsuario;
   private RandomAccessFile entrada;  
   private JButton botonSiguiente, botonAbrir;
   
   private static DecimalFormat dosDigitos = new DecimalFormat( "0.00" );
   
   public LeerArchivo()
   {
      super( "Leer archivo del cliente" );
      interfazUsuario = new IUBanco( 4 );  // cuatro campos de texto
      getContentPane().add( interfazUsuario );
      botonAbrir = interfazUsuario.obtenerBotonHacerTarea1();
      botonAbrir.setText( "Abrir archivo para leer..." );
      botonAbrir.addActionListener(

         new ActionListener() {
            public void actionPerformed( ActionEvent evento )
            {      
               abrirArchivo();
            }
         }
      );  

      botonSiguiente = interfazUsuario.obtenerBotonHacerTarea2();
      botonSiguiente.setText( "Siguiente" );
      botonSiguiente.setEnabled( false );

      botonSiguiente.addActionListener(

         new ActionListener() {

            public void actionPerformed( ActionEvent evento )
            {
               leerRegistro();
            }
         }
      );
   
      addWindowListener(

         new WindowAdapter() {
            public void windowClosing( WindowEvent evento )
            {
               cerrarArchivo();
            }

         }

      );

      setSize( 300, 150 );
      setVisible( true );  

   } // fin del constructor
   
   // permitir al usuario seleccionar el archivo a abrir
   private void abrirArchivo()
   {
      JFileChooser selectorArchivo = new JFileChooser();
      selectorArchivo.setFileSelectionMode( JFileChooser.FILES_ONLY );

      int resultado = selectorArchivo.showOpenDialog( this );
   
      if ( resultado == JFileChooser.CANCEL_OPTION )
         return;

      // obtener el archivo seleccionado
      File nombreArchivo = selectorArchivo.getSelectedFile();

      // mostrar error si el nombre de archivo es incorrecto
      if ( nombreArchivo == null || nombreArchivo.getName().equals( "" ) )
         JOptionPane.showMessageDialog( this, "Nombre de archivo incorrecto", 
            "Nombre de archivo incorrecto", JOptionPane.ERROR_MESSAGE );

      else {

         // abrir el archivo
         try {
            entrada = new RandomAccessFile( nombreArchivo, "r" );
            botonSiguiente.setEnabled( true );
            botonAbrir.setEnabled( false );
         }
         
         // atrapar excepci�n que puede ocurrir al abrir el archivo
         catch ( IOException ioException ) {
            JOptionPane.showMessageDialog( this, "El archivo no existe", 
               "Nombre de archivo incorrecto", JOptionPane.ERROR_MESSAGE );
         }
      }
   }
   
   // leer un registro
   private void leerRegistro()
   {
      RegistroCuentasAccesoAleatorio registro = new RegistroCuentasAccesoAleatorio();
   
      // leer un registro y mostrarlo
      try {

         do {
            registro.leer( entrada );
         } while ( registro.obtenerCuenta() == 0 );

         String valores[] = { String.valueOf( registro.obtenerCuenta() ),
            registro.obtenerPrimerNombre(), registro.obtenerApellidoPaterno(),
            String.valueOf( registro.obtenerSaldo() ) };
         interfazUsuario.establecerValoresCampos( valores );
      }

      // cerrar el archivo al llegar a su fin
      catch ( EOFException excepcionEOF ) {
         JOptionPane.showMessageDialog( this, "No hay mas registros",
            "Se lleg� al fin del archivo", JOptionPane.INFORMATION_MESSAGE );
         cerrarArchivo();
      }

      catch ( IOException excepcionES ) {
         JOptionPane.showMessageDialog( this, "Error al leer el archivo", 
            "Error", JOptionPane.ERROR_MESSAGE );

         System.exit( 1 );
      }

   }
   
   private void cerrarArchivo() 
   {
      // cerrar el archivo y salir
      try {
         if ( entrada != null )
            entrada.close();

         System.exit( 0 );
      }

      catch( IOException excepcionES ) {
         JOptionPane.showMessageDialog( this, "Error al cerrar el archivo",
            "Error", JOptionPane.ERROR_MESSAGE );

         System.exit( 1 );
      }

   }

   public static void main( String args[] )
   {
      new LeerArchivo();
   }

}
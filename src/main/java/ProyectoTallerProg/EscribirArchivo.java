package ProyectoTallerProg;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class EscribirArchivo extends JFrame {

    private RandomAccessFile salida;
    private IUBanco interfazUsuario;
    private JButton botonEntrar, botonAbrir;

    private static final int NUMERO_REGISTROS = 100;

    // configurar GUI
    public EscribirArchivo() {
        super("Escribir en archivo de acceso aleatorio");

        // crear instancia de la interfaz de usuario reutilizable IUBanco
        interfazUsuario = new IUBanco(4);  // cuatro campos de texto
        getContentPane().add(interfazUsuario,
                BorderLayout.CENTER);

        botonAbrir = interfazUsuario.obtenerBotonHacerTarea1();
        botonAbrir.setText("Abrir...");

        botonAbrir.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evento) {
                    abrirArchivo();
                }
            }
        ); // fin de la llamada a addActionListener

        // registrar componente de escucha de ventana para el evento de cierre de ventana
        addWindowListener(
                new WindowAdapter() {
            public void windowClosing(WindowEvent evento) {
                if (salida != null) {
                    agregarRegistro();
                }
                cerrarArchivo();
            }
        }
        ); // fin de la llamada a addWindowListener

        // obtener referencia al bot�n de tarea gen�rico hacerTarea2 en IUBanco
        botonEntrar = interfazUsuario.obtenerBotonHacerTarea2();
        botonEntrar.setText("Introducir");
        botonEntrar.setEnabled(false);

        // registrar componente de escucha para llamar a agregarRegistro cuando se oprima el bot�n
        botonEntrar.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                agregarRegistro();
            }
        }
        ); // fin de la llamada a addActionListener

        setSize(300, 150);
        setVisible(true);
    }

    // permitir al usuario seleccionar el archivo a abrir
    private void abrirArchivo() {
        // mostrar cuadro de di�logo para que el usuario pueda seleccionar el archivo
        JFileChooser selectorArchivo = new JFileChooser();
        selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int resultado = selectorArchivo.showOpenDialog(this);

        if (resultado == JFileChooser.CANCEL_OPTION) {
            return;
        }

        // obtener el archivo seleccionado
        File nombreArchivo = selectorArchivo.getSelectedFile();

        // mostrar error si el nombre de archivo es incorrecto
        if (nombreArchivo == null || nombreArchivo.getName().equals("")) {
            JOptionPane.showMessageDialog(this, "Nombre de archivo incorrecto",
                    "Nombre de archivo incorrecto", JOptionPane.ERROR_MESSAGE);
        } else {

            // abrir el archivo
            try {
                salida = new RandomAccessFile(nombreArchivo, "rw");
                botonEntrar.setEnabled(true);
                botonAbrir.setEnabled(false);
            } catch (IOException excepcionES) {
                JOptionPane.showMessageDialog(this, "El archivo no existe",
                        "Nombre de archivo incorrecto", JOptionPane.ERROR_MESSAGE);
            }

        } // fin de instrucci�n else

    } // fin del m�todo abrirArchivo

    // cerrar el archivo y terminar la aplicaci�n
    private void cerrarArchivo() {
        // cerrar el archivo y salir
        try {
            if (salida != null) {
                salida.close();
            }

            System.exit(0);
        } // procesar excepci�n que puede ocurrir al cerrar el archivo
        catch (IOException excepcionES) {
            JOptionPane.showMessageDialog(this, "Error al cerrar el archivo",
                    "Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }

    } // fin del m�todo cerrarArchivo

    // agregar un registro al archivo
    private void agregarRegistro() {
        String campos[] = interfazUsuario.obtenerValoresCampos();

        // asegurarse que el campo cuenta tenga un valor
        if (!campos[IUBanco.CUENTA].equals("")) {

            // escribir valores en el archivo
            try {
                int numeroCuenta = Integer.parseInt(campos[IUBanco.CUENTA]);

                if (numeroCuenta > 0 && numeroCuenta <= NUMERO_REGISTROS) {
                    RegistroCuentasAccesoAleatorio registro
                            = new RegistroCuentasAccesoAleatorio();

                    registro.establecerCuenta(numeroCuenta);

                    registro.establecerPrimerNombre(campos[IUBanco.PRIMERNOMBRE]);
                    registro.establecerApellidoPaterno(campos[IUBanco.APELLIDO]);
                    registro.establecerSaldo(Double.parseDouble(
                            campos[IUBanco.SALDO]));

                    salida.seek((numeroCuenta - 1) * RegistroCuentasAccesoAleatorio.TAMANIO);
                    registro.escribir(salida);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "El n�mero de cuenta debe ser entre 0 y 100",
                            "N�mero de cuenta incorrecto", JOptionPane.ERROR_MESSAGE);
                }

                interfazUsuario.borrarCampos();  // borrar el contenido de los campos de texto

            } // fin del bloque try
            // procesar formato incorrecto de n�mero de cuenta o saldo
            catch (NumberFormatException excepcionFormato) {
                JOptionPane.showMessageDialog(this,
                        "N�mero de cuenta o saldo incorrectos",
                        "Formato de n�mero incorrecto", JOptionPane.ERROR_MESSAGE);
            } // procesar excepciones que pueden ocurrir al escribir en el archivo
            catch (IOException excepcionES) {
                JOptionPane.showMessageDialog(this,
                        "Error al escribir en el archivo", "Excepci�n de ES",
                        JOptionPane.ERROR_MESSAGE);
                cerrarArchivo();
            }

        } // fin de instrucci�n if

    } // fin del m�todo agregarRegistro

    public static void main(String args[]) {
        new EscribirArchivo();
    }

} // fin de la clase EscribirArchivoAleatorio

/**
 * ************************************************************************
 * (C) Copyright 1992-2003 by Deitel & Associates, Inc. and * Prentice Hall. All
 * Rights Reserved. * * DISCLAIMER: The authors and publisher of this book have
 * used their * best efforts in preparing the book. These efforts include the *
 * development, research, and testing of the theories and programs * to
 * determine their effectiveness. The authors and publisher make * no warranty
 * of any kind, expressed or implied, with regard to these * programs or to the
 * documentation contained in these books. The authors * and publisher shall not
 * be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 * ***********************************************************************
 */

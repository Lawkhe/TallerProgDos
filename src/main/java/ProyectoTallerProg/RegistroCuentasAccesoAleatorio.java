
package ProyectoTallerProg;

import java.io.*;

public class RegistroCuentasAccesoAleatorio extends RegistroCuentas {
  
    public static final int TAMANIO = 72;
    
    public RegistroCuentasAccesoAleatorio() {
        this(0, "", "", 0.0);
    }

    public RegistroCuentasAccesoAleatorio(int cuenta, String primerNombre,
            String apellidoPaterno, double saldo) {
        super(cuenta, primerNombre, apellidoPaterno, saldo);
    }

    public void leer(RandomAccessFile archivo) throws IOException {
        establecerCuenta(archivo.readInt());
        establecerPrimerNombre(leerNombre(archivo));
        establecerApellidoPaterno(leerNombre(archivo));
        establecerSaldo(archivo.readDouble());
    }

    private String leerNombre(RandomAccessFile archivo) throws IOException {
        char nombre[] = new char[15], temp;

        for (int cuenta = 0; cuenta < nombre.length; cuenta++) {
            temp = archivo.readChar();
            nombre[cuenta] = temp;
        }

        return new String(nombre).replace('\0', ' ');
    }

    public void escribir(RandomAccessFile archivo) throws IOException {
        archivo.writeInt(obtenerCuenta());
        escribirNombre(archivo, obtenerPrimerNombre());
        escribirNombre(archivo, obtenerApellidoPaterno());
        archivo.writeDouble(obtenerSaldo());
    }

    private void escribirNombre(RandomAccessFile archivo, String nombre)
            throws IOException {
        StringBuffer bufer = null;

        if (nombre != null) {
            bufer = new StringBuffer(nombre);
        } else {
            bufer = new StringBuffer(15);
        }

        bufer.setLength(15);
        archivo.writeChars(bufer.toString());
    }

}
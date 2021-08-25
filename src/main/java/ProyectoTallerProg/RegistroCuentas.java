
package ProyectoTallerProg;

import java.io.Serializable;

public class RegistroCuentas implements Serializable {
   private int cuenta;
   private String primerNombre;
   private String apellidoPaterno;
   private double saldo;
   
   public RegistroCuentas() 
   {
      this( 0, "", "", 0.0 );
   }
  
   public RegistroCuentas(int cta, String nombre, String apellido, double sald )
   {
      establecerCuenta(cta);
      establecerPrimerNombre(nombre);
      establecerApellidoPaterno(apellido);
      establecerSaldo(sald);
   }

   public void establecerCuenta( int cta )
   {
      cuenta = cta;
   }

   public int obtenerCuenta() 
   { 
      return cuenta; 
   }
   
   public void establecerPrimerNombre( String nombre )
   {
      primerNombre = nombre;
   }

   public String obtenerPrimerNombre() 
   { 
      return primerNombre; 
   }
   
   public void establecerApellidoPaterno( String apellido )
   {
      apellidoPaterno = apellido;
   }

   public String obtenerApellidoPaterno() 
   {
      return apellidoPaterno; 
   }
   
   public void establecerSaldo( double sald )
   {
      saldo = sald;
   }

   public double obtenerSaldo() 
   { 
      return saldo; 
   }

}

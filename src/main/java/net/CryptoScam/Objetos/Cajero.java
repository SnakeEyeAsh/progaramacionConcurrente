package net.CryptoScam.Objetos;

public class Cajero implements Runnable{
    protected final CuentaCrypto cuenta;
    protected final String nombre;
    protected final boolean robar;
    protected final double cantidad;

    public Cajero(CuentaCrypto cuenta, String nombre, boolean robar, double cantidad){
        this.cuenta=cuenta;
        this.nombre=nombre;
        this.robar = robar;
        this.cantidad=cantidad;
    }


    @Override
    public void run() {
        if(robar){
            cuenta.retirar(cantidad,nombre);
        }else{
            cuenta.ingresar(cantidad,nombre);
        }
    }
}

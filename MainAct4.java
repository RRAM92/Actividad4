/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DWFan95
 */
import javax.swing.JOptionPane;

//NODO
class Nodo{
    int dato;
    String nombre;
    Nodo izq, dch;
    
    public Nodo(int d, String nom){
        this.dato = d;
        this.nombre = nom;
        this.izq = null;
        this.dch = null;
    }
    
    @Override
    public String toString(){
        return nombre + " - su dato es " + dato;
    }
}

//ARBOL
class Arbol{
    Nodo raiz;
    public Arbol(){raiz = null;}
    
    public void agregar(int d, String nom){
        Nodo nuevo = new Nodo(d, nom);
        if (raiz == null){
            raiz = nuevo;
            return;
        }
        Nodo aux = raiz;
        Nodo padre;
        while(true){
            padre = aux;
            if (d < aux.dato){
                aux = aux.izq;
                if(aux == null){
                    padre.izq = nuevo;
                    return;
                }
            } else {
                aux = aux.dch;
                if (aux == null){
                    padre.dch = nuevo;
                    return;
                }
            }
        }
    }
    
    public Nodo buscar(int d){
        Nodo aux = raiz;
        while(aux != null){
            if(d == aux.dato){return aux;}
            else if(d < aux.dato){aux = aux.izq;}
            else {aux = aux.dch;}
        }
        return null;
    }
    
    public void eliminar(int d){raiz = eliminarRec(raiz, d);}
    private Nodo eliminarRec(Nodo nodo, int d){
        if (nodo == null) return null;
        else if (d < nodo.dato){nodo.izq = eliminarRec(nodo.izq, d);}
        else{
            if (nodo.izq == null) return nodo.dch;
            else if (nodo.dch == null) return nodo.izq;
            
            Nodo sucesor = encontrarMinimo(nodo.dch);
            nodo.dato = sucesor.dato;
            nodo.nombre = sucesor.nombre;
            nodo.dch = eliminarRec(nodo.dch, sucesor.dato);
        }
        return nodo;
    }
    
    private Nodo encontrarMinimo(Nodo nodo){
        while(nodo.izq != null){nodo = nodo.izq;}
        return nodo;
    }
    
    public boolean vacio(){return raiz == null;}
    public void inorden(){inorden(raiz);}
    private void inorden(Nodo r){
        if(r != null){
            inorden(r.izq);
            System.out.println(r.dato + " -> " + r.nombre);
            inorden(r.dch);
        }
    }
    public void preorden(){preorden(raiz);}
    private void preorden(Nodo r){
        if(r != null){
            System.out.println(r.dato + " -> " + r.nombre);
            preorden(r.izq);
            preorden(r.dch);
        }
    }
    public void postorden(){postorden(raiz);}
    private void postorden(Nodo r){
        if(r != null){
            postorden(r.izq);
            postorden(r.dch);
            System.out.println(r.dato + " -> " + r.nombre);
        }
    }
}

public class MainAct4 {
    public static void main(String[] args){
        //DATOS DE LOS EMPLEADOS
        int[] ids = {98, 87, 68, 42, 32, 34, 83, 85, 17, 18};
        String[] nombres = {"René", "Mariana", "Violeta", "Alfredo", "Bonifacio", "Tatiana", "Isabel", "Vanesa", "Gregorio", "Jessica"};
        
        //POBLACIÓN DEL ÁRBOL
        Arbol arbol = new Arbol();
        for(int i = 0; i < ids.length; i++){arbol.agregar(ids[i], nombres[i]);}
        
        //PEDIR IDENTIDAD
        String input = JOptionPane.showInputDialog(null, "Ingrese ID: ", "Búsqueda de Empleado", JOptionPane.QUESTION_MESSAGE);
        if(input == null) return; //CANCELACIÓN
        int idBuscar = Integer.parseInt(input);
        
        //BUSQUEDA SECUENCIAL
        long inicioSec = System.nanoTime();
        String resultadoSec = secuencial(ids, nombres, idBuscar);
        long tiempoSec = System.nanoTime() - inicioSec;
        
        //BUSQUEDA EN EL ARBOL
        long inicioArb = System.nanoTime();
        Nodo encontrado = arbol.buscar(idBuscar);
        long tiempoArb = System.nanoTime() - inicioArb;
        String resultadoArb = (encontrado != null) ? encontrado.toString() : "NO DETECTADO";
        
        //RESULTADOS
        String mensaje = """
                         SECUENCIAL:
                         """ + resultadoSec + "\n" + "TIEMPO: " + tiempoSec + " ns\n\n" + "ARBOL:\n" + resultadoArb + "\n" + "TIEMPO: " + tiempoArb + " ns";
        JOptionPane.showMessageDialog(null, mensaje, "RESULTADOS", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String secuencial(int[] ids, String[] nombres, int clave) {
        for(int i = 0; i < ids.length; i++){
            if (ids[i] == clave){return nombres[i] + " - su dato es " + ids[i];}
        }
        return "NO ENCONTRADO";
    }
}

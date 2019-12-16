/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloomfilter;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author aguil
 */
public class BloomFilter<T> {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        boolean busca;
        int fp = 0;
        int cantPal = 10;
        
        String[] pals = creaArr(cantPal);
        int e = 2;
        boolean salir = false;
        while(!salir)
        {
            fp = 0;
            boolean[] arr = new boolean[e];
            for(int i = 0; i< pals.length ; i++)
            {
                int h1 = hash1(e, pals[i]);
                int h2 = hash2(e, pals[i]);
                
                //System.out.println(h1 +"   "+h2+" "+i);
                arr[h1] = true;
                arr[h2] = true;
            }
            e++;
            System.out.println("Todo bien");
            
            try
            {
                Scanner archivo;
                File file;
                file = new File("C:\\Users\\aguil\\OneDrive\\Documents\\Compu\\Estructura de Datos\\Trie\\wiki-100k.txt");
                archivo = new Scanner(file);
                String[] arre = new String[5000000];
                int i = 0;
            
                while(archivo.hasNext())
                {
                    arre[i] = archivo.next();
                    busca = busca(arre[i], arr);
                    if(busca && i>= cantPal)
                    {
                        fp = fp +1;
                    }
                    i++;
                }
                //System.out.println("FP:"+fp+"aree"+i);
                
                int porcentaje = (fp*100)/i;
                
                if(porcentaje<= 30)
                {
                    System.out.println("porcentaje Final"+porcentaje);
                    salir = true;
                    e--;
                }
                System.out.println("porcentaje "+porcentaje+ "  FP: "+fp);
            
            }
            catch(Exception ex)
            {
                System.out.println("Main: "+ex);
                System.exit(-1);
            }
            
            
            
        }
        
        
       System.out.println("El tamaño es:"+e);
    }
    
    private static String[] creaArr(int cantPal)
    {
        String[] pals;
        try
        {
            Scanner archivo;
            File file;
            file = new File("C:\\Users\\aguil\\OneDrive\\Documents\\Compu\\Estructura de Datos\\Trie\\wiki-100k.txt");
            archivo = new Scanner(file);
            pals = new String[cantPal];
            int i = 0;
            
            while(archivo.hasNext() && i<cantPal)
            {
                pals[i] = archivo.next();
                i++;
            }
            
        }
        catch(Exception e)
        {
            System.out.println("Crear Arreglo"+e);
            System.exit(-1);
            pals = new String[0];
        }
        
        return pals;
    }
    
    private static  boolean busca(String pal, boolean[] arreg)
    {
        int h1 = hash1(arreg.length, pal);
        if(!arreg[h1])
        {
            return false;
        }
        else
        {
            int h2 = hash2(arreg.length, pal);
            return arreg[h2];
        }
    }
    
    private static Character[] generarAbecedario()
    {

        Character[] abc = new Character[223];

 

        for(int i= 33; i<= 254;i++)

        {

            abc[i-33]=(char)i;

        }

        return abc;

    }
    
    
    public static int hash1(int tamaño, String dato)
    {
        Character[] arr = generarAbecedario();
        String dat = dato.toString();
        int resp;
        
        int i = 0;
        int cont = 0;
        int e;
        while(dat.length()> i && i<5)
        {
            e = 0;
            while(dat.charAt(i)!= arr[e])
            {
                e++;
            }
            cont =  cont + e;
            i++;
        }
        
        if(cont % 2 == 0)
        {
            resp = (cont * tamaño)-cont;
            resp = resp - dat.length();
            resp = resp % tamaño;
        }
        else
        {
            if(cont % 10 == 0)
            {
                int mitad = tamaño /2;
                if(cont % 2 == 0)
                {
                    resp = (mitad + cont - dat.length())% tamaño;
                }
                else
                {
                    resp = (mitad - cont + dat.length())% tamaño;
                }
            }
            resp = (cont - dat.length()) % tamaño;
        }
                
        return resp;
    }
    
    public static int hash2(int tamaño, String dato)
    {
        Character[] arr = generarAbecedario();
        String dat = dato.toString();
        int resp;
        
        int i = 0;
        int cont = 1;
        int e;
        while(dat.length()> i && i<6)
        {
            e = 0;
            while(dat.charAt(dat.length()-i-1)!= arr[e])
            {
                e++;
            }
            cont =  cont + e;
            i++;
        }
        
        if(cont % 2 == 0)
        {
            resp = (cont * tamaño)-cont;
            resp = resp - dat.length();
            resp = resp % tamaño;
        }
        else
        {
            if(cont % 10 == 0)
            {
                int mitad = tamaño /2;
                if(cont % 2 == 0)
                {
                    resp = (mitad + cont - dat.length())% tamaño;
                }
                else
                {
                    resp = (mitad - cont + dat.length())% tamaño;
                }
            }
            else
            {
                resp = (cont - dat.length()) % tamaño;
            }
        }
                
        return Math.abs(resp);
    }
    
}

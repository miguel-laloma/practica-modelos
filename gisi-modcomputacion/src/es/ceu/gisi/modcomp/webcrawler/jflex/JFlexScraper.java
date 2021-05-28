package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Esta clase encapsula toda la lógica de interacción con el parser HTML.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class JFlexScraper {
    
    ArrayList<String> enlacesA = new ArrayList<String>();
    ArrayList<String> enlacesIMG = new ArrayList<String>();
    Stack<String> etiquetasAbiertas = new Stack<String>();
    public boolean malBalanceado = false;
    

    HTMLParser analizador;

    /**
     * Este método actúa como un Autómata y se encarga de leer el código de la
     * página web y obtener sus hiperenlaces e imágenes.
     *
     * @Param fichero fichero con el contenido de la página web.
     */
    public JFlexScraper(File fichero) throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);

    try{
        int estado = 0;
        Token tk = analizador.nextToken();
        
        boolean etiquetaA = false;
        boolean etiquetaIMG = false;
        boolean valorHREF = false;
        boolean valorSRC = false;
 
        System.out.println("ejecución");
        
        while(tk != null){
            switch (estado){
                case 0:
                    if(tk.getTipo() == Tipo.OPEN){
                        estado = 1;
                    } else {
                        estado = 0;
                    }
                    break;
                case 1:
                    if(tk.getTipo() == Tipo.PALABRA){
                        estado = 2;
                        etiquetasAbiertas.push(tk.getValor().toLowerCase());
                        if(tk.getValor().equalsIgnoreCase("a")){
                            etiquetaA = true;
                        }else if(tk.getValor().equalsIgnoreCase("img")){
                            etiquetaIMG = true;
                        }
                        
                    }else if(tk.getTipo() == Tipo.SLASH){
                        estado = 6;
                    }
                    break;
                case 2:
                    if(tk.getTipo() == Tipo.PALABRA){
                        estado = 3;
                        if(etiquetaA){
                            if(tk.getValor().equalsIgnoreCase("href")){
                                valorHREF = true;
                            }
                        }else if (etiquetaIMG){
                            if(tk.getValor().equalsIgnoreCase("src")){
                                valorSRC = true;
                            }
                        }
                    }else if(tk.getTipo() == Tipo.SLASH){
                        estado = 5;
                        etiquetasAbiertas.pop();
                    }else if(tk.getTipo() == Tipo.CLOSE){
                        estado = 0;
                    }
                    break;
                case 3:
                    if(tk.getTipo() == Tipo.IGUAL){
                        estado = 4;
                    }
                    break;
                case 4:
                    if(tk.getTipo() == Tipo.VALOR){
                        estado = 2;
                        if(valorHREF){
                            enlacesA.add(tk.getValor());
                            etiquetaA = false;
                            valorHREF = false;
                        }else if(valorSRC){
                            enlacesIMG.add(tk.getValor());
                            etiquetaIMG = false;
                            valorSRC = false;
                        }
                    }
                    break;
                case 5:
                    if(tk.getTipo() == Tipo.CLOSE){
                        estado = 0;
                    }
                    break;
                case 6:
                    if(tk.getTipo() == Tipo.PALABRA){
                        estado = 7;
                        if(tk.getValor().equalsIgnoreCase(etiquetasAbiertas.peek())){
                            etiquetasAbiertas.pop();
                        }else{
                            malBalanceado = true;
                        }
                    }
                    break;
                case 7:
                    if(tk.getTipo() == Tipo.CLOSE){
                        estado = 0;
                    }
            }
            tk = analizador.nextToken();
        }
    }catch (IOException e){
        System.out.println("Error al leer el fichero");
    }
    }

    /*
     * Método encargado de recolectar los enlaces en el fichero html.
     */
    public ArrayList<String> obtenerHiperenlaces() throws IOException {
        System.out.println("Enlaces -> " + enlacesA.size());
        FileWriter fw = new FileWriter("enlaces_jflex.txt");

        for (String link : enlacesA) {
            fw.write(link);
        }
        
        return enlacesA;
    }

    /*
     * Método encargado de recolectar los enlaces en el fichero html.
     */
    public ArrayList<String> obtenerHiperenlacesImagenes() throws IOException {
        System.out.println("Enlaces a imágenes -> " + enlacesIMG.size());
        FileWriter fw = new FileWriter("enlacesIMG_jflex.txt");

        for (String link : enlacesIMG) {
            fw.write(link);
        }

        return enlacesIMG;
    }

    /*
     * Método encargado de indicar si la construcción del documento es adecuada.
     */
    public boolean esDocumentoHTMLBienBalanceado() {
        System.out.print("El documento está balanceado correctamente: ");
        //return !(malBalanceado || etiquetasAbiertas.empty());
        System.out.println(malBalanceado);
        System.out.println(etiquetasAbiertas.empty());
        System.out.println(etiquetasAbiertas);
        return !malBalanceado && etiquetasAbiertas.empty();
    }
}

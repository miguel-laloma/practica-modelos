package es.ceu.gisi.modcomp.webcrawler.jflex.test;

import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import es.ceu.gisi.modcomp.webcrawler.jflex.HTMLParser;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

/**
 *
 * @author laloma
 */
public class JFlexScraperTest {

    private final static String PATH_PRUEBAS = new java.io.File("").getAbsolutePath()
            + "/test/es/ceu/gisi/modcomp/webcrawler/jflex/test/";

    private File ficheroPrueba1 = new File(PATH_PRUEBAS + "prueba1.html");
    private File ficheroPrueba2 = new File(PATH_PRUEBAS + "prueba2.html");
    private File ficheroPrueba3 = new File(PATH_PRUEBAS + "prueba3.html");

    @Test (expected = FileNotFoundException.class)
    public void existeElFichero() throws FileNotFoundException {
        JFlexScraper jfs = new JFlexScraper(new File("ficheroInexistente.txt"));
    }

    @Test
    public void obtieneEnlaces() throws FileNotFoundException {
        JFlexScraper jfs = new JFlexScraper(ficheroPrueba2);
        ArrayList<String> enlaces = new ArrayList<String>();
        enlaces.add("http://www.bbc.co.uk");
        assertEquals(enlaces, jfs.obtenerHiperenlaces());
    }

    @Test
    public void obtieneImagenes() throws FileNotFoundException {
        JFlexScraper jfs = new JFlexScraper(ficheroPrueba2);
        ArrayList<String> imagenes = new ArrayList<String>();
        imagenes.add("brushedsteel.jpg");
        assertEquals(imagenes, jfs.obtenerHiperenlacesImagenes());
    }

    @Test
    public void malBalanceado() throws FileNotFoundException {
        JFlexScraper jfs = new JFlexScraper(ficheroPrueba3);
        assertFalse(jfs.esDocumentoHTMLBienBalanceado());
    }

    @Test
    public void bienBalanceado() throws FileNotFoundException {
        JFlexScraper jfs = new JFlexScraper(ficheroPrueba2);
        assertTrue(jfs.esDocumentoHTMLBienBalanceado());
    }
}

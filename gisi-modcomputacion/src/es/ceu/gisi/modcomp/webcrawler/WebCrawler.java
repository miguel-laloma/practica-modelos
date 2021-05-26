package es.ceu.gisi.modcomp.webcrawler;

import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import es.ceu.gisi.modcomp.webcrawler.jsoup.JsoupScraper;
import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Esta aplicación contiene el programa principal que ejecuta ambas partes del
 * proyecto de programación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class WebCrawler {

    public static void main(String[] args) throws Exception {
        // Deberá inicializar JFlexScraper con el fichero HTML a analizar
        // Y creará un fichero con todos los hiperenlaces que encuentre.
        // También deberá indicar, mediante un mensaje en pantalla que
        // el fichero HTML que se ha pasado está bien balanceado.

        JFlexScraper jfs = new JFlexScraper(new File("web.html"));
        
        System.out.println("\n JFlex Scrapper \n----------------");
        //FileNotFoundException
        jfs.obtenerHiperenlaces();
        jfs.obtenerHiperenlacesImagenes();
        System.out.println(jfs.esDocumentoHTMLBienBalanceado());

        // Deberá inicializar JsoupScraper con la DIRECCIÓN HTTP de una página
        // web a analizar. Creará un fichero con todos los hiperenlaces que
        // encuentre en la página web. También obtendrá estadísticas de uso 
        // de las etiquetas HTML más comunes: a, br, div, li, ul, p, span, table, td, tr

        JsoupScraper jss = new JsoupScraper(new URL("https://www.acb.com/"));
        //JsoupScraper jss = new JsoupScraper(new URL("https://twitter.com/explore"));
        //MalformedURLException
        //IOException
        
        System.out.println("\n\n JSoup Scrapper \n----------------");

        jss.obtenerHiperenlaces();
        jss.obtenerHiperenlacesImagenes();
        System.out.println("Estadísticas de uso de etiquetas:" + 
                                "\n - a: " + jss.estadisticasEtiqueta("a") +
                                "\n - br: " + jss.estadisticasEtiqueta("br") +
                                "\n - liv: " + jss.estadisticasEtiqueta("liv") +
                                "\n - li: " + jss.estadisticasEtiqueta("li") +
                                "\n - ul: " + jss.estadisticasEtiqueta("ul") +
                                "\n - p: " + jss.estadisticasEtiqueta("p") +
                                "\n - span: " + jss.estadisticasEtiqueta("span") +
                                "\n - table: " + jss.estadisticasEtiqueta("table") +
                                "\n - td: " + jss.estadisticasEtiqueta("td") +
                                "\n - tr: " + jss.estadisticasEtiqueta("tr") + "\n");
        //System.out.println(jss.obtenerContenidoImg());


    }
}

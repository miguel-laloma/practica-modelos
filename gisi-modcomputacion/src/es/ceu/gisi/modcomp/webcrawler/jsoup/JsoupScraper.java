/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ceu.gisi.modcomp.webcrawler.jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;

/**
 * Esta clase encapsula toda la lógica de interacción con el analizador Jsoup.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class JsoupScraper {

    private final Document doc;

    /**
     * Este constructor crea un documento a partir de la URL de la página web a
     * analizar.
     *
     * @param url Una URL que apunte a un documento HTML (p.e.
     *            http://www.servidor.com/index.html)
     */
    public JsoupScraper(URL url) throws IOException {
        // La variable deberá inicializarse de alguna manera utilizando una URL...
        // De momento, se inicializa a null para que compile...
        doc = Jsoup.connect(url.toString()).get();
    }

    /**
     * Este constructor crea un documento a partir del contenido HTML del String
     * que se pasa como parámetro.
     *
     * @param html Un documento HTML contenido en un String.
     */
    public JsoupScraper(String html) throws IOException {
        doc = Jsoup.parse(html);
    }

    /**
     * Realiza estadísticas sobre el número de etiquetas de un cierto tipo.
     *
     * @param etiqueta La etiqueta sobre la que se quieren estadísticas
     *
     * @return El número de etiquetas de ese tipo que hay en el documento HTML
     */
    public int estadisticasEtiqueta(String etiqueta){
        Elements links = doc.getElementsByTag(etiqueta);
        return links.size();
    }

    /**
     * Obtiene todos los hiperenlaces que se encuentran en el documento creado.
     *
     * @return Una lista con todas las URLs de los hiperenlaces
     */
    public List<String> obtenerHiperenlaces() throws IOException {
        ArrayList<String> enlacesA = new ArrayList<String>();
        FileWriter fw = new FileWriter("enlaces_jsoup.txt");

        Elements links = doc.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            enlacesA.add(linkHref);
        }

        for (String link : enlacesA) {
            fw.write(link);
        }

        System.out.println("Enlaces -> " + enlacesA.size());
        return enlacesA;
    }

    /**
     * Obtiene todos los hiperenlaces de las imágenes incrustadas.
     *
     * @return Una lista con todas las URLs de los hiperenlaces
     */
    public List<String> obtenerHiperenlacesImagenes() throws IOException {
        ArrayList<String> enlacesIMG = new ArrayList<String>();
        FileWriter fw = new FileWriter("enlacesIMG_jflex.txt");

        Elements links = doc.getElementsByTag("img");
        for (Element link : links) {
            String linkSrc = link.attr("src");
            enlacesIMG.add(linkSrc);
        }

        for (String link : enlacesIMG) {
            fw.write(link);
        }

        System.out.println("Enlaces a imágenes -> " + enlacesIMG.size());
        return enlacesIMG;
    }


    /**
     * Obtiene la imagen a la que hace referencia LA PRIMERA etiqueta IMG que
     * encontramos.
     *
     * @return El nombre (o ruta) de la primera imagen insertada en el documento
     *         HTML.
     */
    public String obtenerContenidoImg() {
        Element elemento = doc.select("IMG").first();
        String imagen = elemento.attr("src");
        return imagen;
    }
}

package com.maximdoronin.rssnews;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by maximdoronin on 12/26/17.
 */

public class RssXmlParser {
    static final String ITEM = "item";
    static final String TITLE = "title";
    static final String LINK = "link";
    static final String DESCRIPTION = "description";
    static final String PUBDATE = "pubDate";

    public static List<RssItem> parse(String subject) {
        List<RssItem> items = new ArrayList<>();
        try {
            URL url = new URL("http://news.yandex.ru/" + subject + ".rss");

            DateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(url.openStream());
            document.getDocumentElement().normalize();
            NodeList itemList = document.getElementsByTagName(ITEM);
            for (int i = 0; i < itemList.getLength(); i++) {
                Node node = itemList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element element = (Element) node;

                items.add(new RssItem(
                        element.getElementsByTagName(TITLE).item(0).getChildNodes()
                                .item(0).getNodeValue(),
                        element.getElementsByTagName(LINK).item(0).getChildNodes()
                                .item(0).getNodeValue(),
                        element.getElementsByTagName(DESCRIPTION).item(0).getChildNodes()
                                .item(0).getNodeValue(),
                        format.parse(element.getElementsByTagName(PUBDATE).item(0).getChildNodes()
                                .item(0).getNodeValue()),
                        NewsSubject.valueOf(subject)
                ));
            }
        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            Log.e("RSSNEWS", e.toString());
        }
        return items;
    }
}

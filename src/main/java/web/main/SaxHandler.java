package web.main;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    private static String CLASSNAME = "class";

    private String element = null;
    private Object object = null;

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if(qName != CLASSNAME) {
            element = qName;
        } else {
            String className = attributes.getValue(0);
            object = ReflectionHelper.createInstance(className);
        }
    }

    public void endElement(String uri, String localName, String qName) {
        element = null;
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if(element != null) {
            String value = new String(ch, start, length);
            ReflectionHelper.setFieldValue(object, element, value);
        }
    }

    Object getObject() {
        return object;
    }
}

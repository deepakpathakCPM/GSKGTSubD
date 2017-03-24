package com.example.deepakp.gskgtsubd.xmlHandler;

import com.example.deepakp.gskgtsubd.gettersetter.FailureGetterSetter;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class FailureXMLHandler extends DefaultHandler {
    private String elementValue;
    private FailureGetterSetter failureGetterSetter = null;

    public FailureGetterSetter getFailureGetterSetter() {
        return failureGetterSetter;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        failureGetterSetter = new FailureGetterSetter();
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException {
        super.characters(ch, start, length);
        elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("STATUS")) {
            failureGetterSetter.setStatus(elementValue);
        } else if (qName.equals("ERRORMSG")) {
            failureGetterSetter.setErrorMsg(elementValue);
        }
    }
}

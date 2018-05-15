package com.example.he.networktest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by he on 2016/5/3.
 */
public class ContentHandler extends DefaultHandler{
    private String nodename;
    private StringBuilder id;
    private StringBuilder name;

    @Override
    public void startDocument() throws SAXException {
        id=new StringBuilder();
        name=new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodename=localName;//记录当前结点名
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("id".equals(nodename)){
            id.append(ch,start,length);
        }else if ("name".equals(nodename)){
            name.append(ch,start,length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       if ("app".equals(localName)){
           Log.i("ContentHandler","id is"+id.toString().trim());
           Log.i("ContentHandler","name is"+name.toString().trim());
           //清空StringBuilder
           id.setLength(0);
           name.setLength(0);
       }

    }

    @Override
    public void endDocument() throws SAXException {

    }
}

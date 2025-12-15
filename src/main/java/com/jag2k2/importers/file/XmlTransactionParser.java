package com.jag2k2.importers.file;

import com.jag2k2.importers.ofx.OfxParser;
import com.jag2k2.util.Transactions;
import com.jag2k2.tuples.Transaction;
import com.jag2k2.util.TransactionsImpl;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

public class XmlTransactionParser {
    static public String convertFileToProperXMLString(File file){
        StringBuilder contentsAsXml = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while((line = reader.readLine()) != null) {
                contentsAsXml.append(SgmlToXmlConverter.parseLine(line));
            }
            reader.close();
        } catch (Exception ex) {ex.printStackTrace();}
        return contentsAsXml.toString();
    }

    static public Transactions<Transaction> parseTransactions(String fileString){
        Transactions<Transaction> transactions = new TransactionsImpl<>();
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = builder.parse(new InputSource(new StringReader(fileString)));
            transactions = OfxParser.parseTransactions(xmlDocument);
        } catch (Exception ex) {ex.printStackTrace();}
        return transactions;
    }
}

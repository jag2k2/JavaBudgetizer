package flb.importers.file;

import flb.importers.ofx.OfxParser;
import flb.tuples.Transaction;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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
        } catch (Exception ex) {ex.printStackTrace();}
        return contentsAsXml.toString();
    }

    static public List<Transaction> parseTransactionsFromXml(String xmlString){
        List<Transaction> transactions = new ArrayList<>();
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = builder.parse(new InputSource(new StringReader(xmlString)));
            transactions = OfxParser.parseTransactions(xmlDocument);
        } catch (Exception ex) {ex.printStackTrace();}
        return transactions;
    }
}

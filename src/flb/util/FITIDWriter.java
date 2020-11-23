package flb.util;

import flb.databases.AbstractDatabase;
import flb.databases.ProductionDatabase;
import flb.importers.QfxImporter;
import flb.importers.file.FileLoader;
import flb.importers.file.UserChoosesFileLoader;
import flb.tuples.Transaction;
import javax.swing.*;
import java.sql.*;

public class FITIDWriter {
    public static void main(String[] args) {

        AbstractDatabase database = new ProductionDatabase();
        database.connect();

        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        FileLoader fileLoader = new UserChoosesFileLoader(fileChooser, frame);
        QfxImporter qfxImporter = new QfxImporter(fileLoader);

        for(Transaction transaction : qfxImporter.getTransactionsToImport()){
            String update = "UPDATE transactions " +
                    //"SET reference = '-1'";
                    "SET reference = '$ref' " +
                    "WHERE date = '$date' AND type = 'banking' AND description LIKE '$desc' AND amount LIKE $amt";
            update = update.replace("$ref", transaction.getReference());
            update = update.replace("$date", transaction.getDateString());
            update = update.replace("$desc", transaction.getDescriptionWithEscapes() + "%");
            update = update.replace("$amt", String.format("%.2f", transaction.getAmount()));
            update = update.replace("$bal", String.format("%.2f", transaction.getBalance()));
            database.executeUpdate(update);
            System.out.println(update);
        }

        String query = "SELECT reference, COUNT(*) as count " +
                "FROM transactions " +
                "GROUP BY reference " +
                "HAVING COUNT(*) > 1 " +
                "ORDER BY count DESC";

        ResultSet results = database.executeQuery(query);
        String duplicate = "";
        try{
            results.next();
            duplicate = results.getString(1);
            System.out.println(duplicate);
        }catch (Exception ex) {ex.printStackTrace();}

        query = "SELECT * " +
                "FROM transactions " +
                "WHERE reference = '" + duplicate + "'";
        results = database.executeQuery(query);
        try{
            while(results.next()){
                System.out.println(results.getDate("date") + results.getString("description"));
            }
        } catch (Exception ex) {ex.printStackTrace();}


        database.close();
    }
}

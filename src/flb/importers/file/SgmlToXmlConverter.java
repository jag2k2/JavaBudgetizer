package flb.importers.file;

public class SgmlToXmlConverter {

    static protected boolean detectMarkup(String line){
        return(line.matches(" *<+.*"));
    }

    static protected boolean detectSelfClosingTag(String line){
        return line.matches(" *<[^/].*>.+");
    }

    static protected String openTagNameExtractor(String line){
        int indexOfOpeningAngleBracket = line.indexOf("<");
        int indexOfClosingAngleBracket = line.indexOf(">");
        return line.substring(indexOfOpeningAngleBracket + 1 , indexOfClosingAngleBracket);
    }

    static protected String encloseSelfClosingTag(String line){
        if(detectSelfClosingTag(line)){
            return line + "</" + openTagNameExtractor(line) + ">";
        }
        return line;
    }

    static public String parseLine(String line){
        if(!detectMarkup(line)) {
            return "";
        }
        if(detectSelfClosingTag(line)) {
            line = encloseSelfClosingTag(line);
        }
        return line + "\n";
    }
}


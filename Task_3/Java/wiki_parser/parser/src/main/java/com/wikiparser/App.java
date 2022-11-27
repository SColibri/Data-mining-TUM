package com.wikiparser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    // private variables parser object
    static boolean insideText = false;
    static String bufferText = "";
    
    // Main
    public static void main( String[] args )
    {
        // -------------------------------------------
        //                  FILE PATHS
        // -------------------------------------------
        String pathToWiki = "/home/ubuntu/enwiki-20220101-pages-articles-multistream/enwiki-20220101-pages-articles-multistream.xml";
        String outputWiki = "/home/ubuntu/resources/wiki.txt";

        // -------------------------------------------
        //               Word dictionary
        // -------------------------------------------
        FileOutputStream outputFile;
        try {
            outputFile = new FileOutputStream(outputWiki, false);
        } catch (FileNotFoundException e) {
            return;
        }
        
        
        try (Stream<String> stream = Files.lines(Paths.get(pathToWiki))) {

            stream.forEach(line -> 
            {
                // check what type of region we are at
                if(contains_textTag(line))
                {
                    insideText = true;
                }
                
                // if inside text region, copy text
                if(insideText) 
                    bufferText += extract_text(line);

                // check for end tag
                if(conatins_endTextTag(line))
                {
                    insideText = false;
                }

                // Put buffer into file
                if(bufferText.length() > 1000)
                {
                    bufferText += "\n";
                    try {
                        outputFile.write(bufferText.getBytes());
                    } catch (Exception e) {
                        // not much to do
                    }
                    bufferText = "";
                }
            });

            // Add remaining buffer content
            outputFile.write(bufferText.getBytes());
            bufferText = "";
            outputFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

        

    }

    private static boolean contains_textTag(String line)
    {
        return line.contains("<text");
    }

    private static boolean conatins_endTextTag(String line)
    {
        return line.contains("</text>");
    }

    private static String extract_text(String line)
    {
        int startIndex = line.indexOf("<text");
        int startIndexE = line.indexOf(">");
        int endIndexE = line.indexOf("</text>");

        String result = "";

        // check if we have a end index
        if(endIndexE == -1) endIndexE = line.length() - 1;
        if(startIndexE == -1) startIndexE = 0;

        // if starting index is the same then discard
        if(startIndexE > endIndexE) return result;

        // get starting index
        if(startIndex == -1) startIndex = 0;
        else startIndex = startIndexE;

        // copy text only
        result = line.substring(startIndexE, endIndexE).replace("\"", " ")
                                                       .replace("\'", " ")
                                                       .replace("[", " ")
                                                       .replace("]", " ")
                                                       .replace("{", " ")
                                                       .replace("}", " ")
                                                       .replace("|", " ")
                                                       .replace(",", " ")
                                                       .replace("=", " ");

        return result;
    }
}

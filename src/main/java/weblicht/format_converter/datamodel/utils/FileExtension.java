/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.utils;

import java.io.File;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author felahi
 */
public class FileExtension {

    public static File renameFileExtension(String source, String newExtension) {
        String target;
        String currentExtension = getFileExtension(source);

        if (currentExtension.equals("")) {
            target = source + "." + newExtension;
        } else {
            target = source.replaceFirst(Pattern.quote("."
                    + currentExtension) + "$", Matcher.quoteReplacement("." + newExtension));

        }
        return new File(target);
    }

    public static String getFileExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');
        if (i > 0 && i < f.length() - 1) {
            ext = f.substring(i + 1);
        }
        return ext;
    }

    public static boolean checkFileExtension(File inputFile, Set fileExtensions)  {
        String fileExtension = FilenameUtils.getExtension(inputFile.getName()).toLowerCase();
        if (fileExtensions.contains(fileExtension)) {
            return true;
        } 
        return false;
    }
    /*public static boolean checkFileExtension(File inputFile, Set fileExtensions) throws IOException {
        String fileExtension = FilenameUtils.getExtension(inputFile.getName()).toLowerCase();
        if (!fileExtensions.contains(fileExtension)) {
            throw new IOException("File extension is not correct!!");
        } else {
            return true;
        }
    }*/
}

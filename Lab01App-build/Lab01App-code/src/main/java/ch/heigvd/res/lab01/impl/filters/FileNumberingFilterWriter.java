package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    // Numéro de ligne
    private int nbLine = 1;
    // Permet de savoir si c'est la 1ère ligne/caractère envoyé ou non
    private boolean first = true;
    // Permet de stocker le caractère précédent
    private int previous = 'a';
    
    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        char currentChar;
        String tmp = "";
        
        // On fait un substring pour récupèrer la ligne en fonction de l'offset
        // et de la longueur
        str = str.substring(off, off + len);
        
        // On parcourt la chaîne caractère par caractère
        for(int i = 0; i < str.length(); i++) {
            
            // On récupère le caractère courant
            currentChar = str.charAt(i);
            
            // Au 1er passsage, on ajoute le numéro de ligne
            if(first) {
                tmp += nbLine++ + "\t" + currentChar;
                first = false;
            }
            else {
                // Linux
                // On ajoute le \n et le numéro de ligne
                if(currentChar == '\n') {
                    tmp += "\n" + nbLine++ + "\t";
                }
                // Mac et Windows
                else if(currentChar == '\r') {
                    // Windows
                    // On check si le caractère suivant est un \n et on ajoute
                    // \r\n et le numéro de ligne.
                    if(i + 1 < str.length() && str.charAt(i + 1) == '\n') {
                        tmp += "\r\n" + nbLine++ + "\t";
                        // On passe au caractère suivant
                        i++;
                    }
                    // Mac
                    // On ajoute le \r et le numéro de ligne
                    else {
                        tmp += "\r" + nbLine++ + "\t";
                    }
                }
                // Sinon on ajoute le caractère normalement
                else {
                    tmp += currentChar;
                }
            }
        }
        
        // On envoie la chaîne au flux
        super.write(tmp, 0, tmp.length());
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        super.write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        // Lors du 1er passage, on écrit directement le numéro de ligne
        if(first) {
            super.write((char) ('0' + nbLine++));
            super.write('\t');
            super.write(c);
            first = false;
        }
        else{
            // Linux
            // On test si le caractère en cours est un '\n' et 
            // qu'avant il n'y avait pas de '\r'
            if(c == '\n' && previous != '\r') {
                // On écrit le '\n' et le numéro de ligne
                super.write('\n');
                super.write((char) ('0' + nbLine++));
                super.write('\t');
            }
            // Mac
            // On test si le caractère en cours n'est pas un '\n' et que celui
            // d'avant est un '\r'
            else if(c != '\n' && previous == '\r') {
                // On écrit le numéro de ligne (le '\r' a été envoyé avant)
                super.write((char) ('0' + nbLine++));
                super.write('\t');
                super.write(c);
                previous = 'a';
            }
            // Windows
            // On test si le caractère en cours est un '\n' et que celui
            // d'avant est un '\r'
            else if(c == '\n' && previous == '\r') {
                // On écrit le '\n' et le numéro de ligne (le '\r' a été
                // envoyé avant)
                super.write('\n');
                super.write((char) ('0' + nbLine++));
                super.write('\t');
                previous = 'a';
            }
            // Sinon on écrit le caractère normal
            else{
                super.write(c);
            }
            
            // On stocke le caractère précédent si c'est un '\r'
            if(c == '\r') {
                previous = '\r';
            }
        }
    }

}

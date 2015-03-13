package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
      // Si le texte ne contient pas de \n ou de \r, on retourne un résutlat
      // en conséquence
      if(!lines.contains("\n") && !lines.contains("\r")) {
          return new String[] { "", lines };
      }
      
      char currentChar;
      String first = "", second = "";
      
      int i;
      
      // En permier, on parcourt les caractères du texte. Lorsqu'on tombe sur
      // un retour chariot (linux, mac ou windows) on l'ajoute à la chaîne
      // first et on sort de la boucle.
      for(i = 0; i < lines.length(); i++) {
          currentChar = lines.charAt(i);
          
          // Linux
          if(currentChar == '\n') {
              first += "\n";
              // On passe au caractère suivant
              i++;
              break;
          }
          else if(currentChar == '\r') {
                // Windows
                if(i + 1 < lines.length() && lines.charAt(i + 1) == '\n') {
                    first += "\r\n";
                    // On passe deux caractères plus loin
                    i += 2;
                    break;
                }
                // Mac
                else {
                    // On passe au caractère suivant
                    first += "\r";
                    i++;
                    break;
                }
          }
          // Sinon on ajoute simplement le caractère à la chaîne
          else {
              first += currentChar;
          }
      }
      
      // Dans cette boucle, on recommence à l'emplacement suivant le retour
      // chariot et on ajoute les caractères suivants (reste de la chaîne) dans
      // second.
      for(int j = i; j < lines.length(); j++) {
          second += lines.charAt(j);
      }
      
      return new String[] { first, second };
  }

}

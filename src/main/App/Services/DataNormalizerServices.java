package Services;

import Interfaces.IDataNormalizerServices;

/**
 * Servicio de normalización de cadenas de texto.
 *
 * Esta clase se encarga de transformar cadenas de entrada para que cada palabra
 * comience con mayúscula y las demás letras sean minúsculas. Además, respeta
 * guiones dentro de las palabras, capitalizando cada subpalabra.
 * <p>
 * Ejemplo: "juan-perez de la torre" → "Juan-Perez De La Torre"
 * </p>
 *
 * @author juanserealpe
 */
public class DataNormalizerServices implements IDataNormalizerServices {

    /**
     * Normaliza una cadena de texto.
     *
     * @param input Cadena de texto a normalizar.
     * @return Cadena normalizada con cada palabra capitalizada y subpalabras tras guiones también.
     */
    @Override
    public String normalizeString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Dividir la cadena en palabras separadas por espacios
        String[] words = input.trim().split("\\s+");

        for (int i = 0; i < words.length; i++) {
            // Dividir palabras por guiones para capitalizar subpalabras
            String[] subWords = words[i].split("-");
            for (int j = 0; j < subWords.length; j++) {
                if (subWords[j].length() > 0) {
                    // Convertir la primera letra a mayúscula y el resto a minúscula
                    subWords[j] = subWords[j].substring(0, 1).toUpperCase()
                            + subWords[j].substring(1).toLowerCase();
                }
            }
            // Unir subpalabras nuevamente con guion
            words[i] = String.join("-", subWords);
        }

        // Unir palabras nuevamente con espacio
        return String.join(" ", words);
    }

}

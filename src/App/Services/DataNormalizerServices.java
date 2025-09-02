package App.Services;

import App.Interfaces.IDataNormalizerServices;

public class DataNormalizerServices implements IDataNormalizerServices {

    @Override
    public String normalizeString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.trim().split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // manejar guiones dentro de la palabra
            String[] subWords = words[i].split("-");
            for (int j = 0; j < subWords.length; j++) {
                if (subWords[j].length() > 0) {
                    subWords[j] = subWords[j].substring(0, 1).toUpperCase()
                            + subWords[j].substring(1).toLowerCase();
                }
            }
            words[i] = String.join("-", subWords);
        }

        return String.join(" ", words);
    }

}

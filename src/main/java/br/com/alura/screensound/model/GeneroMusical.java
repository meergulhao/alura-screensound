package br.com.alura.screensound.model;

public enum GeneroMusical {
    ROCK,
    POP,
    RAP,
    SAMBA,
    SERTANEJO;

    public static GeneroMusical fromString(String text) {
        for (GeneroMusical generoMusical : GeneroMusical.values()) {
            if (generoMusical.toString().equalsIgnoreCase(text)) {
                return generoMusical;
            }
        }
        throw new IllegalArgumentException("Nenhum gênero musical encontrado para a palavra " + text);
    }
}

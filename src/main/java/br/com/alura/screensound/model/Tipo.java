package br.com.alura.screensound.model;

public enum Tipo {
    SOLO,
    DUPLA,
    BANDA,
    GRUPO;

    public static Tipo fromString(String text) {
        for (Tipo tipo : Tipo.values()) {
            if (tipo.toString().equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo encontrado para a palavra " + text);
    }
}

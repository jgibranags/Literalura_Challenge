package com.gibran.Literalura.Model;

public enum Idioma {
    ESPANOL("es", "español"),
    INGLES("en", "ingles"),
    FRANCES("fr", "frances"),
    PORTUGUES("pt", "portugues");

    private String diminutivo;
    private String completo;

    Idioma(String diminutivo, String completo) {
        this.diminutivo = diminutivo;
        this.completo = completo;
    }

    public static Idioma fromDiminutivo(String text) {
        // Si el texto tiene corchetes, asumimos que es un array
        if (text.startsWith("[") && text.endsWith("]")) {
            // Quitamos los corchetes y separamos por comas
            String[] idiomas = text.substring(1, text.length() - 1).split(",");
            for (String idioma : idiomas) {
                // Buscamos cada idioma en el enum
                for (Idioma enumIdioma : Idioma.values()) {
                    if (enumIdioma.diminutivo.equalsIgnoreCase(idioma.trim())) {
                        return enumIdioma;
                    }
                }
            }
        } else {
            // Si no tiene corchetes, buscamos el texto directamente
            for (Idioma idioma : Idioma.values()) {
                if (idioma.diminutivo.equalsIgnoreCase(text)) {
                    return idioma;
                }
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }

    public static Idioma fromCompleto(String text){
        for (Idioma categoria : Idioma.values()){
            if (categoria.completo.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException(("Ninguna categoria encontrada: " + text));
    }

    public String getDiminutivo() {
        return diminutivo;
    }

    public void setDiminutivo(String diminutivo) {
        this.diminutivo = diminutivo;
    }

    public String getCompleto() {
        return completo;
    }

    public void setCompleto(String completo) {
        this.completo = completo;
    }
}

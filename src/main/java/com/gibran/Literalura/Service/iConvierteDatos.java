package com.gibran.Literalura.Service;

public interface iConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

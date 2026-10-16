package com.agrupamento.jhonatanTeles.exeptions;

public class LocalDateTimeExeption extends RuntimeException {
    public LocalDateTimeExeption(String message) {super(message);}
    public LocalDateTimeExeption(String message, Throwable cause) {super(message, cause);}
}

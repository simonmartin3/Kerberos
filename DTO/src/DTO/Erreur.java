package DTO;

import java.io.Serializable;



public class Erreur implements Serializable {
    public final static int ERREUR = 0;
    public final static int SUCCESS = 1;
    private String message;
    private int code;
    public Erreur( String message, int code) {
        this.message = message;
        this.code =code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Erreur{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

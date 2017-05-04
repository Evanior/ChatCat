package fr.imie.huard.chatcat;

import java.util.Date;

/**
 * Created by huard.cdi04 on 04/05/2017.
 */

public class Message {
    private long id;
    private String pseudo;
    private Date date;
    private String message;

    public Message(long id, String pseudo, Date date, String message) {
        this.id = id;
        this.pseudo = pseudo;
        this.date = date;
        this.message = message;
    }

    public Message(String pseudo, Date date, String message) {
        this.pseudo = pseudo;
        this.date = date;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

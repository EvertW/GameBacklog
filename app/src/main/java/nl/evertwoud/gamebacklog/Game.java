package nl.evertwoud.gamebacklog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game implements Serializable {

    static String[] statusStrings = new String[]{
            "Playing",
            "Want to play",
            "Stalled",
            "Dropped"
    };

    String title;
    String platform;
    Integer status;
    String notes;
    Date date;

    public Game(String pTitle, String pPlatform, Integer pStatus, String pNotes, Date pDate) {
        title = pTitle;
        platform = pPlatform;
        status = pStatus;
        notes = pNotes;
        date = pDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String pPlatform) {
        platform = pPlatform;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer pStatus) {
        status = pStatus;
    }

    public String getStatusString() {
        return statusStrings[status];
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String pNotes) {
        notes = pNotes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date pDate) {
        date = pDate;
    }

    public String getDateFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String[] getStatusStrings() {
        return statusStrings;
    }
}

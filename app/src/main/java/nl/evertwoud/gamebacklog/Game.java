package nl.evertwoud.gamebacklog;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Entity
public class Game implements Serializable {

    static String[] statusStrings = new String[]{
            "Playing",
            "Want to play",
            "Stalled",
            "Dropped"
    };

    @NonNull
    @PrimaryKey(autoGenerate = true)
    Integer id;
    String title;
    String platform;
    Integer status;
    String notes;
    Long date;

    public Game() {
    }

    public Game(String pTitle, String pPlatform, Integer pStatus, String pNotes, Long pDate) {
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
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal.getTime();
    }

    public void setDate(Date pDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        date = cal.getTimeInMillis();
    }

    public String getDateFormatted() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(cal.getTime());
    }

    public static String[] getStatusStrings() {
        return statusStrings;
    }
}

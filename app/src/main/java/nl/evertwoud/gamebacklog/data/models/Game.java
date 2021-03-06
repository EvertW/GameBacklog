package nl.evertwoud.gamebacklog.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nl.evertwoud.gamebacklog.R;


@Entity
public class Game implements Serializable {

    static Integer[] statusStrings = new Integer[]{
            R.string.status_playing,
            R.string.status_want_to_play,
            R.string.status_stalled,
            R.string.status_droppped
    };

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String title;
    public String platform;
    public Integer status;
    public String notes;
    public Long date;

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

    public String getStatusString(Context pContext) {
        return pContext.getString(statusStrings[status]);
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

    /**
     * Gets the date as a formatted string
     * @return formatted date string
     */
    public String getDateFormatted() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(cal.getTime());
    }


    /**
     * Returns a list of the available status strings.
     * @param pContext a context
     * @return the list of statuses
     */
    public static String[] getStatusStrings(Context pContext) {
        String[] list = new String[statusStrings.length];
        list[0] = pContext.getString(statusStrings[0]);
        list[1] = pContext.getString(statusStrings[1]);
        list[2] = pContext.getString(statusStrings[2]);
        list[3] = pContext.getString(statusStrings[3]);
        return list;
    }
}

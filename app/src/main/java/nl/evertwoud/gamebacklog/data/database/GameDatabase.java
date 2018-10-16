package nl.evertwoud.gamebacklog.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import nl.evertwoud.gamebacklog.data.database.dao.GameDao;
import nl.evertwoud.gamebacklog.data.models.Game;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDao dao();
}
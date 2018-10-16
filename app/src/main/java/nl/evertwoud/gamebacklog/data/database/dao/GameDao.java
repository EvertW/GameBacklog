package nl.evertwoud.gamebacklog.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nl.evertwoud.gamebacklog.data.models.Game;

@Dao
public interface GameDao {

    @Insert
    public void addGame(Game movies);

    @Query("SELECT * FROM Game WHERE id = :id")
    public Game getGameById(int id);

    @Query("SELECT * FROM Game")
    public List<Game> getAllGames();

    @Update
    public void updateGame(Game pGame);

    @Delete
    public void deleteGame(Game pGame);
}

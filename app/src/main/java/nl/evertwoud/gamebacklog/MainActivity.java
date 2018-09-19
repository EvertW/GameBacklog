package nl.evertwoud.gamebacklog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.ItemClickListener {

    private static final int NEW_GAME = 1;
    private static final int EDIT_GAME = 2;

    FloatingActionButton mFab;
    RecyclerView mRecyclerView;
    GameAdapter mAdapter;
    List<Game> mGames;
    private Game mEditingGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.backlog_recycler);
        mFab = findViewById(R.id.plus_button);

        init();
    }

    private void init() {
        if (getListFromPreferences() != null && !getListFromPreferences().isEmpty()) {
            mGames = getListFromPreferences();
        } else {
            mGames = new LinkedList<>();
        }

        setUpRecyclerView(mGames);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddGameActivity();
            }
        });
    }

    void goToAddGameActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, NEW_GAME);
    }

    void setUpRecyclerView(List<Game> games) {
        // set up the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GameAdapter(this, games);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Game game = mAdapter.getItem(position);
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("title", game.getTitle());
        intent.putExtra("platform", game.getPlatform());
        intent.putExtra("notes", game.getNotes());
        intent.putExtra("status", game.getStatus());
        startActivityForResult(intent, EDIT_GAME);
        mEditingGame = game;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.getExtras() == null) {
                return;
            }
            Bundle bundle = data.getExtras();

            switch (requestCode) {
                case NEW_GAME:
                    String title = bundle.getString("title");
                    String platform = bundle.getString("platform");
                    String notes = bundle.getString("notes");
                    int status = bundle.getInt("status");
                    mGames.add(new Game(title, platform, status, notes, new Date()));
                    break;
                case EDIT_GAME:
                    String edited_title = bundle.getString("title");
                    String edited_platform = bundle.getString("platform");
                    String edited_notes = bundle.getString("notes");
                    int edited_status = bundle.getInt("status");

                    mEditingGame.setTitle(edited_title);
                    mEditingGame.setPlatform(edited_platform);
                    mEditingGame.setNotes(edited_notes);
                    mEditingGame.setStatus(edited_status);
                    mEditingGame.setDate(new Date());
                    break;
            }
            storeListInPreferences(mGames);
            setUpRecyclerView(mGames);
        }
    }

    void storeListInPreferences(List<Game> pGames) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pGames);
        prefsEditor.putString("GAMES", json);
        prefsEditor.commit();
    }

    List<Game> getListFromPreferences() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("GAMES", "");
        Type type = new TypeToken<List<Game>>() {
        }.getType();
        List<Game> games = gson.fromJson(json, type);
        return games;
    }
}

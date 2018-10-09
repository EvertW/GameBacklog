package nl.evertwoud.gamebacklog;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.ItemClickListener {
    private static final String DATABASE_NAME = "game_db";
    private static final int NEW_GAME = 1;
    private static final int EDIT_GAME = 2;

    FloatingActionButton mFab;
    RecyclerView mRecyclerView;
    GameAdapter mAdapter;
    List<Game> mGames;
    private Game mEditingGame;

    private GameDatabase mGameDatabase;
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder,
                             int swipeDir) {
            //Get the swiped item and remove it from the list (async)
            int position = viewHolder.getAdapterPosition();
            doAsync(() -> {
                mGameDatabase.dao().deleteGame(mAdapter.getItem(position));
                runOnUiThread(() -> mAdapter.remove(position));
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.backlog_recycler);
        mFab = findViewById(R.id.plus_button);

        initializeDatabase();
        getAllGames();
        mFab.setOnClickListener(v -> goToAddGameActivity());
    }

    private void initializeDatabase() {
        mGameDatabase = Room.databaseBuilder(getApplicationContext(),
                GameDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    private void getAllGames() {
        doAsync(() -> {
            mGames = mGameDatabase.dao().getAllGames();
            runOnUiThread(() -> setUpRecyclerView(mGames));
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
            if (data == null || data.getExtras() == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            switch (requestCode) {
                case NEW_GAME:
                    String title = bundle.getString("title");
                    String platform = bundle.getString("platform");
                    String notes = bundle.getString("notes");
                    int status = bundle.getInt("status");
                    doAsync(() -> {
                        mGameDatabase.dao().addGame(new Game(title, platform, status, notes, System.currentTimeMillis()));
                        getAllGames();
                    });
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
                    doAsync(() -> {
                        mGameDatabase.dao().updateGame(mEditingGame);
                        getAllGames();
                    });
                    break;
            }
        }
    }

    void doAsync(Runnable pRunnable) {
        new Thread(pRunnable).start();
    }
}

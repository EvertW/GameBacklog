package nl.evertwoud.gamebacklog.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import nl.evertwoud.gamebacklog.R;
import nl.evertwoud.gamebacklog.data.models.Game;

public class AddActivity extends AppCompatActivity {

    EditText mAddTitle;
    EditText mAddPlatform;
    EditText mAddNotes;
    FloatingActionButton mSaveButton;
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        mAddTitle = findViewById(R.id.add_title);
        mAddPlatform = findViewById(R.id.add_platform);
        mAddNotes = findViewById(R.id.add_notes);
        mSpinner = findViewById(R.id.add_status);
        mSaveButton = findViewById(R.id.save_button);
        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fill data when in edit mode
        if (getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            mAddTitle.setText(bundle.getString("title"));
            mAddPlatform.setText(bundle.getString("platform"));
            mAddNotes.setText(bundle.getString("notes"));
            mSpinner.setSelection(bundle.getInt("status"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Initialization method
    private void init() {
        //Set up the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Game.getStatusStrings(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        //Add click listener which returns all the entered data.
        mSaveButton.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("title",mAddTitle.getText().toString());
            intent.putExtra("platform",mAddPlatform.getText().toString());
            intent.putExtra("notes", mAddNotes.getText().toString());
            intent.putExtra("status", mSpinner.getSelectedItemPosition());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}

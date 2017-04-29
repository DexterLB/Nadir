package org.qtrp.nadir.Activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.qtrp.nadir.CustomViews.AddRollDialogFragment;
import org.qtrp.nadir.CustomViews.RollAdapter;
import org.qtrp.nadir.Database.FilmRollDbHelper;
import org.qtrp.nadir.Database.Roll;
import org.qtrp.nadir.R;

import java.util.ArrayList;

public class ManageRollsActivity extends AppCompatActivity  implements AddRollDialogFragment.NoticeDialogListener {
    SharedPreferences prefereces;
    FloatingActionButton settingsButton;
    FloatingActionButton addButton;
    ListView rollsList;
    FilmRollDbHelper filmRollDbHelper;
    ArrayAdapter<Roll> adapter;
    DialogFragment addRollDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rolls);

        bindWidgets();
        loadUtils();
        setListeners();
        setDatasets();
    }

    private void setDatasets() {
        ArrayList<Roll> listItems = filmRollDbHelper.getRolls();

        adapter = new RollAdapter(this, listItems);
        rollsList.setAdapter(adapter);
    }

    private void bindWidgets() {
        //settingsButton = (FloatingActionButton) findViewById(R.id.settingsButton);
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        rollsList = (ListView) findViewById(R.id.rollsList);
    }

    private void loadUtils() {
        prefereces = PreferenceManager.getDefaultSharedPreferences(this);
        filmRollDbHelper = new FilmRollDbHelper(this);
        addRollDialog = new AddRollDialogFragment();

    }

    private void setListeners(){
//        settingsButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ManageRollsActivity.this, SettingsActivity.class);
//                startActivity(intent);
//            }
//        });

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addRollDialog.show(getFragmentManager(), "Add roll");
            }
        });

        rollsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ManageRollsActivity.this, RollActivity.class);
                intent.putExtra("roll_id", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        adapter.clear();
        adapter.addAll(filmRollDbHelper.getRolls());
    }
}

package com.example.finalspaghettiproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalspaghettiproject.database.TaskdbHelper;
import com.example.finalspaghettiproject.database.dbTasks;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //making this a constant for logging have read some tips that developers create TAG for it
    //this is where to define some variables
    private static final String TAG = "MainActivity";
    private TaskdbHelper Helper;
    private ListView TaskListView;

    //this array adapter helps populate the ListView w/ data
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Helper = new TaskdbHelper(this);
        TaskListView = (ListView) findViewById(R.id.list_tasks);

        updateUI(); //this is called every time the data of the app changes

        SQLiteDatabase db = Helper.getReadableDatabase();
        Cursor cursor = db.query(dbTasks.TaskEntry.TABLE,
                new String[]{dbTasks.TaskEntry._ID, dbTasks.TaskEntry.COL_TASK_TITLE}, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(dbTasks.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
            //when the app runs, logcat should be able to show the list of tasks stored in the db

        }
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // onCreateOptionsMenu method existence is to inflate the menu in the main activity
    // and it uses onOptionsItemSelected method to react to different user interactions with
    // the menu item(s)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        //AlertDialog is used to get the task from the user
                        // when the add item button is clicked
                        .setTitle("Add a new spaghetti task")
                        .setMessage("What do you want to do next homie?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = Helper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(dbTasks.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(dbTasks.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI(); //this is called every time the data of the app changes

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            case R.id.action_Contact:
                startActivity(new Intent(MainActivity.this, contactInfo.class));




            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = Helper.getReadableDatabase();
        Cursor cursor = db.query(dbTasks.TaskEntry.TABLE,
                new String[]{dbTasks.TaskEntry._ID, dbTasks.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(dbTasks.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_tasks, // what view to use for the items
                    R.id.task_title, // this is where to put the String od data
                    taskList); //the area of where to get all the data
            TaskListView.setAdapter(mAdapter); // this is to set it as the adapter
            //of the ListView Instance
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = Helper.getWritableDatabase();
        db.delete(dbTasks.TaskEntry.TABLE,
                dbTasks.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }
}

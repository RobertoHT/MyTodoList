package micromaster.beginner.com.mytodolist.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.Nullable;

import micromaster.beginner.com.mytodolist.data.TodoListContrat;

/**
 * Created by praxis on 31/03/17.
 */

public class TodoListService extends IntentService {
    public static final String EXTRA_TASK_DESCRIPTION = "EXTRA_TASK_DESCRIPTION";

    public TodoListService() {
        super("TodoListService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String taskDescription = intent.getStringExtra(EXTRA_TASK_DESCRIPTION);

        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListContrat.TodoEntry.COLUMN_DESCRIPTION, taskDescription);

        getContentResolver().insert(TodoListContrat.TodoEntry.CONTENT_URI, contentValues);
    }
}

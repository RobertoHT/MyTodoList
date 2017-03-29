package micromaster.beginner.com.mytodolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import micromaster.beginner.com.mytodolist.data.TodoListContrat.TodoEntry;

/**
 * Created by praxis on 29/03/17.
 */

public class TodoListDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todolist.db";

    public TodoListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + TodoEntry.TABLE_NAME + " (" +
                TodoEntry._ID + " INTEGER PRIMARY KEY, " +
                TodoEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                TodoEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TodoEntry.COLUMN_DONE + " INTEGER, " +
                "UNIQUE (" + TodoEntry.COLUMN_DATE + ", " + TodoEntry.COLUMN_DESCRIPTION + ") ON " +
                "CONFLICT IGNORE);";

        sqLiteDatabase.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

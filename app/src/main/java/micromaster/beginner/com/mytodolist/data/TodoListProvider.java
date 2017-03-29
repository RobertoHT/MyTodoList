package micromaster.beginner.com.mytodolist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by praxis on 29/03/17.
 */

public class TodoListProvider extends ContentProvider {
    private static final int TODO = 100;
    private static final int TODO_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TodoListDBHelper mOpenHelper;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TodoListContrat.CONTENT_AUTORITHY;

        matcher.addURI(authority, TodoListContrat.PATH_TODO, TODO);
        matcher.addURI(authority, TodoListContrat.PATH_TODO + "/#", TODO_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new TodoListDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)){
            case TODO:{
                retCursor = mOpenHelper.getReadableDatabase().query(TodoListContrat.TodoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case TODO_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TodoListContrat.TodoEntry.TABLE_NAME,
                        projection,
                        TodoListContrat.TodoEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case TODO:{
                long _id = db.insert(TodoListContrat.TodoEntry.TABLE_NAME, null, contentValues);
                if(_id > 0){
                    returnUri = TodoListContrat.TodoEntry.CONTENT_URI;
                }else{
                    throw  new android.database.SQLException("Failed to insert row in " + uri);
                }
                break;
            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDelete;

        switch (match){
            case TODO:{
                rowsDelete = db.delete(TodoListContrat.TodoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(selection == null || rowsDelete != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdate;

        switch (match){
            case TODO:{
                rowsUpdate = db.update(TodoListContrat.TodoEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdate != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdate;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match){
            case TODO:{
                db.beginTransaction();
                int returnCount = 0;
                try{
                    for (ContentValues value:values){
                        long _id = db.insert(TodoListContrat.TodoEntry.TABLE_NAME, null, value);
                        if(_id != -1){
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            default: return super.bulkInsert(uri, values);
        }
    }
}

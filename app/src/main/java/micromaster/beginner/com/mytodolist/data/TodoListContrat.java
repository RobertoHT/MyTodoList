package micromaster.beginner.com.mytodolist.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by praxis on 29/03/17.
 */

public class TodoListContrat {
    public static final String CONTENT_AUTORITHY = "edu.galileo.todolist";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTORITHY);
    private static final String PATH_TODO = "todo";

    public static final class TodoEntry implements BaseColumns{
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_DESCRIPTION = "descp";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DONE = "done";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODO).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTORITHY + "/" + PATH_TODO;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTORITHY + "/" + PATH_TODO;
    }
}

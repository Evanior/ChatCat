package fr.imie.huard.chatcat.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by huard.cdi04 on 11/05/2017.
 */

public class MessageCPContract{
    public static final String AUTHORITY = "fr.imie.huard.chatcat";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MESSAGE = "message";
    public static final class MessageEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MESSAGE).build();

        public static final String

                TABLE_NAME = "messages";
        public static final String COLUMN_PSEUDO = "pseudo";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_DATE = "date";
    }
}
package fr.imie.huard.chatcat.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static fr.imie.huard.chatcat.provider.MessageCPContract.MessageEntry.TABLE_NAME;

/**
 * Created by huard.cdi04 on 11/05/2017.
 */

public class MessageProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mPostDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;
        
        switch (match) {
            case MESSAGE:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
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
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // COMPLETED (2) Write URI matching code to identify the match for the tasks directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned
        final SQLiteDatabase db = mPostDbHelper.getWritableDatabase();
        switch (match) {
            case MESSAGE:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MessageCPContract.MessageEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    public static final int MESSAGE = 100;
    public static final int POST_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildURIMatcher();

    public static UriMatcher buildURIMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MessageCPContract.AUTHORITY,MessageCPContract.PATH_MESSAGE, MESSAGE);
        uriMatcher.addURI(MessageCPContract.AUTHORITY,
                MessageCPContract.PATH_MESSAGE + "/#",POST_WITH_ID);
        return uriMatcher;
    }
}

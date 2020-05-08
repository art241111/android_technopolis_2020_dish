package ru.art241111.clubolympus.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {
    private ClubOlympusContract() {
    }
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "ru.art241111.clubolympus";
    public static final String PATH_MEMBERS = "members";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME  + AUTHORITY);


    public static final String DATABASE_NAME = "clubOlympus";
    public static final int DATABASE_VERSION = 2;

    public static final class  MemberEntry implements BaseColumns {
        public static final String TABLE_NAME = "clubMembers";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_SPORT = "sport";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MEMBERS);

        public static final String CONTENT_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MEMBERS;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MEMBERS;
    }
}

package com.universaltask.ion.universaltask.data.local;

import android.provider.BaseColumns;

/**
 * Created by Ionut on 2/12/2017.
 */

public final class TasksPersistenceContract {

    private TasksPersistenceContract(){}

   public static abstract class TaskEntry implements  BaseColumns {
       public static final String TABLE_NAME = "task";
       public static final String COLUMN_NAME_ENTRY_ID = "entrid";
       public static final String COLUMN_NAME_TITLE = "title";
       public static final String COLUMN_NAME_DESCRIPTION = "description";
       public static final String COLUMN_NAME_COMPLETED = "completed";

   }
}

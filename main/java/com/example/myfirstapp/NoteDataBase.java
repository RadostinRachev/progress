package com.example.myfirstapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

@Database(entities = {Note.class} , version = 8, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context){

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class , "note_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    //.addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    @Override
    public void close() {
        super.close();
        instance = null;
    }

    public void checkpoint() {
        int attempts = 0;
        int max_attempts = 10;
        Cursor csr;
        SupportSQLiteDatabase ssd  = instance.getOpenHelper().getWritableDatabase();
        if (isWALOn(ssd)) {
            Log.d("CHKPOINTINFO","Attempt " +  (attempts + 1));
            while (checkpoint(ssd) > 0 && attempts++ < max_attempts) { }
        }
    }

    private boolean isWALOn(SupportSQLiteDatabase db) {
        boolean rv = false;
        Cursor csr = db.query("PRAGMA journal_mode");
        if  (csr.moveToFirst()) {
            if (csr.getString(0).toLowerCase().equals("wal")) rv = true;
        }
        csr.close();
        return rv;
    }

    private int checkpoint(SupportSQLiteDatabase db) {
        Log.d("CHKPOINTINFO","Attempting Database Checkpoint");
        int blocked = 0;
        int pages_to_checkpoint = 0;
        int checkpointed_pages = 0;
        Cursor csr = db.query("PRAGMA wal_checkpoint(TRUNCATE)");
        if (csr.moveToFirst()) {
            blocked = csr.getInt(0);
            pages_to_checkpoint = csr.getInt(1);
            checkpointed_pages = csr.getInt(2);
        }
        csr.close();
        Log.d("CHKPOINTINFO",
                "Checkpoint values Blocked = " + blocked +
                        " Pages to Checkpoint = " + pages_to_checkpoint +
                        " Pages Checkpointed = " + checkpointed_pages
        );
        if (blocked > 0) return -1;
        if (checkpointed_pages < pages_to_checkpoint) return 1;
        return 0;
    }
/*
    public void backup(Context context) {
        instance.close();
        //......... backup the file
        File db = context.getDatabasePath("note_database");
        File dbShm = new File(db.getParent(), "note_database-shm");
        File dbWal = new File(db.getParent(), "note_database-wal");

        File db2 = new File("/sdcard/", "note_database");
        File dbShm2 = new File(db2.getParent(), "note_database-shm");
        File dbWal2 = new File(db2.getParent(), "note_database-wal");

        try {
            FileUtils.copyFile(db, db2);
            FileUtils.copyFile(dbShm, dbShm2);
            FileUtils.copyFile(dbWal, dbWal2);
        } catch (Exception e) {
            Log.e("SAVEDB", e.toString());
        }

        getInstance(context);
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

    };
*/

    private static class PopulateDbAsyncTask extends AsyncTask<Void , Void , Void>{

        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDataBase db) {
            noteDao = db.noteDao();
            //noteDao.insert(new Note("0"));
            //noteDao.insert(new Note("1"));
            //noteDao.insert(new Note("2"));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //noteDao.getAllNotes();
            return null;
        }
    }

}





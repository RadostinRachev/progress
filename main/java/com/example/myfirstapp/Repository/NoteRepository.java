package com.example.myfirstapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myfirstapp.Checks;
import com.example.myfirstapp.Note;
import com.example.myfirstapp.NoteDao;
import com.example.myfirstapp.NoteDataBase;

import java.util.ArrayList;
import java.util.List;

/*
Singleton pattern
 */
public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private String NotesResult;
    private Checks NotesChecks;
    private List<String> Date;
    private String Hresult;
    private String Sresult;
    private String Presult;
    private int save = 0;
    private int savedday = 0;
    NoteDataBase dataBase;

    public NoteRepository(Application application) {
        Log.d("APPINFO","Retrieving the Database Instance");
        dataBase = NoteDataBase.getInstance(application);
        Log.d("APPINFO","Retrieving the Database DAO");
        noteDao = dataBase.noteDao();
        //allNotes = noteDao.getAllNotes();
        //NotesResult = noteDao.getResultNote();
/*
        Log.d("APPINFO","Mock backup/close");
        dataBase.backup(application);
        Log.d("APPINFO","Retrieveing the Database Instance after the close");
        dataBase = NoteDataBase.getInstance(application);
        Log.d("APPINFO","Retrieving Rows from the Database");
        allNotes = noteDao.getAllNotes();
        Log.d("MYTABLEINFO","ID = " + allNotes + " VALUE = " + allNotes.getValue());
        Log.d("APPINFO","Closing the database");
        dataBase.close();
        allNotes = noteDao.getAllNotes();
        Log.d("MYTABLEINFO","ID = " + allNotes + " VALUE = " + allNotes.getValue());
*/
    }

    public void insert(Note note){

        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){

    return allNotes;
    }

    public int CheckDay(String date){
        savedday = noteDao.CheckDay(date);
        return savedday;
    }

    public int CheckSave(String datee){
        save = noteDao.CheckSave(datee);
        return save;
    }

    public void updateChanged(String dates,String result , String health , String social , String personal) {
        noteDao.updateChanged(dates,result,health,social,personal);
    }

    public void updateSave(String result , ArrayList<Checks> checked ,String health , String social , String personal,String dates) {
        noteDao.updateSave(result , checked , health , social ,personal ,dates);
    }

    public List<String> getDate(String date){
       // Date = noteDao.getRecordsByDate(date);
        return Date;
    }

    public String getHealthResult(String datata){
        Hresult = noteDao.getHealthNote(datata);
        return Hresult;
    }

    public String getSocialResult(String datichka){
        Sresult = noteDao.getSocialNote(datichka);
        return Sresult;
    }

    public String getPersonalResult(String datov){
        Presult = noteDao.getPersonalNote(datov);
        return Presult;
    }


    public String getNotesResult(String da){
       NotesResult = noteDao.getResultNote(da);
        return NotesResult;
    }


    public Checks getNotesChecks(String dat){
        NotesChecks = noteDao.getChecksNotes(dat);
        return NotesChecks;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note , Void , Void> {

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note , Void , Void> {

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note , Void , Void> {

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void , Void , Void> {

        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}

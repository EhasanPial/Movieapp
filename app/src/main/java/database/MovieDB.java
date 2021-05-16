package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {

    private static final String DB_NAME = "movie";
    private static MovieDB sInstance;

    static MovieDB getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (MovieDB.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDB.class, DB_NAME).build();
                }

            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}

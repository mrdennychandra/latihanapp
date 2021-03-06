package id.unikom.latihanapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import id.unikom.latihanapp.model.Contact;


@Database(entities = {Contact.class},
        version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    AppDatabase() {
    }

    private static AppDatabase create(final Context context) {
        SupportFactory factory = new SupportFactory(SQLiteDatabase
                .getBytes("MySecretKey".toCharArray()));
        return Room.databaseBuilder(context, AppDatabase.class,
                "contact.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .openHelperFactory(factory)
                .build();
    }
}
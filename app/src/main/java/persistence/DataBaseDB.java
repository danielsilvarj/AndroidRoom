package persistence;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import entity.Cliente;

@Database(entities = {Cliente.class}, version = 1, exportSchema = false)
public abstract class DataBaseDB extends RoomDatabase {

    private static DataBaseDB INSTANCE = null;

    public abstract ClienteDao getClienteDao();

    public static DataBaseDB getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE  = Room.databaseBuilder(context.getApplicationContext(), DataBaseDB.class, "coti-database.db").build();

        }

        return INSTANCE;
    }

}

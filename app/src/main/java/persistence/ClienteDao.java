package persistence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entity.Cliente;

@Dao
public interface ClienteDao {


    @Insert
    void insert(Cliente cliente);

    @Query("select * from clientes order by id")
    LiveData<List<Cliente>> listar();

    @Query("select * from clientes where id = :param1")
    Cliente listarUm(int param1);

    @Update
    void updateCliente(Cliente cliente);

    @Delete
    void delete(Cliente cliente);




}

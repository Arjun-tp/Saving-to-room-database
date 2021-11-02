package com.example.test1.DaoClass;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.test1.EntityClass.PersonModel;

import java.util.List;

@Dao
public interface Daoclass {

    //Database Queries

    @Insert
    void insertAllData(PersonModel model);

    @Query("select * from user")
    List<PersonModel> getAllData();

    @Query("delete from user where `key`= :id")
    void deleteData(int id);

    @Query("update user set name= :name, age= :age, fee= :fee, termDate= :termDate where `key`= :key")
    void updateData(String name, String age, String fee, String termDate, int key);

}
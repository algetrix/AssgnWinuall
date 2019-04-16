package com.example.Assignment;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class FavouriteDao_Impl implements FavouriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfEntity;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavById;

  public FavouriteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEntity = new EntityInsertionAdapter<Entity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `defaultdata`(`id`,`maxTempMum`,`minTempMum`,`maxTempAhm`,`minTempAhm`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Entity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindDouble(2, value.getMaxTempMum());
        stmt.bindDouble(3, value.getMinTempMum());
        stmt.bindDouble(4, value.getMaxTempAhm());
        stmt.bindDouble(5, value.getMinTempAhm());
      }
    };
    this.__deletionAdapterOfEntity = new EntityDeletionOrUpdateAdapter<Entity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `defaultdata` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Entity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfDeleteFavById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM defaultdata WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public void insert(Entity Entity) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfEntity.insert(Entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Entity Entity) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfEntity.handle(Entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFavById(int id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavById.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteFavById.release(_stmt);
    }
  }

  @Override
  public List<Entity> getdefaultdata() {
    final String _sql = "SELECT * FROM `defaultdata`";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMaxTempMum = _cursor.getColumnIndexOrThrow("maxTempMum");
      final int _cursorIndexOfMinTempMum = _cursor.getColumnIndexOrThrow("minTempMum");
      final int _cursorIndexOfMaxTempAhm = _cursor.getColumnIndexOrThrow("maxTempAhm");
      final int _cursorIndexOfMinTempAhm = _cursor.getColumnIndexOrThrow("minTempAhm");
      final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Entity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final double _tmpMaxTempMum;
        _tmpMaxTempMum = _cursor.getDouble(_cursorIndexOfMaxTempMum);
        final double _tmpMinTempMum;
        _tmpMinTempMum = _cursor.getDouble(_cursorIndexOfMinTempMum);
        final double _tmpMaxTempAhm;
        _tmpMaxTempAhm = _cursor.getDouble(_cursorIndexOfMaxTempAhm);
        final double _tmpMinTempAhm;
        _tmpMinTempAhm = _cursor.getDouble(_cursorIndexOfMinTempAhm);
        _item = new Entity(_tmpId,_tmpMaxTempMum,_tmpMinTempMum,_tmpMaxTempAhm,_tmpMinTempAhm);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}

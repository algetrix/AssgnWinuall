package com.example.Assignment;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class NewDatabase_Impl extends NewDatabase {
  private volatile FavouriteDao _favouriteDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `defaultdata` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `maxTempMum` REAL NOT NULL, `minTempMum` REAL NOT NULL, `maxTempAhm` REAL NOT NULL, `minTempAhm` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"a0b1fd390bd3b8b4f6d38a9f8215090c\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `defaultdata`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsDefaultdata = new HashMap<String, TableInfo.Column>(5);
        _columnsDefaultdata.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsDefaultdata.put("maxTempMum", new TableInfo.Column("maxTempMum", "REAL", true, 0));
        _columnsDefaultdata.put("minTempMum", new TableInfo.Column("minTempMum", "REAL", true, 0));
        _columnsDefaultdata.put("maxTempAhm", new TableInfo.Column("maxTempAhm", "REAL", true, 0));
        _columnsDefaultdata.put("minTempAhm", new TableInfo.Column("minTempAhm", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDefaultdata = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDefaultdata = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDefaultdata = new TableInfo("defaultdata", _columnsDefaultdata, _foreignKeysDefaultdata, _indicesDefaultdata);
        final TableInfo _existingDefaultdata = TableInfo.read(_db, "defaultdata");
        if (! _infoDefaultdata.equals(_existingDefaultdata)) {
          throw new IllegalStateException("Migration didn't properly handle defaultdata(com.example.Assignment.Entity).\n"
                  + " Expected:\n" + _infoDefaultdata + "\n"
                  + " Found:\n" + _existingDefaultdata);
        }
      }
    }, "a0b1fd390bd3b8b4f6d38a9f8215090c", "e1149cf8aa598440f42470262475dbf5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "defaultdata");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `defaultdata`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public FavouriteDao favouriteDao() {
    if (_favouriteDao != null) {
      return _favouriteDao;
    } else {
      synchronized(this) {
        if(_favouriteDao == null) {
          _favouriteDao = new FavouriteDao_Impl(this);
        }
        return _favouriteDao;
      }
    }
  }
}

package com.yutian.base.database.srcgen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SSQCONFIG".
*/
public class SSQCONFIGDao extends AbstractDao<SSQCONFIG, Integer> {

    public static final String TABLENAME = "SSQCONFIG";

    /**
     * Properties of entity SSQCONFIG.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ID = new Property(0, Integer.class, "ID", true, "ID");
        public final static Property NAME = new Property(1, String.class, "NAME", false, "NAME");
        public final static Property VALUE = new Property(2, String.class, "VALUE", false, "VALUE");
    };


    public SSQCONFIGDao(DaoConfig config) {
        super(config);
    }
    
    public SSQCONFIGDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SSQCONFIG\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: ID
                "\"NAME\" TEXT," + // 1: NAME
                "\"VALUE\" TEXT);"); // 2: VALUE
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SSQCONFIG\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SSQCONFIG entity) {
        stmt.clearBindings();
 
        Integer ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String NAME = entity.getNAME();
        if (NAME != null) {
            stmt.bindString(2, NAME);
        }
 
        String VALUE = entity.getVALUE();
        if (VALUE != null) {
            stmt.bindString(3, VALUE);
        }
    }

    /** @inheritdoc */
    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SSQCONFIG readEntity(Cursor cursor, int offset) {
        SSQCONFIG entity = new SSQCONFIG( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // NAME
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // VALUE
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SSQCONFIG entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setNAME(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setVALUE(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Integer updateKeyAfterInsert(SSQCONFIG entity, long rowId) {
        return entity.getID();
    }
    
    /** @inheritdoc */
    @Override
    public Integer getKey(SSQCONFIG entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

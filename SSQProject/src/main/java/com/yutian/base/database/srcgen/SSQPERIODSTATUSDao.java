package com.yutian.base.database.srcgen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SSQPERIODSTATUS".
*/
public class SSQPERIODSTATUSDao extends AbstractDao<SSQPERIODSTATUS, String> {

    public static final String TABLENAME = "SSQPERIODSTATUS";

    /**
     * Properties of entity SSQPERIODSTATUS.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property PERIOD = new Property(0, String.class, "PERIOD", true, "PERIOD");
        public final static Property STATUS = new Property(1, String.class, "STATUS", false, "STATUS");
    };


    public SSQPERIODSTATUSDao(DaoConfig config) {
        super(config);
    }
    
    public SSQPERIODSTATUSDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SSQPERIODSTATUS\" (" + //
                "\"PERIOD\" TEXT PRIMARY KEY NOT NULL ," + // 0: PERIOD
                "\"STATUS\" TEXT);"); // 1: STATUS
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SSQPERIODSTATUS\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SSQPERIODSTATUS entity) {
        stmt.clearBindings();
 
        String PERIOD = entity.getPERIOD();
        if (PERIOD != null) {
            stmt.bindString(1, PERIOD);
        }
 
        String STATUS = entity.getSTATUS();
        if (STATUS != null) {
            stmt.bindString(2, STATUS);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SSQPERIODSTATUS readEntity(Cursor cursor, int offset) {
        SSQPERIODSTATUS entity = new SSQPERIODSTATUS( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // PERIOD
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // STATUS
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SSQPERIODSTATUS entity, int offset) {
        entity.setPERIOD(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSTATUS(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(SSQPERIODSTATUS entity, long rowId) {
        return entity.getPERIOD();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(SSQPERIODSTATUS entity) {
        if(entity != null) {
            return entity.getPERIOD();
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

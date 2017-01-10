package com.yutian.mtkviewer.dbcontrol.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TREE_ITEM".
*/
public class TreeItemDao extends AbstractDao<TreeItem, Void> {

    public static final String TABLENAME = "TREE_ITEM";

    /**
     * Properties of entity TreeItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property NAME = new Property(0, String.class, "NAME", false, "NAME");
        public final static Property FARID_PARENT = new Property(1, String.class, "FARID_PARENT", false, "FARID__PARENT");
        public final static Property FARID = new Property(2, String.class, "FARID", false, "FARID");
        public final static Property URL = new Property(3, String.class, "URL", false, "URL");
        public final static Property HASGRAP = new Property(4, String.class, "HASGRAP", false, "HASGRAP");
        public final static Property TOP_PARENT = new Property(5, String.class, "TOP_PARENT", false, "TOP__PARENT");
    };


    public TreeItemDao(DaoConfig config) {
        super(config);
    }
    
    public TreeItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TREE_ITEM\" (" + //
                "\"NAME\" TEXT," + // 0: NAME
                "\"FARID__PARENT\" TEXT," + // 1: FARID_PARENT
                "\"FARID\" TEXT NOT NULL ," + // 2: FARID
                "\"URL\" TEXT," + // 3: URL
                "\"HASGRAP\" TEXT," + // 4: HASGRAP
                "\"TOP__PARENT\" TEXT);"); // 5: TOP_PARENT
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TREE_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TreeItem entity) {
        stmt.clearBindings();
 
        String NAME = entity.getNAME();
        if (NAME != null) {
            stmt.bindString(1, NAME);
        }
 
        String FARID_PARENT = entity.getFARID_PARENT();
        if (FARID_PARENT != null) {
            stmt.bindString(2, FARID_PARENT);
        }
        stmt.bindString(3, entity.getFARID());
 
        String URL = entity.getURL();
        if (URL != null) {
            stmt.bindString(4, URL);
        }
 
        String HASGRAP = entity.getHASGRAP();
        if (HASGRAP != null) {
            stmt.bindString(5, HASGRAP);
        }
 
        String TOP_PARENT = entity.getTOP_PARENT();
        if (TOP_PARENT != null) {
            stmt.bindString(6, TOP_PARENT);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public TreeItem readEntity(Cursor cursor, int offset) {
        TreeItem entity = new TreeItem( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // NAME
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // FARID_PARENT
            cursor.getString(offset + 2), // FARID
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // URL
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // HASGRAP
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // TOP_PARENT
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TreeItem entity, int offset) {
        entity.setNAME(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setFARID_PARENT(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFARID(cursor.getString(offset + 2));
        entity.setURL(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHASGRAP(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTOP_PARENT(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(TreeItem entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(TreeItem entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

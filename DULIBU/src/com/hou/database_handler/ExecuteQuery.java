package com.hou.database_handler;

import java.io.IOException;
import java.util.ArrayList;

import com.hou.model.Lichtrinh;
import com.hou.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class ExecuteQuery {
	protected static final String TAG = "Execute Query";
	SQLiteDatabase database;
	DatabaseHelper mDbHelper;
	private final Context mContext;

	public ExecuteQuery(Context context) {
		this.mContext = context;
		mDbHelper = new DatabaseHelper(mContext);
	}

	public ExecuteQuery createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public ExecuteQuery open() throws SQLException {
		try {
			mDbHelper.openDataBase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/*
	 * tbl_user
	 */

	// select * from tbl_user
	public ArrayList<User> getAllUser() {
		ArrayList<User> listUser = new ArrayList<User>();
		String selectQuery = "SELECT * FROM " + ColumnName.TBL_USER_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				User u = new User();

				u.setMaUser(cursor.getString(0));
				u.setFullname(cursor.getString(1));
				u.setEmail(cursor.getString(2));
				u.setUsername(cursor.getString(3));
				u.setNgaysinh(cursor.getString(4));
				u.setSdt(cursor.getString(5));
				u.setGioitinh(cursor.getInt(6));
				u.setSdt_lienhe(cursor.getString(7));
				u.setAvatar(cursor.getString(8));
				u.setGhichu(cursor.getString(9));

				listUser.add(u);
			} while (cursor.moveToNext());
		}
		return listUser;
	}

	// insert single record
	public boolean insert_tbl_user_single(User u) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.TBL_USER_MAUSER, u.getMaUser());
			cv.put(ColumnName.TBL_USER_FULLNAME, u.getFullname());
			cv.put(ColumnName.TBL_USER_EMAIL, u.getEmail());
			cv.put(ColumnName.TBL_USER_USERNAME, u.getUsername());
			cv.put(ColumnName.TBL_USER_NGAYSINH, u.getNgaysinh());
			cv.put(ColumnName.TBL_USER_SDT, u.getSdt());
			cv.put(ColumnName.TBL_USER_GIOITINH, u.getGioitinh());
			cv.put(ColumnName.TBL_USER_SDTLIENHE, u.getSdt_lienhe());
			cv.put(ColumnName.TBL_USER_AVATAR, u.getAvatar());
			cv.put(ColumnName.TBL_USER_GHICHU, u.getGhichu());

			database.insert(ColumnName.TBL_USER_TABLE, null, cv);
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tbl_user_single", e.getMessage());
			return false;
		}
	}

	// insert multi record
	public boolean insert_tbl_user_multi(ArrayList<User> listU) {
		try {
			database = mDbHelper.getWritableDatabase();

			for (User u : listU) {
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.TBL_USER_MAUSER, u.getMaUser());
				cv.put(ColumnName.TBL_USER_FULLNAME, u.getFullname());
				cv.put(ColumnName.TBL_USER_EMAIL, u.getEmail());
				cv.put(ColumnName.TBL_USER_USERNAME, u.getUsername());
				cv.put(ColumnName.TBL_USER_NGAYSINH, u.getNgaysinh());
				cv.put(ColumnName.TBL_USER_SDT, u.getSdt());
				cv.put(ColumnName.TBL_USER_GIOITINH, u.getGioitinh());
				cv.put(ColumnName.TBL_USER_SDTLIENHE, u.getSdt_lienhe());
				cv.put(ColumnName.TBL_USER_AVATAR, u.getAvatar());
				cv.put(ColumnName.TBL_USER_GHICHU, u.getGhichu());

				database.insert(ColumnName.TBL_USER_TABLE, null, cv);
			}
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tbl_user_single", e.getMessage());
			return false;
		}
	}
	
	/*
	 * tbl_lichtrinh
	 */
	
	
}

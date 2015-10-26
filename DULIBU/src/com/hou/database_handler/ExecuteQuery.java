package com.hou.database_handler;

import java.io.IOException;
import java.util.ArrayList;

import com.hou.model.Chatluong_Lichtrinh;
import com.hou.model.Diemphuot;
import com.hou.model.Tinh_Thanhpho;
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
	 * dm_chatluong_lichtrinh
	 */

	// select * from dm_chatluong_lichtrinh
	public ArrayList<Chatluong_Lichtrinh> getAllChatluongLichtrinh() {
		ArrayList<Chatluong_Lichtrinh> list = new ArrayList<Chatluong_Lichtrinh>();
		String selectQuery = "SELECT * FROM "
				+ ColumnName.DM_CHATLUONG_LICHTRINH_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Chatluong_Lichtrinh cl = new Chatluong_Lichtrinh();

				cl.setMaChatluong(cursor.getColumnName(0));
				cl.setTenChatluong(cursor.getColumnName(1));
				cl.setGhichu(cursor.getColumnName(2));

				list.add(cl);
			} while (cursor.moveToNext());
		}
		return list;
	}

	// insert multi record
	public boolean insert_dm_chatluong_lichtrinh_multi(
			ArrayList<Chatluong_Lichtrinh> list) {
		try {
			database = mDbHelper.getWritableDatabase();
			for (Chatluong_Lichtrinh cl : list) {
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.DM_CHATLUONG_LICHTRINH_MACHATLUONG,
						cl.getMaChatluong());
				cv.put(ColumnName.DM_CHATLUONG_LICHTRINH_TENCHATLUONG,
						cl.getTenChatluong());
				cv.put(ColumnName.DM_CHATLUONG_LICHTRINH_GHICHU, cl.getGhichu());

				database.insert(ColumnName.DM_CHATLUONG_LICHTRINH_TABLE, null,
						cv);
			}
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_dm_chatluong_lichtrinh_multi", e.getMessage());
			return false;
		}
	}

	/*
	 * tbl_diem_phuot
	 */

	// select * from tbl_diemphuot
	public ArrayList<Diemphuot> getAllDiemphuot() {
		ArrayList<Diemphuot> listDiemphuot = new ArrayList<Diemphuot>();
		String selectQuery = "SELECT * FROM " + ColumnName.TBL_DIEM_PHUOT_TABLE + " ORDER BY maDiemphuot ASC";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Diemphuot d = new Diemphuot();

				d.setMaDiemphuot(cursor.getString(0));
				d.setTenDiemphuot(cursor.getString(1));
				d.setLat(cursor.getString(2));
				d.setLon(cursor.getString(3));
				d.setMaTinh(cursor.getString(4));
				d.setDiachi(cursor.getString(5));
				d.setGhichu(cursor.getString(6));
				d.setImage(cursor.getString(7));
				d.setTrangthaiChuan(cursor.getInt(8));

				listDiemphuot.add(d);
			} while (cursor.moveToNext());
		}
		return listDiemphuot;
	}

	// select * from tbl_diemphuot
	public ArrayList<Diemphuot> getAllDiemphuotBy2MaTinh(String start,
			String end) {
		ArrayList<Diemphuot> listDiemphuot = new ArrayList<Diemphuot>();
		String selectQuery = "SELECT * FROM " + ColumnName.TBL_DIEM_PHUOT_TABLE
				+ " WHERE " + ColumnName.TBL_DIEM_PHUOT_MATINH + " = '" + start
				+ "' OR " + ColumnName.TBL_DIEM_PHUOT_MATINH + " = '" + end
				+ "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Diemphuot d = new Diemphuot();

				d.setMaDiemphuot(cursor.getString(0));
				d.setTenDiemphuot(cursor.getString(1));
				d.setLat(cursor.getString(2));
				d.setLon(cursor.getString(3));
				d.setMaTinh(cursor.getString(4));
				d.setDiachi(cursor.getString(5));
				d.setGhichu(cursor.getString(6));
				d.setImage(cursor.getString(7));
				d.setTrangthaiChuan(cursor.getInt(8));

				listDiemphuot.add(d);
			} while (cursor.moveToNext());
		}
		return listDiemphuot;
	}
	
	// insert single Diemphuot to tbl_diemphuot
		public boolean insert_tbl_diemphuot_single(Diemphuot dp) {
			try {
				database = mDbHelper.getWritableDatabase();
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.TBL_DIEM_PHUOT_MADIEMPHUOT, dp.getMaDiemphuot());
				cv.put(ColumnName.TBL_DIEM_PHUOT_TENDIEMPHUOT, dp.getTenDiemphuot());
				cv.put(ColumnName.TBL_DIEM_PHUOT_LAT, dp.getLat());
				cv.put(ColumnName.TBL_DIEM_PHUOT_LON, dp.getLon());
				cv.put(ColumnName.TBL_DIEM_PHUOT_MATINH, dp.getMaTinh());
				cv.put(ColumnName.TBL_DIEM_PHUOT_DIACHI, dp.getDiachi());
				cv.put(ColumnName.TBL_DIEM_PHUOT_GHICHU, dp.getGhichu());
				cv.put(ColumnName.TBL_DIEM_PHUOT_IMAGE, dp.getImage());
				cv.put(ColumnName.TBL_DIEM_PHUOT_TRANGTHAICHUAN, dp.getTrangthaiChuan());
				

				database.insert(ColumnName.TBL_DIEM_PHUOT_TABLE, null, cv);
				return true;
			} catch (SQLiteException e) {
				Log.e("insert_tbl_user_single", e.getMessage());
				return false;
			}
		}

	// insert multi DiemPhuot to tblDiemPhuot
		public boolean insert_tbl_Diemphuot_multi(ArrayList<Diemphuot> listD) {
			try {
				database = mDbHelper.getWritableDatabase();
				for (Diemphuot dp : listD) {
					ContentValues cv = new ContentValues();

					cv.put(ColumnName.TBL_DIEM_PHUOT_MADIEMPHUOT, dp.getMaDiemphuot());
					cv.put(ColumnName.TBL_DIEM_PHUOT_TENDIEMPHUOT, dp.getTenDiemphuot());
					cv.put(ColumnName.TBL_DIEM_PHUOT_LAT, dp.getLat());
					cv.put(ColumnName.TBL_DIEM_PHUOT_LON, dp.getLon());
					cv.put(ColumnName.TBL_DIEM_PHUOT_MATINH, dp.getMaTinh());
					cv.put(ColumnName.TBL_DIEM_PHUOT_DIACHI, dp.getDiachi());
					cv.put(ColumnName.TBL_DIEM_PHUOT_GHICHU, dp.getGhichu());
					cv.put(ColumnName.TBL_DIEM_PHUOT_IMAGE, dp.getImage());

					database.insert(ColumnName.TBL_DIEM_PHUOT_TABLE, null, cv);
				}
				return true;
			} catch (SQLiteException e) {
				Log.e("insert_tbl_user_multi", e.getMessage());
				return false;
			}
		}

	/*
	 * tbl_lichtrinh
	 */

	/*
	 * tbl_tinh_thanhpho
	 */

	// select tinh_thanhpho where tenTinh
	public Tinh_Thanhpho getTinhByTentinh(String tenTinh) {
		Tinh_Thanhpho c = new Tinh_Thanhpho();
		String selectQuery = "SELECT * FROM "
				+ ColumnName.TBL_TINH_THANHPHO_TABLE + " WHERE "
				+ ColumnName.TBL_TINH_THANHPHO_TENTINH + " = '" + tenTinh + "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			c.setMaTinh(cursor.getString(0));
			c.setTenTinh(cursor.getString(1));
			c.setLat(cursor.getString(2));
			c.setLon(cursor.getString(3));
			c.setImage(cursor.getString(4));
			c.setGhichu(cursor.getString(5));
		}
		return c;
	}

	// select * from tbl_tinh_thanhpho
	public ArrayList<Tinh_Thanhpho> getAllTinhThanhpho() {
		ArrayList<Tinh_Thanhpho> listCity = new ArrayList<Tinh_Thanhpho>();
		String selectQuery = "SELECT * FROM "
				+ ColumnName.TBL_TINH_THANHPHO_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Tinh_Thanhpho c = new Tinh_Thanhpho();

				c.setMaTinh(cursor.getString(0));
				c.setTenTinh(cursor.getString(1));
				c.setLat(cursor.getString(2));
				c.setLon(cursor.getString(3));
				c.setImage(cursor.getString(4));
				c.setGhichu(cursor.getString(5));

				listCity.add(c);
			} while (cursor.moveToNext());
		}
		return listCity;
	}

	// insert multi record
	public boolean insert_tbl_tinh_thanhpho_multi(
			ArrayList<Tinh_Thanhpho> listCity) {
		try {
			database = mDbHelper.getWritableDatabase();
			for (Tinh_Thanhpho city : listCity) {
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.TBL_TINH_THANHPHO_MATINH, city.getMaTinh());
				cv.put(ColumnName.TBL_TINH_THANHPHO_TENTINH, city.getTenTinh());
				cv.put(ColumnName.TBL_TINH_THANHPHO_LAT, city.getLat());
				cv.put(ColumnName.TBL_TINH_THANHPHO_LON, city.getLon());
				cv.put(ColumnName.TBL_TINH_THANHPHO_IMAGE, city.getImage());
				cv.put(ColumnName.TBL_TINH_THANHPHO_GHICHU, city.getGhichu());

				database.insert(ColumnName.TBL_TINH_THANHPHO_TABLE, null, cv);
			}
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tbl_tinh_thanhpho_multi", e.getMessage());
			return false;
		}
	}

	public boolean delete_tbl_tinh_thanhpho() {
		try {
			database = mDbHelper.getWritableDatabase();
			database.delete(ColumnName.TBL_TINH_THANHPHO_TABLE, null, null); 
			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tbl_tinh_thanhpho", e.getMessage());
			return false;
		}
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

}

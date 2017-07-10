package com.pub.application;

import android.app.Application;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.pub.R;
import com.pub.anyversion.AnyVersion;
import com.pub.anyversion.Version;
import com.pub.anyversion.VersionParser;
import com.pub.base.BaseApp;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
/**
 * Created by Leader on 2017/5/22.
 *
 */

public class AppInitControl implements IAppInitControl {

	private Application mContext = null;
	
	public static boolean DEBUG = false;

	@Override
	public void init(Application context) {
		mContext = context;
		initLog();
		initCookieStore();
		initUpdateVersion();
		initDatabase();
	}

	@Override
	public void initLog() {
		Logger.init(mContext.getResources().getString(R.string.app_name))
				.methodCount(2).hideThreadInfo().logLevel(DEBUG ? LogLevel.FULL : LogLevel.NONE)
				.methodOffset(2);
	}

	@Override
	public void initSecurity() {

	}

	@Override
	public void initUpdateVersion() {
		AnyVersion.init(mContext, new VersionParser() {
			@Override
			public Version onParse(String response) {
				final JSONTokener tokener = new JSONTokener(response);
				try {
					JSONObject json = (JSONObject) tokener.nextValue();
					if (json.optBoolean("success", false)) {
						json = json.getJSONObject("data");
						Version version = new Version(json
								.getString("versionname"), json
								.getString("description"), json
								.getString("downloadurl"), json
								.getInt("versioncode"));
						return version;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}


	@Override
	public void initCookieStore() {
		// PersistentCookieStore cookieStore = new
		// PersistentCookieStore(mContext);
		// HttpClient.getInstance().getBase().setCookieStore(cookieStore);
	}

	@Override
	public void initDatabase() {
		//TODO 获取应用程序版本号
		int appVersion = 3;

		DaoConfig config = new DaoConfig(mContext);
//		config.setDbName(ConfigUtils.getFromConfig(KEY.privateInfo) + ".db");
		config.setDbVersion(appVersion);
		config.setDbUpgradeListener(new DbUpgradeListener() {

			/** 当程序目录中的数据库版本与当前应用程序的版本不一致时，会调用此方法 */
			@Override
			public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
			}
		});
		BaseApp.DBLoader = DbUtils.create(config);
		BaseApp.DBLoader.configDebug(DEBUG);
	}

	@Override
	public void initSMS() {
		// TODO 自动生成的方法存根

	}

	@Override
	public void initSocialization() {
		// TODO 自动生成的方法存根

	}

	@Override
	public void initStatistics() {
		// TODO 自动生成的方法存根

	}

}

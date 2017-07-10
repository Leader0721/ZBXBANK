package com.pub.widget.fileselector;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pub.R;
import com.pub.widget.fileselector.config.FileConfig;
import com.pub.widget.fileselector.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;


/**
 * 文件选择Activity
 * <p/>
 * 接收一个FileConfig对象通过Intent传入（可选）
 * <p/>
 * Created by zzhoujay on 2014/12/30 0030.
 */
public class FileSelectorActivity extends Activity {

    public static final int FILE_SELECT_ACTIVITY_CODE = 10086;

    private FileSelector fileSelector;
    private FileConfig fileConfig;
    private Class aClass;
    private boolean actionBarModel = true;
    private MenuItem ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        aClass = intent.getClass();

        if (intent != null) {
            try {
                fileConfig = (FileConfig) intent.getSerializableExtra(FileConfig.FILE_CONFIG);
            } catch (Exception e) {
                e.printStackTrace();
                fileConfig = new FileConfig();
            }
        }


        fileSelector = new FileSelector(this, fileConfig);

        fileSelector.setOnCancelListener(new FileSelector.OnCancelListener() {
            @Override
            public void OnCancel() {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        if (fileConfig.multiModel) {
            fileSelector.setOnConfirmListener(new FileSelector.OnConfirmListener() {
                @Override
                public void OnConfirm(ArrayList<String> filePath) {
                    Intent resultIntent = new Intent(getApplicationContext(), aClass);
                    resultIntent.putExtra(FileSelector.RESULT, filePath);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
        } else {
            fileSelector.setOnSelectCompleteListener(new FileSelector.OnSelectCompleteListener() {
                @Override
                public void onSelectComplete(String filePath) {

                    Intent resultIntent = new Intent(getApplicationContext(), aClass);
                    ArrayList<String> path = new ArrayList<>(1);
                    path.add(filePath);
                    if (FileUtils.getFileLastName(path.get(0)) != null) {
                        if (FileUtils.getFileLastName(path.get(0)).equals("xls")
                                || FileUtils.getFileLastName(path.get(0)).equals("xlsx")) {
                            resultIntent.putExtra(FileSelector.RESULT, path);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "请选择Excel文件", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择Excel文件", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            });

        }

        this.actionBarModel = fileConfig.actionBarMode;
        if (fileConfig.actionBarMode) {
            enableActionBarMode();
        }

        fileSelector.hitTitle();

//        setTitle(fileConfig == null ? "选择文件" : fileConfig.title);

        setContentView(fileSelector.getFileSelector());
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.getCustomView().findViewById(R.id.imgView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleCenter(actionBar);
    }

    public boolean formatValidation(ArrayList<String> path) {
        boolean isType = false;
        for (int i = 0; i < path.size(); i++) {
            if (FileUtils.getFileLastName(path.get(i)) != null) {
                switch (FileUtils.getFileLastName(path.get(i))) {
                    //Excel 2007
                    case "xlsx":
                    case "xls":
                    case "xlsm":
                    case "xltx":
                    case "xltm":
                    case "xlsb":
                    case "xlam":
                        //Word 2007
                    case "doc":
                    case "docx":
                    case "docm":
                    case "dotx":
                    case "dotm":
                        isType = true;
                        break;
                    default:
                        return false;
                }
            }
        }
        return isType;
    }

    private int titleWidth;

    private void setTitleCenter(ActionBar actionBar) {
        final ImageView imgView = (ImageView) actionBar.getCustomView().findViewById(R.id.imgView);
        final TextView title = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int width_full = metric.widthPixels;
        title.post(new Runnable() {
            @Override
            public void run() {
                titleWidth = title.getWidth();
                int left = (width_full - titleWidth) / 2;
                title.setPadding(left, 0, 0, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actionBarModel) {

            ok = menu.add(0, 10086, 1, "确定");
            ok.setIcon(R.mipmap.ic_check_white_48dp);
            ok.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            ok.setVisible(false);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarModel) {
            switch (item.getItemId()) {
                case 10086:
                    ArrayList<String> paths = fileSelector.getSelectFilePaths();
                    if (formatValidation(paths)) {
                        for (int i = 0; i < paths.size(); i++) {
                            try {
                                long size = FileUtils.getFileSize(new File(paths.get(i)));
                                if (size >= 5 * 1024 * 1024) {
                                    Toast.makeText(this, "文件大小不能超过5M", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(getApplicationContext(), aClass);
                        intent.putStringArrayListExtra(FileSelector.RESULT, paths);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "仅支持Excel和World文件", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case android.R.id.home:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void enableActionBarMode() {
        if (null == getActionBar()) {

            actionBarModel = false;

        } else {

            fileSelector.dismissOptionBar();

            fileSelector.setOnSelectCountChangeListener(new FileSelector.onSelectCountChangeListener() {
                @Override
                public void onSelectCountChange(int selectCount) {
                    if (selectCount > 0) {
                        ok.setVisible(true);
                    } else {
                        ok.setVisible(false);
                    }
                }
            });

            fileSelector.setOnListUpdataComplete(new FileSelector.onListUpdataComplete() {
                @Override
                public void onComplete() {
                    ok.setVisible(false);
                }
            });

            if (null == getActionBar()) {

                getActionBar().setDisplayHomeAsUpEnabled(true);

            } else {

                getActionBar().setDisplayHomeAsUpEnabled(true);

            }
        }
    }
}

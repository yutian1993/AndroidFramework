package com.yutian.mtkviewer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yutian.mtkviewer.config.AppValues;
import com.yutian.mtkviewer.dbcontrol.DBConfigController;
import com.yutian.utillib.CommonUtil.FileUtil;
import com.yutian.utillib.CommonUtil.SharedPreferenceUtil;
import com.yutian.utillib.CommonUtil.SqliteDataUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context m_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        m_context = this;
        initValue();
    }

    public void initValue() {
        String fileName = SharedPreferenceUtil.getDBName(this);
        if (fileName != null) {
            ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(R.id.nav_database).setTitle("DataBase：" + fileName);
        } else {
            ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(R.id.nav_database).setTitle("No DataBase.");
        }

//        if (!hasPermission())
//            requestAllPermissions();
    }

    public void requestAllPermissions() {
        requestPermission(AppValues.PERMISSION_MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, new Runnable() {
            @Override
            public void run() {
                requestPermission(AppValues.PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, new Runnable() {
                    @Override
                    public void run() {
                        //ConfigControl需要提前初始化
                        DBConfigController.getInstance(m_context);
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        //Nothing
                    }
                });
            }
        }, new Runnable() {
            @Override
            public void run() {
                //Nothing
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            if (!hasPermission())
                requestAllPermissions();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_database) {
            System.out.println("Database Show");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, AppValues.FILEBROWSER);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            if (requestCode == AppValues.FILEBROWSER) {
                Uri uri = data.getData();
                if (SqliteDataUtil.isSqliteDB(uri.getPath())) {
                    SharedPreferenceUtil.saveDBPath(this, uri.getPath());
                    ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(R.id.nav_database).setTitle("DataBase：" + FileUtil.getFileName(uri.getPath(), null));
                } else {
                    System.out.println("No SqliteDB");
                }
//                System.out.println(uri.getPath());
            }
//            //得到uri，后面就是将uri转化成file的过程。
//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
//            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            actualimagecursor.moveToFirst();
//            String img_path = actualimagecursor.getString(actual_image_column_index);
//            File file = new File(img_path);
//            Toast.makeText(MainActivity.this, file.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private Map<Integer, Runnable> allowablePermissionRunnables = new HashMap<>();
    private Map<Integer, Runnable> disallowablePermissionRunnables = new HashMap<>();

    /**
     * 请求权限
     *
     * @param id                   请求授权的id 唯一标识即可
     * @param permission           请求的权限
     * @param allowableRunnable    同意授权后的操作
     * @param disallowableRunnable 禁止权限后的操作
     */
    protected void requestPermission(int id, String permission, Runnable allowableRunnable, Runnable disallowableRunnable) {
        if (allowableRunnable == null) {
            throw new IllegalArgumentException("allowableRunnable == null");
        }

        allowablePermissionRunnables.put(id, allowableRunnable);
        if (disallowableRunnable != null) {
            disallowablePermissionRunnables.put(id, disallowableRunnable);
        }

        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //减少是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
                System.out.println("Show permission dialog!");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, id);
                return;
            } else {
                allowableRunnable.run();
            }
        } else {
            allowableRunnable.run();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Runnable allowRun = allowablePermissionRunnables.get(requestCode);
            allowRun.run();
            allowablePermissionRunnables.remove(requestCode);
        } else {
//            Runnable disallowRun = disallowablePermissionRunnables.get(requestCode);
//            disallowRun.run();
//            this.finish();
            System.out.println("No grant");
        }
    }

    private boolean hasPermission() {
        String[] permissionList = new String[]{
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean result = true;
        for (String permission : permissionList) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                result = false;
            }
        }
        return result;
    }
}

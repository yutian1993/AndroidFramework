package com.yutian.mtkviewer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yutian.mtkviewer.R;
import com.yutian.mtkviewer.adapter.TreeItemAdapter;
import com.yutian.mtkviewer.config.AppValues;
import com.yutian.mtkviewer.dbcontrol.DBConfigController;
import com.yutian.mtkviewer.dbcontrol.TreeItemController;
import com.yutian.mtkviewer.dbcontrol.greendao.TreeItem;
import com.yutian.mtkviewer.decoration.FloatingBarItemDecoration;
import com.yutian.utillib.CommonUtil.FileUtil;
import com.yutian.utillib.CommonUtil.SharedPreferenceUtil;
import com.yutian.utillib.CommonUtil.SqliteDataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;

    @Bind(R.id.m_recycleerview_list)
    RecyclerView mRecyclerView;
    private TreeItemAdapter mTreeItemAdapter = null;
    private TreeItemController mTreeItemController = null;

    private LinkedHashMap<Integer, String> mHeaderList;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


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

        mContext = this;
        initValue();
    }

    public void initValue() {
        String fileName = SharedPreferenceUtil.getDBName(this);
        if (fileName != null) {
            ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(R.id.nav_database).setTitle("DataBase：" + fileName);
        } else {
            ((NavigationView) findViewById(R.id.nav_view)).getMenu().findItem(R.id.nav_database).setTitle("No DataBase.");
        }

        if (!hasPermission())
            requestAllPermissions();
        else {
            DBConfigController.getInstance(this);
            mTreeItemController = TreeItemController.getInstance(this);
        }
    }

    public void generateList() {
        fetchData();

        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(
                new FloatingBarItemDecoration(this, mHeaderList));
    }

    public void requestAllPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, AppValues.PERMISSION_WRITE_EXTERNAL_STORAGE);
//        requestPermission(AppValues.PERMISSION_MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, new Runnable() {
//            @Override
//            public void run() {
//                requestPermission(AppValues.PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, new Runnable() {
//                    @Override
//                    public void run() {
//                        //ConfigControl需要提前初始化
//                        DBConfigController.getInstance(mContext);
//                    }
//                }, new Runnable() {
//                    @Override
//                    public void run() {
//                        //Nothing
//                    }
//                });
//            }
//        }, new Runnable() {
//            @Override
//            public void run() {
//                //Nothing
//            }
//        });
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

        if (id == R.id.nav_swlist) {
            if (mTreeItemController != null) {
                this.setTitle(R.string.title_software);
                generateDataList("SW");
            } else {
                Toast.makeText(this, "No database show!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_hw) {
            if (mTreeItemController != null) {
                this.setTitle(R.string.title_hardware);
                generateDataList("HW");
            } else {
                Toast.makeText(this, "No database show!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_functiontest) {
            if (mTreeItemController != null) {
                Toast.makeText(this, "Function remain!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No database show!", Toast.LENGTH_SHORT).show();
            }
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

                    //Update DB
                    DBConfigController.getInstance(this);
                    mTreeItemController = TreeItemController.getInstance(this);
                    generateList();
                } else {
                    Snackbar.make(mRecyclerView, "You choose is not a sqlite database!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
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

        System.out.println("Result size: " + grantResults.length);
        for (int result : grantResults) {
            System.out.println("Result: " + result);
        }

        System.out.println("PackageManager.PERMISSION_GRANTED: " + PackageManager.PERMISSION_GRANTED);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //ConfigControl需要提前初始化
            DBConfigController.getInstance(mContext);
            mTreeItemController = TreeItemController.getInstance(this);
//            Runnable allowRun = allowablePermissionRunnables.get(requestCode);
//            allowRun.run();
//            allowablePermissionRunnables.remove(requestCode);
        } else {
//            Runnable disallowRun = disallowablePermissionRunnables.get(requestCode);
//            disallowRun.run();
//            this.finish();
//            System.out.println("No grant");
            this.finish();
        }
    }

    private boolean hasPermission() {
        String[] permissionList = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean result = true;
        for (String permission : permissionList) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                System.out.println(permission + " not PERMISSION_GRANTED");
                result = false;
            }
        }
        return result;
    }


    private void fetchData() {
        mHeaderList = new LinkedHashMap<>();
    }

    private void generateDataList(String kind) {
        List<TreeItem> headerList = mTreeItemController.getFirstTreeByTopParent(kind);
        int headIndex = 0;
        List<TreeItem> dataList = new ArrayList<>();
        for (TreeItem item : headerList) {
            List<TreeItem> itemList = mTreeItemController.getSecondTreeByParent(item.getFARID());
            if (mHeaderList == null)
                mHeaderList = new LinkedHashMap<>();
            mHeaderList.put(headIndex, item.getNAME());
            headIndex += itemList.size();
            dataList.addAll(itemList);
        }

        if (mTreeItemAdapter == null) {
            mTreeItemAdapter = new TreeItemAdapter(LayoutInflater.from(this), null);
            mTreeItemAdapter.setOnTreeItemClickListener(new TreeItemAdapter.OnTreeItemClickListener() {
                @Override
                public void onTreeItemClicked(TreeItem item) {
                    Intent startIntent = new Intent(getApplicationContext(),
                            SecondItemsActivity.class);
                    startIntent.putExtra(getResources().getString(R.string.intent_itemid), item.getFARID());
                    startIntent.putExtra(getResources().getString(R.string.intent_itemname), item.getNAME());
                    startActivity(startIntent);
                }
            });
            mRecyclerView.setAdapter(mTreeItemAdapter);
        }
        mTreeItemAdapter.setNewData(dataList);
        mTreeItemAdapter.notifyDataSetChanged();
        mRecyclerView.invalidate();
    }
}

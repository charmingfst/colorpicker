package com.chm.myapplication;

import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chm.myapplication.R;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    Button btn;
    private Resources rs;
    private AssetManager amr;

    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.img);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn)
        {
            if (flag) {
                String dexpath= "";
                try {
                    //所需的资源的apk的APK路径
                    dexpath = getPackageManager().getApplicationInfo("com.bcxgps.baidumap", 0).sourceDir;
                    Toast.makeText(this, dexpath, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                loadRes(dexpath);
//                setImage(dexpath);
                flag = false;
            }else {
                amr =null;
                rs = null;
                iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                flag = true;
            }
        }


    }
    private void loadRes(String path) {
        try {
            amr = AssetManager.class.newInstance();
            Method addAssPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssPath.invoke(amr, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rs = new Resources(amr, super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());
        iv.setImageDrawable(rs.getDrawable(R.drawable.message_pay));
    }

    public Resources getResourcs(){
        return rs == null ? super.getResources():rs;

    }

    public AssetManager getAssets(){
        return amr == null ? super.getAssets(): amr;
    }
    private void setImage(String dexpath){
        DexClassLoader loader = new DexClassLoader(dexpath, getApplicationInfo().dataDir, null, this.getClass().getClassLoader());
        try {
            Class<?> clazz = loader.loadClass("com.bcxgps.baidumap.main.HellogpsActivity");
            Method getImageId = clazz.getMethod("getImageId");
            //调用静态方法
//            int ic_launcher = (Integer) getImageId.invoke(clazz);
            int ic_launcher = (int) getImageId.invoke(clazz.newInstance());
            iv.setImageDrawable(getResourcs().getDrawable(ic_launcher));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

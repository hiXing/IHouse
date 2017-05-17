package com.zufangwang.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zufangwang.base.Configs;
import com.zufangwang.base.DrawerBaseActivity;
import com.zufangwang.fragment.ReleaseHouseFragment;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.utils.FileTools;
import com.zufangwang.utils.SelectHeadTools;

import java.io.File;
import java.io.IOException;

/**
 * Created by Francis on 2016/4/27.
 */
public class ReleaseHouseActivity extends DrawerBaseActivity{
    private ReleaseHouseFragment houseReleaseFragment=new ReleaseHouseFragment();
    private Uri photoUri = null;
    @Override
    protected Fragment getLayoutFragment() {
        return houseReleaseFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void loadData() {

    }
    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle(getIntent().getStringExtra("house_type"));
        houseReleaseFragment.setHouse_type(getIntent().getStringExtra("house_type"));
    }
    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        initUri();
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
    //照片返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectHeadTools.startPhotoZoom(this,photoUri, 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data==null)
                    return;
                SelectHeadTools.startPhotoZoom(this, data.getData(), 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data==null)
                    return;
                Bitmap bit = data.getExtras().getParcelable("data");
                File file = FileTools.getFileByUri(this,photoUri);
                houseReleaseFragment.setImageView(bit);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //初始化照片路径
    private void initUri(){
        if(!FileTools.hasSdcard()){
            Toast.makeText(this,"没有找到SD卡，请检查SD卡是否存在",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = FileTools.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            Toast.makeText(this, "创建文件失败。", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    //发布成功后关掉当前Activity
    public void succeedRelease(){
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("信息尚未发布,确认离开吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.create().show();
        }
        return super.onKeyDown(keyCode, event);
    }
}

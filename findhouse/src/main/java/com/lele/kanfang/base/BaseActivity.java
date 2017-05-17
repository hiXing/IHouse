package com.lele.kanfang.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lele.kanfang.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private int permissionRequestCode = 88;

    public static final int RC_SETTINGS_SCREEN = 99;

    private PermissionCallback permissionCallback;

    private String[] permissions;

    private String permissionDes = "";

    public interface PermissionCallback {
        void hasPermission();
        void noPermission();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback callback, @NonNull String... permissions) {

        if(permissions == null || permissions.length == 0) return;

        this.permissionCallback = callback;

        this.permissionDes = permissionDes;

        this.permissions = permissions;

        requestPermission(permissionDes, permissionRequestCode, permissions);

    }

    private void requestPermission(final String permissionDes, final int permissionRequestCode, final String[] permissions) {
        if(EasyPermissions.hasPermissions(this, permissions)) {
            if(permissionCallback != null) {
                permissionCallback.hasPermission();
                permissionCallback = null;
            }
        }else {
            List<String> list = new ArrayList<>();
            Collections.addAll(list,permissions);
            EasyPermissions.requestPermissions(this, permissionDes, permissionRequestCode, permissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(permissionCallback != null) {
            permissionCallback.hasPermission();
            permissionCallback = null;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, permissionDes)
                    .setTitle(getString(R.string.hint))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }else if(permissionCallback != null) {
            permissionCallback.noPermission();
            permissionCallback = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            requestPermission(permissionDes, permissionRequestCode, permissions);
        }
    }

}

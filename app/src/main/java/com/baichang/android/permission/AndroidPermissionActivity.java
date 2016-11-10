package com.baichang.android.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AndroidPermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_android);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.permission_btn_test)
    void forget() {
        //PermissionUtils permission = PermissionUtils.getInstance(GestureLockActivity.this);
        PermissionUtils permission = new PermissionUtils(AndroidPermissionActivity.this);
        //6.0权限拍照测试
        AndroidPermissionDialog dialog = new AndroidPermissionDialog();
        dialog.setResetDialogListener(new AndroidPermissionDialog.ResetDialogListener() {
            @Override
            public void photo() {
                permission.setPermission(PermissionUtils.CAMERA, grant -> {
                    if (grant) {
                        PhotoUtil.choose(AndroidPermissionActivity.this, 1);
                    } else {
                        Toast.makeText(AndroidPermissionActivity.this, "没有相机权限", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void image() {
                permission.setPermission(PermissionUtils.READ, grant -> {
                    if (grant) {
                        PhotoUtil.choose(AndroidPermissionActivity.this, 0);
                    } else {
                        Toast.makeText(AndroidPermissionActivity.this, "没有相册权限", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void call() {
                permission.setPermission(PermissionUtils.CALL, grant -> {
                    if (grant) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "10086"));
                        startActivity(intent);
                    } else {
                        Toast.makeText(AndroidPermissionActivity.this, "没有拨打电话权限", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        dialog.show(getFragmentManager(), "test");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            //相册
            PhotoUtil.photoZoom(data.getData());
        } else if (requestCode == 101) {
            //拍照
            PhotoUtil.photoZoom(null);
        } else if (requestCode == 102 && resultCode != 0) {
            Toast.makeText(AndroidPermissionActivity.this, "图片路径：" + PhotoUtil.getPhotoPath(), Toast.LENGTH_SHORT).show();
            PhotoUtil.cleanActivity();
        }
    }

}

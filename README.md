# AndroidPermission6.0
🐼 AndroidPermission6.0动态权限
# How to use？
1. build.gradle  
 ``` compile 'com.tbruyelle.rxpermissions:rxpermissions:0.8.2@aar'```  
 ``` compile 'io.reactivex:rxandroid:1.2.1'```
2. PermissionUtils.class
 	```java
	public class PermissionUtils {
	//自定义权限字符
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String CALL = Manifest.permission.CALL_PHONE;
    public static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String LOCATION = Manifest.permission.LOCATION_HARDWARE;
    private static RxPermissions rxPermission;

    public PermissionUtils(Context context) {
        if (context == null) {
            throw new NullPointerException("Context not null");
        }
        if (rxPermission == null) {
            rxPermission = RxPermissions.getInstance(context);
        }
    }

    public static PermissionUtils getInstance(Context context) {
        return new PermissionUtils(context);
    }

    public void setPermission(String permission, Listener listener) {
        if (TextUtils.isEmpty(permission)) {
            throw new NullPointerException("permission not null");
        }
        if (listener == null) {
            throw new NullPointerException("PermissionListener not null");
        }
        if (TextUtils.equals(permission, CAMERA)) {
            rxPermission.request(CAMERA).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, CALL)) {
            rxPermission.request(CALL).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, WRITE)) {
            rxPermission.request(WRITE).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, READ)) {
            rxPermission.request(READ).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, CONTACTS)) {
            rxPermission.request(CONTACTS).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, LOCATION)) {
            rxPermission.request(LOCATION).subscribe(listener::isPermission);
        } else {
            throw new IllegalArgumentException("Please enter the correct permissions");
        }
    }

    public interface Listener {
        void isPermission(boolean grant);
    }
	```
3. Activity 
    ```java
    //PermissionUtils permission = PermissionUtils.getInstance(GestureLockActivity.this);
    PermissionUtils permission = new PermissionUtils(AndroidPermissionActivity.this);
    //6.0权限拍照测试
    AndroidPermissionDialog dialog = new AndroidPermissionDialog();
    dialog.setResetDialogListener(new AndroidPermissionDialog.ResetDialogListener() {
    @Override
    public void photo() {
        permission.setPermission(PermissionUtils.CAMERA, grant -> {
            if (grant) {
            	PhotoUtil.choose(AndroidPermissionActivity.this, 1)
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
    dialog.show(getFragmentManager(), "test");
```
4. Thanks [RxPermissions](https://github.com/tbruyelle/RxPermissions)

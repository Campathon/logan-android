package compathon.org.logan_android;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

/**
 * Created by Andy on 5/5/2018.
 */

public class Application extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Fabric.with(this, new Crashlytics());
    }

}

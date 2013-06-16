package net.ice4c.tuer;

import android.content.Intent;
import android.net.Uri;

import greendroid.app.GDApplication;

public class Application extends GDApplication {
    @Override
    public Class<?> getHomeActivityClass() {
        return MainActivity.class;
    }
    @Override
    public Intent getMainApplicationIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(net.ice4c.tuer.api.Uri.REDIRECT_URI));
    }
    
}

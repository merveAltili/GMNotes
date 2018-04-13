package com.example.merve.butterknife;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by merve on 10.04.2018.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

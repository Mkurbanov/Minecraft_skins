package com.mkurbanov.minecraftskins;

import android.app.Application;

import androidx.room.Room;

import com.mkurbanov.minecraftskins.data.config;
import com.mkurbanov.minecraftskins.data.database.AppDatabase;
import com.mkurbanov.minecraftskins.tools.Functions;

public class App extends Application {
    private Functions functions;
    private static App app;
    public AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-" + config.APP_NAME).build();
    }

    public static App getInstance() {
        if (app == null)
            app = new App();
        return app;
    }

    public Functions getFunctions() {
        if (functions == null)
            functions = new Functions();
        return functions;
    }
}

package de.puncty.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.puncty.lib.MeetupCollection;
import com.puncty.lib.Session;
import com.puncty.lib.UserCollection;

public class Puncty {
    public static final String BASE_URL = "https://puncty.johannespour.de";

    private static @Nullable Puncty instance;
    private final @NonNull Session session;

    public Puncty(@NonNull Session session) {
        instance = this;
        this.session = session;
    }

    public @NonNull Session getSession() {
        return session;
    }

    public @NonNull MeetupCollection getMeetupCollection() {
        return new MeetupCollection(session);
    }

    public @NonNull UserCollection getUserCollection() {
        return new UserCollection(session);
    }

    public static boolean exists() {
        return instance != null;
    }

    public static @NonNull Puncty create(Session session) {
        return new Puncty(session);
    }

    public static @Nullable Puncty getInstance() {
        return instance;
    }
}

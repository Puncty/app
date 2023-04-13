package de.puncty.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.puncty.lib.MeetupCollection;
import com.puncty.lib.Session;
import com.puncty.lib.UserCollection;

public class Puncty {
    private static @Nullable Puncty instance;
    private @NonNull Session session;

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

    public static @Nullable Puncty getInstance() {
        return instance;
    }
}

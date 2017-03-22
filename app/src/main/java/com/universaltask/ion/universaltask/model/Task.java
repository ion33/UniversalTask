package com.universaltask.ion.universaltask.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Objects;

import com.google.common.base.Strings;

import java.util.UUID;

public final  class Task {

    @NonNull
    private final String mId;
    @Nullable
    private final String mDescription;
    @Nullable
    private final String mTitle;
    private boolean mCompleted;

    public  Task(@NonNull String id,@Nullable String title,@Nullable String description, boolean completed ) {
        this.mId = id;
        this.mCompleted = completed;
        this.mDescription = description;
        this.mTitle = title;
    }

    ///use this constructor to make a new UTask
    public Task(@Nullable String title,@Nullable String description) {
        this(UUID.randomUUID().toString(), description, title, false);
    }

    ///use this constructor to make a new UTask
    public Task(@Nullable String title,@Nullable String description, boolean completed) {
        this(UUID.randomUUID().toString(), description, title, completed);
    }

    ///use this constructor to String a new UTask
    public Task(@NonNull String id,@Nullable String title,@Nullable String description) {
        this(id, description, title, false);
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public  String getDescription() {
        return mDescription;
    }

    @Nullable
    public  String getTitle() {
        return mTitle;
    }

    public String getFullTitle() {
        return mTitle + " : " + mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public void setCompleted(boolean completed) {
        this.mCompleted = completed;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) && Strings.isNullOrEmpty(mDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != getClass())
            return false;
        Task temp = (Task) o;

        return Objects.equals(temp.getDescription(), getDescription())
                && Objects.equals(temp.getTitle(), getTitle())
                && Objects.equals(temp.getId(), getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mDescription);
    }
}

/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.net;

import android.annotation.SuppressLint;
import android.net.TetheringManagerHidden.TetheringType;
import android.net.wifi.SoftApConfiguration;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/** The mapping of tethering interface and type. */
@SuppressLint("UnflaggedApi")
public final class TetheringInterface implements Parcelable {
    private final int mType;
    private final String mInterface;
    @Nullable private final SoftApConfiguration mSoftApConfig;

    @SuppressLint("UnflaggedApi")
    public TetheringInterface(@TetheringType int type, @NonNull String iface) {
        throw new RuntimeException("stub!");
    }

    public TetheringInterface(
            @TetheringType int type,
            @NonNull String iface,
            @Nullable SoftApConfiguration softApConfig) {
        throw new RuntimeException("stub!");
    }

    /** Get tethering type. */
    @SuppressLint("UnflaggedApi")
    public int getType() {
        throw new RuntimeException("stub!");
    }

    /** Get tethering interface. */
    @NonNull @SuppressLint("UnflaggedApi")
    public String getInterface() {
        throw new RuntimeException("stub!");
    }

    /**
     * Get the SoftApConfiguration provided for this interface, if any. This will only be populated
     * for apps with the same uid that specified the configuration, or apps with permission
     * android.Manifest.permission.NETWORK_SETTINGS.
     */
    @Nullable @SuppressLint("UnflaggedApi")
    public SoftApConfiguration getSoftApConfiguration() {
        throw new RuntimeException("stub!");
    }

    @Override
    @SuppressLint("UnflaggedApi")
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("stub!");
    }

    @Override
    @SuppressLint("UnflaggedApi")
    public int hashCode() {
        throw new RuntimeException("stub!");
    }

    @Override
    @SuppressLint("UnflaggedApi")
    public boolean equals(@Nullable Object obj) {
        throw new RuntimeException("stub!");
    }

    @Override
    @SuppressLint("UnflaggedApi")
    public int describeContents() {
        return 0;
    }

    @NonNull @SuppressLint("UnflaggedApi")
    public static final Creator<TetheringInterface> CREATOR =
            new Creator<TetheringInterface>() {
                @NonNull @Override
                @SuppressLint("UnflaggedApi")
                public TetheringInterface createFromParcel(@NonNull Parcel in) {
                    throw new RuntimeException("stub!");
                }

                @NonNull @Override
                @SuppressLint("UnflaggedApi")
                public TetheringInterface[] newArray(int size) {
                    throw new RuntimeException("stub!");
                }
            };

    @NonNull @Override
    public String toString() {
        throw new RuntimeException("stub!");
    }
}

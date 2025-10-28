/*
 * Copyright (C) 2019 The Android Open Source Project
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

package android.net.wifi;

import android.net.MacAddress;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.List;

public final class SoftApInfo implements Parcelable {

    public static final int CHANNEL_WIDTH_AUTO = -1;

    public static final int CHANNEL_WIDTH_INVALID = 0;

    public static final int CHANNEL_WIDTH_20MHZ_NOHT = 1;

    public static final int CHANNEL_WIDTH_20MHZ = 2;

    public static final int CHANNEL_WIDTH_40MHZ = 3;

    public static final int CHANNEL_WIDTH_80MHZ = 4;

    public static final int CHANNEL_WIDTH_80MHZ_PLUS_MHZ = 5;

    public static final int CHANNEL_WIDTH_160MHZ = 6;

    public static final int CHANNEL_WIDTH_2160MHZ = 7;

    public static final int CHANNEL_WIDTH_4320MHZ = 8;

    public static final int CHANNEL_WIDTH_6480MHZ = 9;

    public static final int CHANNEL_WIDTH_8640MHZ = 10;

    public static final int CHANNEL_WIDTH_320MHZ = 11;

    public int getFrequency() {
        throw new RuntimeException("stub!");
    }

    public void setFrequency(int freq) {
        throw new RuntimeException("stub!");
    }

    public int getBandwidth() {
        throw new RuntimeException("stub!");
    }

    public void setBandwidth(int bandwidth) {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Nullable public MacAddress getBssid() {
        throw new RuntimeException("stub!");
    }

    @Nullable public MacAddress getBssidInternal() {
        throw new RuntimeException("stub!");
    }

    public void setBssid(@Nullable MacAddress bssid) {
        throw new RuntimeException("stub!");
    }

    public void setWifiStandard(int wifiStandard) {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public int getWifiStandard() {
        throw new RuntimeException("stub!");
    }

    public int getWifiStandardInternal() {
        throw new RuntimeException("stub!");
    }

    public void setApInstanceIdentifier(@NonNull String apInstanceIdentifier) {
        throw new RuntimeException("stub!");
    }

    @Nullable public String getApInstanceIdentifier() {
        throw new RuntimeException("stub!");
    }

    public void setAutoShutdownTimeoutMillis(long idleShutdownTimeoutMillis) {
        throw new RuntimeException("stub!");
    }

    public long getAutoShutdownTimeoutMillis() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    public void setVendorData(@NonNull List<OuiKeyedData> vendorData) {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @NonNull public List<OuiKeyedData> getVendorData() {
        throw new RuntimeException("stub!");
    }

    public SoftApInfo(@Nullable SoftApInfo source) {
        throw new RuntimeException("stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("stub!");
    }

    @NonNull public static final Creator<SoftApInfo> CREATOR =
            new Creator<>() {
                public SoftApInfo createFromParcel(Parcel in) {
                    throw new RuntimeException("stub!");
                }

                public SoftApInfo[] newArray(int size) {
                    throw new RuntimeException("stub!");
                }
            };
}

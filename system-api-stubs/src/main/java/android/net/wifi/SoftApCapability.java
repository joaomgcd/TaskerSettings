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

import android.net.wifi.SoftApConfigurationHidden.BandType;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SoftApCapability implements Parcelable {

    public static final long SOFTAP_FEATURE_ACS_OFFLOAD = 1 << 0;

    public static final long SOFTAP_FEATURE_CLIENT_FORCE_DISCONNECT = 1 << 1;

    public static final long SOFTAP_FEATURE_WPA3_SAE = 1 << 2;

    @RequiresApi(Build.VERSION_CODES.S)
    public static final long SOFTAP_FEATURE_MAC_ADDRESS_CUSTOMIZATION = 1 << 3;

    @RequiresApi(Build.VERSION_CODES.S)
    public static final long SOFTAP_FEATURE_IEEE80211_AX = 1 << 4;

    @RequiresApi(Build.VERSION_CODES.S)
    public static final long SOFTAP_FEATURE_BAND_24G_SUPPORTED = 1 << 5;

    @RequiresApi(Build.VERSION_CODES.S)
    public static final long SOFTAP_FEATURE_BAND_5G_SUPPORTED = 1 << 6;

    @RequiresApi(Build.VERSION_CODES.S)
    public static final long SOFTAP_FEATURE_BAND_6G_SUPPORTED = 1 << 7;

    @RequiresApi(Build.VERSION_CODES.S)
    public static final long SOFTAP_FEATURE_BAND_60G_SUPPORTED = 1 << 8;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final long SOFTAP_FEATURE_IEEE80211_BE = 1 << 9;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final long SOFTAP_FEATURE_WPA3_OWE_TRANSITION = 1 << 10;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static final long SOFTAP_FEATURE_WPA3_OWE = 1 << 11;

    @Retention(RetentionPolicy.SOURCE)
    @LongDef(
            flag = true,
            value = {
                SOFTAP_FEATURE_ACS_OFFLOAD,
                SOFTAP_FEATURE_CLIENT_FORCE_DISCONNECT,
                SOFTAP_FEATURE_WPA3_SAE,
                SOFTAP_FEATURE_MAC_ADDRESS_CUSTOMIZATION,
                SOFTAP_FEATURE_IEEE80211_AX,
                SOFTAP_FEATURE_IEEE80211_BE,
                SOFTAP_FEATURE_BAND_24G_SUPPORTED,
                SOFTAP_FEATURE_BAND_5G_SUPPORTED,
                SOFTAP_FEATURE_BAND_6G_SUPPORTED,
                SOFTAP_FEATURE_BAND_60G_SUPPORTED,
                SOFTAP_FEATURE_WPA3_OWE_TRANSITION,
                SOFTAP_FEATURE_WPA3_OWE,
            })
    public @interface HotspotFeatures {}

    public void setCountryCode(String countryCode) {
        throw new RuntimeException("stub!");
    }

    public String getCountryCode() {
        throw new RuntimeException("stub!");
    }

    public int getMaxSupportedClients() {
        throw new RuntimeException("stub!");
    }

    public void setMaxSupportedClients(int maxClient) {
        throw new RuntimeException("stub!");
    }

    public boolean areFeaturesSupported(@HotspotFeatures long features) {
        throw new RuntimeException("stub!");
    }

    public void setSupportedFeatures(boolean value, @HotspotFeatures long features) {
        throw new RuntimeException("stub!");
    }

    public boolean setSupportedChannelList(
            @BandType int band, @Nullable int[] supportedChannelList) {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @NonNull public int[] getSupportedChannelList(@BandType int band) {
        throw new RuntimeException("stub!");
    }

    public SoftApCapability(@HotspotFeatures long features) {
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

    @NonNull public static final Creator<SoftApCapability> CREATOR =
            new Creator<>() {
                public SoftApCapability createFromParcel(Parcel in) {
                    throw new RuntimeException("stub!");
                }

                public SoftApCapability[] newArray(int size) {
                    return new SoftApCapability[size];
                }
            };
}

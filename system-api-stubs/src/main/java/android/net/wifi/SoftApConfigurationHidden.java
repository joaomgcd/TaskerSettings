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
import android.util.SparseIntArray;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import dev.rikka.tools.refine.RefineAs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@RefineAs(SoftApConfiguration.class)
public final class SoftApConfigurationHidden implements Parcelable {
    @VisibleForTesting static final int PSK_MIN_LEN = 8;

    @VisibleForTesting static final int PSK_MAX_LEN = 63;

    public static final int BAND_2GHZ = 1 << 0;

    public static final int BAND_5GHZ = 1 << 1;

    public static final int BAND_6GHZ = 1 << 2;

    public static final int BAND_60GHZ = 1 << 3;

    public static final int BAND_ANY = BAND_2GHZ | BAND_5GHZ | BAND_6GHZ;

    public static final long DEFAULT_TIMEOUT = -1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            flag = true,
            value = {
                BAND_2GHZ,
                BAND_5GHZ,
                BAND_6GHZ,
                BAND_60GHZ,
            })
    public @interface BandType {}

    public static int[] BAND_TYPES = {BAND_2GHZ, BAND_5GHZ, BAND_6GHZ, BAND_60GHZ};

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {RANDOMIZATION_NONE, RANDOMIZATION_PERSISTENT, RANDOMIZATION_NON_PERSISTENT})
    public @interface MacRandomizationSetting {}

    public static final int RANDOMIZATION_NONE = 0;

    public static final int RANDOMIZATION_PERSISTENT = 1;

    public static final int RANDOMIZATION_NON_PERSISTENT = 2;

    public static final int SECURITY_TYPE_OPEN = 0;

    public static final int SECURITY_TYPE_WPA2_PSK = 1;

    public static final int SECURITY_TYPE_WPA3_SAE_TRANSITION = 2;

    public static final int SECURITY_TYPE_WPA3_SAE = 3;

    public static final int SECURITY_TYPE_WPA3_OWE_TRANSITION = 4;

    public static final int SECURITY_TYPE_WPA3_OWE = 5;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            value = {
                SECURITY_TYPE_OPEN,
                SECURITY_TYPE_WPA2_PSK,
                SECURITY_TYPE_WPA3_SAE_TRANSITION,
                SECURITY_TYPE_WPA3_SAE,
                SECURITY_TYPE_WPA3_OWE_TRANSITION,
                SECURITY_TYPE_WPA3_OWE,
            })
    public @interface SecurityType {}

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull public static final Creator<SoftApConfigurationHidden> CREATOR =
            new Creator<>() {
                @Override
                public SoftApConfigurationHidden createFromParcel(Parcel in) {
                    throw new RuntimeException("stub!");
                }

                @Override
                public SoftApConfigurationHidden[] newArray(int size) {
                    throw new RuntimeException("stub!");
                }
            };

    @Nullable @Deprecated
    public String getSsid() {
        throw new RuntimeException("stub!");
    }

    @Nullable public WifiSsid getWifiSsid() {
        throw new RuntimeException("stub!");
    }

    @NonNull @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public List<ScanResult.InformationElement> getVendorElements() {
        throw new RuntimeException("stub!");
    }

    public List<ScanResult.InformationElement> getVendorElementsInternal() {
        throw new RuntimeException("stub!");
    }

    @Nullable public MacAddress getBssid() {
        throw new RuntimeException("stub!");
    }

    @Nullable public String getPassphrase() {
        throw new RuntimeException("stub!");
    }

    public boolean isHiddenSsid() {
        throw new RuntimeException("stub!");
    }

    @Deprecated
    public @BandType int getBand() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public @NonNull int[] getBands() {
        throw new RuntimeException("stub!");
    }

    @Deprecated
    public int getChannel() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public @NonNull SparseIntArray getChannels() {
        throw new RuntimeException("stub!");
    }

    public @SecurityType int getSecurityType() {
        throw new RuntimeException("stub!");
    }

    public int getMaxNumberOfClients() {
        throw new RuntimeException("stub!");
    }

    public boolean isAutoShutdownEnabled() {
        throw new RuntimeException("stub!");
    }

    public long getShutdownTimeoutMillis() {
        throw new RuntimeException("stub!");
    }

    public boolean isClientControlByUserEnabled() {
        throw new RuntimeException("stub!");
    }

    @NonNull public List<MacAddress> getBlockedClientList() {
        throw new RuntimeException("stub!");
    }

    @NonNull public List<MacAddress> getAllowedClientList() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @MacRandomizationSetting
    public int getMacRandomizationSetting() {
        throw new RuntimeException("stub!");
    }

    @MacRandomizationSetting
    public int getMacRandomizationSettingInternal() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public boolean isBridgedModeOpportunisticShutdownEnabled() {
        throw new RuntimeException("stub!");
    }

    public boolean isBridgedModeOpportunisticShutdownEnabledInternal() {
        throw new RuntimeException("stub!");
    }

    public boolean isIeee80211axEnabledInternal() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public boolean isIeee80211axEnabled() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public boolean isIeee80211beEnabled() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @NonNull public int[] getAllowedAcsChannels(@BandType int band) {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public int getMaxChannelBandwidth() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.S)
    public boolean isUserConfiguration() {
        throw new RuntimeException("stub!");
    }

    public @NonNull MacAddress getPersistentRandomizedMacAddress() {
        throw new RuntimeException("stub!");
    }

    public boolean isUserConfigurationInternal() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public long getBridgedModeOpportunisticShutdownTimeoutMillis() {
        throw new RuntimeException("stub!");
    }

    public long getBridgedModeOpportunisticShutdownTimeoutMillisInternal() {
        throw new RuntimeException("stub!");
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @NonNull public List<OuiKeyedData> getVendorData() {
        throw new RuntimeException("stub!");
    }

    @Nullable public WifiConfiguration toWifiConfiguration() {
        throw new RuntimeException("stub!");
    }

    public static final class Builder {

        public Builder() {
            throw new RuntimeException("stub!");
        }

        public Builder(@NonNull SoftApConfigurationHidden other) {
            throw new RuntimeException("stub!");
        }

        @NonNull public SoftApConfigurationHidden build() {
            throw new RuntimeException("stub!");
        }

        @NonNull @Deprecated
        public Builder setSsid(@Nullable String ssid) {
            throw new RuntimeException("stub!");
        }

        @NonNull @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        public Builder setWifiSsid(@Nullable WifiSsid wifiSsid) {
            throw new RuntimeException("stub!");
        }

        @NonNull @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        public Builder setVendorElements(
                @NonNull List<ScanResult.InformationElement> vendorElements) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setBssid(@Nullable MacAddress bssid) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setPassphrase(@Nullable String passphrase, @SecurityType int securityType) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setHiddenSsid(boolean hiddenSsid) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setBand(@BandType int band) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.S)
        @NonNull public Builder setBands(@NonNull int[] bands) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setChannel(int channel, @BandType int band) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.S)
        @NonNull public Builder setChannels(@NonNull SparseIntArray channels) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setMaxNumberOfClients(@IntRange(from = 0) int maxNumberOfClients) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setAutoShutdownEnabled(boolean enable) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setShutdownTimeoutMillis(@IntRange(from = -1) long timeoutMillis) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setClientControlByUserEnabled(boolean enabled) {
            throw new RuntimeException("stub!");
        }

        @NonNull @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        public Builder setAllowedAcsChannels(@BandType int band, @NonNull int[] channels) {
            throw new RuntimeException("stub!");
        }

        @NonNull @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        public Builder setMaxChannelBandwidth(int maxChannelBandwidth) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setAllowedClientList(@NonNull List<MacAddress> allowedClientList) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setBlockedClientList(@NonNull List<MacAddress> blockedClientList) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.S)
        @NonNull public Builder setMacRandomizationSetting(
                @MacRandomizationSetting int macRandomizationSetting) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.S)
        @NonNull public Builder setBridgedModeOpportunisticShutdownEnabled(boolean enable) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.S)
        @NonNull public Builder setIeee80211axEnabled(boolean enable) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @NonNull public Builder setIeee80211beEnabled(boolean enable) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setUserConfiguration(boolean isUserConfigured) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @NonNull public Builder setBridgedModeOpportunisticShutdownTimeoutMillis(
                @IntRange(from = -1) long timeoutMillis) {
            throw new RuntimeException("stub!");
        }

        @NonNull public Builder setRandomizedMacAddress(@NonNull MacAddress mac) {
            throw new RuntimeException("stub!");
        }

        @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
        @NonNull public Builder setVendorData(@NonNull List<OuiKeyedData> vendorData) {
            throw new RuntimeException("stub!");
        }
    }
}

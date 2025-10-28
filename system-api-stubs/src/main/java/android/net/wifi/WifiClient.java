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
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public final class WifiClient implements Parcelable {

    @NonNull public MacAddress getMacAddress() {
        throw new RuntimeException("stub!");
    }

    @NonNull public String getApInstanceIdentifier() {
        throw new RuntimeException("stub!");
    }

    public WifiClient(@NonNull MacAddress macAddress, @NonNull String apInstanceIdentifier) {
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

    @NonNull public static final Creator<WifiClient> CREATOR =
            new Creator<>() {
                public WifiClient createFromParcel(Parcel in) {
                    throw new RuntimeException("stub!");
                }

                public WifiClient[] newArray(int size) {
                    throw new RuntimeException("stub!");
                }
            };
}

/*
 * Copyright (C) 2020 The Android Open Source Project
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

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.List;

/** Initial information reported by tethering upon callback registration. */
public class TetheringCallbackStartedParcel implements Parcelable {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public long supportedTypes = 0L;

    public Network upstreamNetwork;
    public TetheringConfigurationParcel config;
    public TetherStatesParcel states;
    public List<TetheredClient> tetheredClients;
    public int offloadStatus = 0;

    public static final Creator<TetheringCallbackStartedParcel> CREATOR =
            new Creator<TetheringCallbackStartedParcel>() {
                @Override
                public TetheringCallbackStartedParcel createFromParcel(Parcel in) {
                    throw new RuntimeException("stub!");
                }

                @Override
                public TetheringCallbackStartedParcel[] newArray(int size) {
                    throw new RuntimeException("stub!");
                }
            };

    @Override
    public final void writeToParcel(@NonNull Parcel _aidl_parcel, int _aidl_flag) {
        throw new RuntimeException("stub!");
    }

    public final void readFromParcel(Parcel _aidl_parcel) {
        throw new RuntimeException("stub!");
    }

    @Override
    public int describeContents() {
        throw new RuntimeException("stub!");
    }
}

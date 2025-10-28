/*
 * Copyright (C) 2024 The Android Open Source Project
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

import android.net.TetheringManager;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class SoftApState implements Parcelable {

    public SoftApState(
            int state,
            int failureReason,
            @Nullable TetheringManager.TetheringRequest tetheringRequest,
            @Nullable String iface) {
        throw new RuntimeException("stub!");
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull public static final Creator<SoftApState> CREATOR =
            new Creator<>() {
                @Override
                @NonNull public SoftApState createFromParcel(Parcel in) {
                    throw new RuntimeException("stub!");
                }

                @Override
                @NonNull public SoftApState[] newArray(int size) {
                    throw new RuntimeException("stub!");
                }
            };

    public int getState() {
        throw new RuntimeException("stub!");
    }

    public int getFailureReason() {
        throw new RuntimeException("stub!");
    }

    public int getFailureReasonInternal() {
        throw new RuntimeException("stub!");
    }

    @Nullable public TetheringManager.TetheringRequest getTetheringRequest() {
        throw new RuntimeException("stub!");
    }

    @Nullable public String getIface() {
        throw new RuntimeException("stub!");
    }
}

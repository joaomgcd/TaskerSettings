/*
 * Copyright (C) 2023 The Android Open Source Project
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

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;

public final class OuiKeyedData implements Parcelable {

    public int getOui() {
        throw new RuntimeException("stub!");
    }

    public @NonNull PersistableBundle getData() {
        throw new RuntimeException("stub!");
    }

    public boolean validate() {
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

    OuiKeyedData(@NonNull Parcel in) {
        throw new RuntimeException("stub!");
    }

    @NonNull public static final Parcelable.Creator<OuiKeyedData> CREATOR =
            new Parcelable.Creator<>() {
                @Override
                public OuiKeyedData createFromParcel(Parcel in) {
                    throw new RuntimeException("stub!");
                }

                @Override
                public OuiKeyedData[] newArray(int size) {
                    throw new RuntimeException("stub!");
                }
            };

    public static final class Builder {
        public Builder(int oui, @NonNull PersistableBundle data) {
            throw new RuntimeException("stub!");
        }

        @NonNull public OuiKeyedData build() {
            throw new RuntimeException("stub!");
        }
    }
}

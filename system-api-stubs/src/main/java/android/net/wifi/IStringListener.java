/*
 * Copyright (C) 2022 The Android Open Source Project
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

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.NonNull;

/** Interface for IStringListener. */
public interface IStringListener extends IInterface {
    abstract class Stub extends Binder implements IStringListener {
        /** Construct the stub at attach it to the interface. */
        public Stub() {
            throw new RuntimeException("stub!");
        }

        /**
         * Cast an IBinder object into an android.net.wifi.IStringListener interface, generating a
         * proxy if needed.
         */
        public static IStringListener asInterface(IBinder obj) {
            throw new RuntimeException("stub!");
        }

        @Override
        public android.os.IBinder asBinder() {
            throw new RuntimeException("stub!");
        }

        @Override
        public boolean onTransact(int code, @NonNull Parcel data, Parcel reply, int flags)
                throws RemoteException {
            throw new RuntimeException("stub!");
        }
    }

    void onResult(String value) throws RemoteException;
}

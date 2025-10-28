/*
 * Copyright (C) 2008 The Android Open Source Project
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

public interface IWifiManager extends IInterface {
    /** Local-side IPC implementation stub class. */
    abstract class Stub extends Binder implements IWifiManager {
        public Stub() {
            throw new RuntimeException("stub!");
        }

        /**
         * Cast an IBinder object into an android.net.wifi.IWifiManager interface, generating a
         * proxy if needed.
         */
        public static IWifiManager asInterface(IBinder obj) {
            throw new RuntimeException("stub!");
        }

        @Override
        public IBinder asBinder() {
            throw new RuntimeException("stub!");
        }

        @Override
        public boolean onTransact(int code, @NonNull Parcel data, Parcel reply, int flags)
                throws RemoteException {
            throw new RuntimeException("stub!");
        }
    }

    boolean is5GHzBandSupported();

    boolean is6GHzBandSupported();

    boolean validateSoftApConfiguration(SoftApConfiguration config);

    SoftApConfiguration getSoftApConfiguration();

    void queryLastConfiguredTetheredApPassphraseSinceBoot(IStringListener listener);

    boolean setSoftApConfiguration(SoftApConfiguration softApConfig, String packageName);

    // Used on Android S and above
    void registerSoftApCallback(ISoftApCallback callback);

    // Used on Android R
    void registerSoftApCallback(IBinder binder, ISoftApCallback callback, int callbackIdentifier);

    // Used on few Android R versions (OEM ROMs based on an older AOSP commit)
    void registerSoftApCallback(IBinder binder, ISoftApCallback callback);

    // Used on Android S and above
    void unregisterSoftApCallback(ISoftApCallback callback);

    // Used on Android R
    void unregisterSoftApCallback(int callbackIdentifier);
}

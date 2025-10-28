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

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import androidx.annotation.NonNull;

public interface ITetheringConnector extends IInterface {
    /** Local-side IPC implementation stub class. */
    abstract class Stub extends Binder implements ITetheringConnector {
        public Stub() {
            throw new RuntimeException("stub!");
        }

        /**
         * Cast an IBinder object into an android.net.ITetheringConnector interface, generating a
         * proxy if needed.
         */
        public static ITetheringConnector asInterface(IBinder obj) {
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

        public static final java.lang.String DESCRIPTOR = "android.net.ITetheringConnector";
    }

    void registerTetheringEventCallback(ITetheringEventCallback callback, String callerPkg);

    void unregisterTetheringEventCallback(ITetheringEventCallback callback, String callerPkg);

    void startTethering(
            TetheringRequestParcel request,
            String callerPkg,
            String callingAttributionTag,
            IIntResultListener receiver);

    void startTethering(
            TetheringRequestParcel request, String callerPkg, IIntResultListener receiver);

    void startTethering(
            int type, ResultReceiver receiver, boolean showProvisioningUi, String callerPkg);

    void startTethering(int type, ResultReceiver receiver, boolean showProvisioningUi);

    void stopTethering(
            int type, String callerPkg, String callingAttributionTag, IIntResultListener receiver);

    void stopTethering(int type, String callerPkg, IIntResultListener receiver);

    void stopTethering(int type);
}

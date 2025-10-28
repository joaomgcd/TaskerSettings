/*
 * Copyright (C) 2018 The Android Open Source Project
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
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.List;
import java.util.Map;

/** Interface for Soft AP callback. */
public interface ISoftApCallback extends IInterface {
    /** Local-side IPC implementation stub class. */
    abstract class Stub extends Binder implements ISoftApCallback {
        public Stub() {
            throw new RuntimeException("stub!");
        }

        /**
         * Cast an IBinder object into an android.net.wifi.ISoftApCallback interface, generating a
         * proxy if needed.
         */
        public static ISoftApCallback asInterface(IBinder obj) {
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

        public static final java.lang.String DESCRIPTOR = "android.net.wifi.ISoftApCallback";
    }

    /**
     * Service to manager callback providing current soft AP state. The possible parameter values
     * listed are defined in WifiManager.java
     *
     * @param state SoftApState
     */
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    void onStateChanged(SoftApState state) throws RemoteException;

    /**
     * Service to manager callback providing current soft AP state. The possible parameter values
     * listed are defined in WifiManager.java
     *
     * @param state new AP state. One of WIFI_AP_STATE_DISABLED, WIFI_AP_STATE_DISABLING,
     *     WIFI_AP_STATE_ENABLED, WIFI_AP_STATE_ENABLING, WIFI_AP_STATE_FAILED
     * @param failureReason reason when in failed state. One of SAP_START_FAILURE_GENERAL,
     *     SAP_START_FAILURE_NO_CHANNEL
     */
    @Deprecated(since = "VANILLA_ICE_CREAM")
    void onStateChanged(int state, int failureReason);

    /**
     * Service to manager callback providing informations of softap.
     *
     * @param infos The currently {@link SoftApInfo} in each AP instance.
     * @param clients The currently connected clients in each AP instance.
     * @param isBridged whether or not the current AP enabled on bridged mode.
     * @param isRegistration whether or not the callbackk was triggered when register.
     */
    @RequiresApi(Build.VERSION_CODES.S)
    void onConnectedClientsOrInfoChanged(
            Map<String, SoftApInfo> infos,
            Map<String, List<WifiClient>> clients,
            boolean isBridged,
            boolean isRegistration)
            throws RemoteException;

    /**
     * Service to manager callback providing connected client's information.
     *
     * @param clients the currently connected clients
     */
    @Deprecated(since = "S")
    void onConnectedClientsChanged(List<WifiClient> clients);

    /**
     * Service to manager callback providing information of softap.
     *
     * @param softApInfo is the softap information. {@link SoftApInfo}
     */
    @Deprecated(since = "S")
    void onInfoChanged(SoftApInfo softApInfo);

    /**
     * Service to manager callback providing informations of softap.
     *
     * @param softApInfoList is the list of the softap informations. {@link SoftApInfo}
     */
    @Deprecated(since = "S")
    void onInfoListChanged(List<SoftApInfo> softApInfoList);

    /**
     * Service to manager callback providing capability of softap.
     *
     * @param capability is the softap capability. {@link SoftApCapability}
     */
    void onCapabilityChanged(SoftApCapability capability) throws RemoteException;

    /**
     * Service to manager callback providing blocked client of softap with specific reason code.
     *
     * @param client the currently blocked client.
     * @param blockedReason one of blocked reason from WifiManager.SapClientBlockedReason
     */
    void onBlockedClientConnecting(WifiClient client, int blockedReason) throws RemoteException;

    /**
     * Service to manager callback providing clients that disconnected from the softap.
     *
     * @param info information about the AP instance
     * @param clients the disconnected clients of the AP instance
     */
    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    void onClientsDisconnected(SoftApInfo info, List<WifiClient> clients);
}

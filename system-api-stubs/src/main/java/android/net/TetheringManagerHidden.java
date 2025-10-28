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
package android.net;

import android.annotation.SuppressLint;
import android.net.wifi.SoftApConfiguration;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dev.rikka.tools.refine.RefineAs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

/**
 * This class provides the APIs to control the tethering service.
 *
 * <p>The primary responsibilities of this class are to provide the APIs for applications to start
 * tethering, stop tethering, query configuration and query status.
 */
@RefineAs(TetheringManager.class)
@SuppressLint({"NotCloseable", "UnflaggedApi"})
public class TetheringManagerHidden {

    /**
     * Indicates that this tethering connection will provide connectivity beyond this device (e.g.,
     * global Internet access).
     */
    public static final int CONNECTIVITY_SCOPE_GLOBAL = 1;

    /** Indicates that this tethering connection will only provide local connectivity. */
    public static final int CONNECTIVITY_SCOPE_LOCAL = 2;

    /** Wifi tethering type. */
    public static final int TETHERING_WIFI = 0;

    /** USB tethering type. */
    public static final int TETHERING_USB = 1;

    /** Bluetooth tethering type. */
    public static final int TETHERING_BLUETOOTH = 2;

    /**
     * Wifi P2p tethering type. Wifi P2p tethering is seWt through events automatically, and don't
     * need to start from #startTethering.
     */
    public static final int TETHERING_WIFI_P2P = 3;

    /** Ncm local tethering type. */
    public static final int TETHERING_NCM = 4;

    /** Ethernet tethering type. */
    public static final int TETHERING_ETHERNET = 5;

    /**
     * VIRTUAL tethering type. This tethering type is for providing external network to virtual
     * machines running on top of Android devices, which are created and managed by AVF(Android
     * Virtualization Framework).
     */
    public static final int TETHERING_VIRTUAL = 7;

    /** Error types */
    public static final int TETHER_ERROR_NO_ERROR = 0;

    public static final int TETHER_ERROR_SERVICE_UNAVAIL = 2;
    public static final int TETHER_ERROR_UNSUPPORTED = 3;
    public static final int TETHER_ERROR_INTERNAL_ERROR = 5;
    public static final int TETHER_ERROR_NO_CHANGE_TETHERING_PERMISSION = 14;
    public static final int TETHER_ERROR_UNKNOWN_TYPE = 16;
    public static final int TETHER_ERROR_UNKNOWN_REQUEST = 17;
    public static final int TETHER_ERROR_DUPLICATE_REQUEST = 18;

    /**
     * Starts tethering and runs tether provisioning for the given type if needed. If provisioning
     * fails, stopTethering will be called automatically.
     *
     * @param request a {@link TetheringRequest} which can specify the preferred configuration.
     * @param executor {@link Executor} to specify the thread upon which the callback of
     *     TetheringRequest will be invoked.
     * @param callback A callback that will be called to indicate the success status of the
     *     tethering start request.
     */
    public void startTethering(
            @NonNull final TetheringRequest request,
            @NonNull final Executor executor,
            @NonNull final StartTetheringCallback callback) {
        throw new RuntimeException("stub!");
    }

    /**
     * Starts tethering and runs tether provisioning for the given type if needed. If provisioning
     * fails, stopTethering will be called automatically.
     *
     * @param type The tethering type, on of the {@code TetheringManager#TETHERING_*} constants.
     * @param executor {@link Executor} to specify the thread upon which the callback of
     *     TetheringRequest will be invoked.
     */
    public void startTethering(
            int type,
            @NonNull final Executor executor,
            @NonNull final StartTetheringCallback callback) {
        throw new RuntimeException("stub!");
    }

    /**
     * Stops tethering for the given type. Also cancels any provisioning rechecks for that type if
     * applicable.
     */
    public void stopTethering(@TetheringType final int type) {
        throw new RuntimeException("stub!");
    }

    /**
     * Stops tethering for the given request. Operation will fail with {@link
     * #TETHER_ERROR_UNKNOWN_REQUEST} if there is no request that matches it.
     */
    public void stopTethering(
            @NonNull TetheringRequest request,
            @NonNull final Executor executor,
            @NonNull final StopTetheringCallback callback) {
        throw new UnsupportedOperationException();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            value = {
                TETHERING_WIFI,
                TETHERING_USB,
                TETHERING_BLUETOOTH,
                TETHERING_WIFI_P2P,
                TETHERING_NCM,
                TETHERING_ETHERNET,
                TETHERING_VIRTUAL,
            })
    public @interface TetheringType {}

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            value = {
                TETHER_ERROR_SERVICE_UNAVAIL,
                TETHER_ERROR_UNSUPPORTED,
                TETHER_ERROR_INTERNAL_ERROR,
                TETHER_ERROR_NO_CHANGE_TETHERING_PERMISSION,
                TETHER_ERROR_UNKNOWN_TYPE,
                TETHER_ERROR_DUPLICATE_REQUEST,
            })
    public @interface StartTetheringError {}

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            value = {
                TETHER_ERROR_NO_ERROR,
                TETHER_ERROR_UNKNOWN_REQUEST,
            })
    public @interface StopTetheringError {}

    /** Connectivity scopes for {@link TetheringRequest.Builder#setConnectivityScope}. */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            value = {
                CONNECTIVITY_SCOPE_GLOBAL,
                CONNECTIVITY_SCOPE_LOCAL,
            })
    public @interface ConnectivityScope {}

    public interface StartTetheringCallback {
        /** Called when tethering has been successfully started. */
        default void onTetheringStarted() {}

        /**
         * Called when starting tethering failed.
         *
         * @param error The error that caused the failure.
         */
        default void onTetheringFailed(@StartTetheringError final int error) {}
    }

    /**
     * Callback for use with {@link #stopTethering} to find out whether stop tethering succeeded.
     */
    public interface StopTetheringCallback {
        /** Called when tethering has been successfully stopped. */
        default void onStopTetheringSucceeded() {}

        /**
         * Called when starting tethering failed.
         *
         * @param error The error that caused the failure.
         */
        default void onStopTetheringFailed(@StopTetheringError final int error) {}
    }

    /**
     * Use with {@link #startTethering} to specify additional parameters when starting tethering.
     */
    public static final class TetheringRequest implements Parcelable {
        public TetheringRequest(@NonNull final TetheringRequestParcel request) {
            throw new RuntimeException("stub!");
        }

        private TetheringRequest(@NonNull Parcel in) {
            throw new RuntimeException("stub!");
        }

        /** Get a TetheringRequestParcel from the configuration */
        public TetheringRequestParcel getParcel() {
            throw new RuntimeException("stub!");
        }

        @NonNull public static final Creator<TetheringRequest> CREATOR =
                new Creator<>() {
                    @Override
                    public TetheringRequest createFromParcel(@NonNull Parcel in) {
                        return new TetheringRequest(in);
                    }

                    @Override
                    public TetheringRequest[] newArray(int size) {
                        return new TetheringRequest[size];
                    }
                };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            throw new RuntimeException("stub!");
        }

        /** Builder used to create TetheringRequest. */
        public static class Builder {

            /** Default constructor of Builder. */
            public Builder(@TetheringType final int type) {
                throw new RuntimeException("stub!");
            }

            /**
             * Configure tethering with static IPv4 assignment.
             *
             * <p>A DHCP server will be started, but will only be able to offer the client address.
             * The two addresses must be in the same prefix.
             *
             * @param localIPv4Address The preferred local IPv4 link address to use.
             * @param clientAddress The static client address.
             */
            @NonNull public Builder setStaticIpv4Addresses(
                    @NonNull final LinkAddress localIPv4Address,
                    @NonNull final LinkAddress clientAddress) {
                throw new RuntimeException("stub!");
            }

            /** Start tethering without entitlement checks. */
            @NonNull public Builder setExemptFromEntitlementCheck(boolean exempt) {
                throw new RuntimeException("stub!");
            }

            /**
             * If an entitlement check is needed, sets whether to show the entitlement UI or to
             * perform a silent entitlement check. By default, the entitlement UI is shown.
             */
            @NonNull public Builder setShouldShowEntitlementUi(boolean showUi) {
                throw new RuntimeException("stub!");
            }

            /**
             * Sets the name of the interface. Currently supported only for - {@link
             * #TETHERING_VIRTUAL}. - {@link #TETHERING_WIFI} (for Local-only Hotspot) - {@link
             * #TETHERING_WIFI_P2P}
             */
            public Builder setInterfaceName(@Nullable final String interfaceName) {
                throw new RuntimeException("stub!");
            }

            /** Sets the connectivity scope to be provided by this tethering downstream. */
            @NonNull public Builder setConnectivityScope(@ConnectivityScope int scope) {
                throw new RuntimeException("stub!");
            }

            /**
             * Set the desired SoftApConfiguration for {@link #TETHERING_WIFI}. If this is null or
             * not set, then the persistent tethering SoftApConfiguration from WifiManager will be
             * used. If TETHERING_WIFI is already enabled and a new request is made with a different
             * SoftApConfiguration, the request will be accepted if the device can support an
             * additional tethering Wi-Fi AP interface. Otherwise, the request will be rejected.
             * Non-system callers using TETHERING_WIFI must specify a SoftApConfiguration.
             *
             * @param softApConfig SoftApConfiguration to use.
             * @throws IllegalArgumentException if the tethering type isn't TETHERING_WIFI.
             */
            @NonNull public Builder setSoftApConfiguration(@Nullable SoftApConfiguration softApConfig) {
                throw new RuntimeException("stub!");
            }

            /** Build {@link TetheringRequest} with the currently set configuration. */
            @NonNull public TetheringRequest build() {
                throw new RuntimeException("stub!");
            }
        }
    }
}

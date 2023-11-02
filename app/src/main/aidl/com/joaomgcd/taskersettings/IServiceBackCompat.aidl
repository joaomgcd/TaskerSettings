package com.joaomgcd.taskersettings;
import com.joaomgcd.taskersettings.IServiceBackCompatCallback;

interface IServiceBackCompat {
    void doBackCompatAction(in Bundle bundle, in IServiceBackCompatCallback callback);
}
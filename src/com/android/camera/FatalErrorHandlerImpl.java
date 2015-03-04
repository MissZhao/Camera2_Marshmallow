/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.android.camera;

import android.app.Activity;

import com.android.camera.debug.Log;
import com.android.camera.stats.UsageStatistics;
import com.android.camera.util.CameraUtil;
import com.google.common.logging.eventprotos;

public final class FatalErrorHandlerImpl implements FatalErrorHandler {
    private static final Log.Tag TAG = new Log.Tag("FatalErrorHandler");

    private final Activity mActivity;

    public FatalErrorHandlerImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onMediaStorageFailure() {
        // Log a stack trace to be sure we can track the source.
        Log.e(TAG, "Handling Media Storage Failure:", new Exception());

        // Log the error
        UsageStatistics.instance().storageWarning(Storage.ACCESS_FAILURE);

        Reason reason = Reason.MEDIA_STORAGE_FAILURE;
        boolean finishActivity = reason.doesFinishActivity();
        CameraUtil.showError(mActivity, reason.getDialogMsgId(), reason.getFeedbackMsgId(),
                finishActivity);
    }

    @Override
    public void onCameraOpenFailure() {
        // Log a stack trace to be sure we can track the source.
        Log.e(TAG, "Handling Camera Open Failure:", new Exception());

        UsageStatistics.instance().cameraFailure(
                eventprotos.CameraFailure.FailureReason.OPEN_FAILURE, null,
                UsageStatistics.NONE, UsageStatistics.NONE);

        Reason reason = Reason.CANNOT_CONNECT_TO_CAMERA;
        boolean finishActivity = reason.doesFinishActivity();
        CameraUtil.showError(mActivity, reason.getDialogMsgId(), reason.getFeedbackMsgId(),
                finishActivity);
    }

    @Override
    public void onCameraReconnectFailure() {
        // Log a stack trace to be sure we can track the source.
        Log.e(TAG, "Handling Camera Reconnect Failure:", new Exception());

        UsageStatistics.instance().cameraFailure(
                eventprotos.CameraFailure.FailureReason.RECONNECT_FAILURE, null,
                UsageStatistics.NONE, UsageStatistics.NONE);

        Reason reason = Reason.CANNOT_CONNECT_TO_CAMERA;
        boolean finishActivity = reason.doesFinishActivity();
        CameraUtil.showError(mActivity, reason.getDialogMsgId(), reason.getFeedbackMsgId(),
                finishActivity);
    }

    @Override
    public void onGenericCameraAccessFailure() {
        // Log a stack trace to be sure we can track the source.
        Log.e(TAG, "Handling Camera Access Failure:", new Exception());

        UsageStatistics.instance().cameraFailure(
                eventprotos.CameraFailure.FailureReason.UNKNOWN_REASON, null,
                UsageStatistics.NONE, UsageStatistics.NONE);

        Reason reason = Reason.CANNOT_CONNECT_TO_CAMERA;
        boolean finishActivity = reason.doesFinishActivity();
        CameraUtil.showError(mActivity, reason.getDialogMsgId(), reason.getFeedbackMsgId(),
                finishActivity);
    }

    @Override
    public void onCameraDisabledFailure() {
        // Log a stack trace to be sure we can track the source.
        Log.e(TAG, "Handling Camera Disabled Failure:", new Exception());

        // Log the error
        UsageStatistics.instance().cameraFailure(
                eventprotos.CameraFailure.FailureReason.SECURITY, null,
                UsageStatistics.NONE, UsageStatistics.NONE);

        Reason reason = Reason.CAMERA_DISABLED_BY_SECURITY_POLICY;
        boolean finishActivity = reason.doesFinishActivity();
        CameraUtil.showError(mActivity, reason.getDialogMsgId(), reason.getFeedbackMsgId(),
                finishActivity);
    }

    @Override
    public void handleFatalError(Reason reason) {
        // Log a stack trace to be sure we can track the source.
        Log.e(TAG, "Handling Fatal Error:", new Exception());

        boolean finishActivity = reason.doesFinishActivity();
        CameraUtil.showError(mActivity, reason.getDialogMsgId(), reason.getFeedbackMsgId(),
                finishActivity);
    }
}

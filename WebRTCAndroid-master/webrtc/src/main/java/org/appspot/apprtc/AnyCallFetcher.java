/*
 *  Copyright 2014 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package org.appspot.apprtc;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import org.appspot.apprtc.AppRTCClient.SignalingParameters;
import org.appspot.apprtc.util.AsyncHttpURLConnection;
import org.appspot.apprtc.util.AsyncHttpURLConnection.AsyncHttpEvents;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.PeerConnection;
import org.webrtc.SessionDescription;

/**
 * AsyncTask that converts an AppRTC room URL into the set of signaling
 * parameters to use with that room.
 */
public class AnyCallFetcher {
  private static final String TAG = "RoomRTCClient";
  private final CallFetcherEvents events;
  private final String roomUrl;
  private final String clientId;

  /**
   * Room parameters fetcher callbacks.
   */
  public interface CallFetcherEvents {
    /**
     * Callback fired once the room's signaling parameters
     * SignalingParameters are extracted.
     */
    void onCallFetcherResponse(final String params);

    /**
     * Callback for room parameters extraction error.
     */
    void onCallFetcherError(final String description);
  }

  public AnyCallFetcher(
          String roomUrl, String clientId, final CallFetcherEvents events) {
    this.roomUrl = roomUrl;
    this.clientId = clientId;
    this.events = events;
  }

  public void makeRequest() {
    Log.d(TAG, "Connecting to room: " + roomUrl);
    AsyncHttpURLConnection httpConnection =
            new AsyncHttpURLConnection("GET", roomUrl + "/client/" + clientId, "", new AsyncHttpEvents() {
              @Override
              public void onHttpError(String errorMessage) {
                Log.e(TAG, "Room connection error: " + errorMessage);
                events.onCallFetcherError(errorMessage);
              }

              @Override
              public void onHttpComplete(String response) {
                roomHttpResponseParse(response);
              }
            });
    httpConnection.send();
  }

  private void roomHttpResponseParse(String response) {
    events.onCallFetcherResponse(response);
  }
}

/*
 *  Copyright (c) 2014 The WebRTC project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree.
 */

/* More information about these options at jshint.com/docs/options */

/* globals trace, requestIceServers, sendUrlRequest, sendAsyncUrlRequest,
   SignalingChannel, PeerConnectionClient, setupLoopback,
   parseJSON, isChromeApp, apprtc, Constants */

/* exported Call */

'use strict';

var Registerforme = function(params, clientid) {
  this.params_ = params;
  this.roomServer_ = params.roomServer || '';

  this.channel_ = new SignalingChannel(params.wssUrl, params.wssPostUrl);
  this.channel_.onmessage = this.onRecvSignalingChannelMessage_.bind(this);

 
};



 
 
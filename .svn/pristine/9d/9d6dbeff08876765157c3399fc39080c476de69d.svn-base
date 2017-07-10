// { "framework": "Vue" }
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _stringify = __webpack_require__(8);

	var _stringify2 = _interopRequireDefault(_stringify);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	/**
	 * Created by edwinZhang on 17/5/22.
	 */
	var stream = weex.requireModule('stream');

	var pMethod = 'POST';
	var gMethod = 'GET';
	var requestType = 'json';
	var eventModule = weex.requireModule('WXEventModule');

	exports.postData = function (url, bodyObject, completeCallback, progressCallBack) {
	    var options = {
	        method: pMethod,
	        url: url,
	        type: requestType,
	        headers: { 'TokenId': eventModule.getTokenId(), 'Content-Type': 'application/json' }
	    };

	    if (bodyObject) {

	        options.body = (0, _stringify2.default)(bodyObject);
	    }

	    stream.fetch(options, function (res) {
	        try {
	            if (completeCallback) {
	                completeCallback(res.data);
	            }
	        } catch (e) {}
	    }, function (res) {
	        if (progressCallBack && res.status == 200) {
	            var curLength = res.length;
	            var ctLength = res.headers['Content-Length'];
	            var cpPercent = (curLength / ctLength).toFixed(2);
	            console.log("this postData progress" + cpPercent);
	            progressCallBack(cpPercent);
	        }
	    });
	};

	exports.getData = function (url, completeCallback, progressCallBack) {
	    var options = {
	        method: gMethod,
	        url: url,
	        type: requestType,
	        headers: { 'TokenId': eventModule.getTokenId(), 'Content-Type': 'application/json' }
	    };

	    stream.fetch(options, function (res) {
	        try {
	            for (var dt in res.data) {
	                console.log("request data is " + dt + "--->" + res.data[dt]);
	            }

	            if (completeCallback) {
	                completeCallback(res.data);
	            }
	        } catch (e) {}
	    }, function (res) {
	        if (progressCallBack && res.status == 200) {
	            var curLength = res.length;
	            var ctLength = res.headers['Content-Length'];
	            var cpPercent = (curLength / ctLength).toFixed(2);
	            console.log("this getData progress" + cpPercent);
	            progressCallBack(cpPercent);
	        }
	    });
	};

/***/ }),
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(9), __esModule: true };

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

	var core  = __webpack_require__(10)
	  , $JSON = core.JSON || (core.JSON = {stringify: JSON.stringify});
	module.exports = function stringify(it){ // eslint-disable-line no-unused-vars
	  return $JSON.stringify.apply($JSON, arguments);
	};

/***/ }),
/* 10 */
/***/ (function(module, exports) {

	var core = module.exports = {version: '2.4.0'};
	if(typeof __e == 'number')__e = core; // eslint-disable-line no-undef

/***/ })
/******/ ]);
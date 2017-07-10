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
/***/ (function(module, exports) {

	'use strict';

	/**
	 * Created by edwinZhang on 17/5/23.
	 */

	var modal = weex.requireModule('modal');

	exports.showToast = function (str) {
	    console.log('will show toast' + str);
	    modal.toast({
	        message: str,
	        duration: 0.3
	    });
	};

	exports.showAlert = function (str, callBack) {
	    console.log('will show alert' + str);
	    modal.alert({
	        message: str,
	        duration: 0.3
	    }, callBack);
	};

	exports.showConfirm = function (str, callBack) {
	    console.log('will show confirm');
	    modal.confirm({
	        message: 'Do you confirm ?',
	        duration: 0.3
	    }, callBack);
	};

	exports.showPrompt = function (str, callBack) {
	    console.log('will show prompt');
	    modal.prompt({
	        message: str,
	        duration: 0.3
	    }, callBack);
	};

/***/ })
/******/ ]);
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

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(4)
	)

	/* script */
	__vue_exports__ = __webpack_require__(5)

	/* template */
	var __vue_template__ = __webpack_require__(6)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "D:\\JavaProject\\weexpro\\src\\include\\pullrefresh.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-9fd5e286"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__
	module.exports.el = 'true'
	new Vue(module.exports)


/***/ }),
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */
/***/ (function(module, exports) {

	module.exports = {
	  "indicator": {
	    "width": 60,
	    "height": 60,
	    "color": "#889967"
	  },
	  "refresh-view": {
	    "height": 100,
	    "flex": 2,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "center"
	  }
	}

/***/ }),
/* 5 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var self;
	exports.default = {
	    props: {
	        height: {
	            type: [Number],
	            default: 0
	        },
	        width: {
	            type: [Number],
	            default: 0
	        },
	        ifrefresh: {
	            type: [String],
	            default: 'show'
	        },
	        ifloading: {
	            type: [String],
	            default: 'show'
	        }
	    },
	    data: function data() {
	        return {
	            refresh_display: 'hide',
	            showLoading: 'hide'
	        };
	    },

	    created: function created() {
	        self = this;
	        console.log("self.height" + self.height + self.width);
	        //        self.$on('indicator.onHide', self.hideIndicator);
	    },
	    methods: {
	        onrefresh: function onrefresh(event) {
	            self.refresh_display = 'show';
	            self.$emit('refreshStart');
	            console.log('is onrefresh start');
	        },
	        onpullingdown: function onpullingdown(event) {
	            console.log('is onpulling down');
	        },
	        onloading: function onloading(event) {
	            self.showLoading = 'show';
	            self.$emit('loadingStart');
	            console.log('is onloading start');
	        },
	        hideIndicator: function hideIndicator() {
	            if (self.refresh_display == 'show') {
	                self.refresh_display = 'hide';
	            }
	            if (self.showLoading == 'show') {
	                self.showLoading = 'hide';
	            }
	            console.log("is hideIndicator");
	        }
	    }
	};
	module.exports = exports['default'];

/***/ }),
/* 6 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('scroller', [(_vm.ifrefresh == 'show') ? _c('refresh', {
	    staticClass: ["refresh-view"],
	    style: {
	      width: _vm.width
	    },
	    attrs: {
	      "display": _vm.refresh_display
	    },
	    on: {
	      "refresh": _vm.onrefresh
	    }
	  }, [_c('loading-indicator', {
	    staticClass: ["indicator"]
	  }), _c('text', [_vm._v("Refreshing ...")])], 1) : _vm._e(), _c('div', {
	    style: {
	      height: _vm.height,
	      width: _vm.width
	    }
	  }, [_vm._t("default")], 2), (_vm.ifloading == 'show') ? _c('loading', {
	    staticClass: ["refresh-view"],
	    style: {
	      width: _vm.width
	    },
	    attrs: {
	      "display": _vm.showLoading
	    },
	    on: {
	      "loading": _vm.onloading
	    }
	  }, [_c('loading-indicator', {
	    staticClass: ["indicator"]
	  }), _c('text', [_vm._v("Loading ...")])], 1) : _vm._e()], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ })
/******/ ]);
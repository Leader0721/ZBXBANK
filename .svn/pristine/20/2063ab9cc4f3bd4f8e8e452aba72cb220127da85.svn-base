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
/******/ ({

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(91)
	)

	/* script */
	__vue_exports__ = __webpack_require__(92)

	/* template */
	var __vue_template__ = __webpack_require__(94)
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
	__vue_options__.__file = "D:\\JavaProject\\weexpro\\src\\include\\topbartest.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-7fb192b9"
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

/***/ 88:
/***/ (function(module, exports) {

	module.exports = {
	  "tabText": {
	    "fontSize": 38,
	    "margin": 20
	  },
	  "kBgColorStyle": {
	    "backgroundColor": "rgb(247,247,247)"
	  },
	  "tabTextBG": {
	    "alignItems": "center",
	    "borderBottomWidth": 2
	  },
	  "tabTextBGSelected": {
	    "borderBottomColor": "#0000FF"
	  },
	  "tabTextBGNormal": {
	    "borderBottomColor": "rgba(0,0,0,0)"
	  }
	}

/***/ }),

/***/ 89:
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
	//
	//
	//
	//


	var animation = weex.requireModule('animation');
	var modal = weex.requireModule('modal');

	exports.default = {

	    props: {

	        tabColorNormal: {
	            default: 'black'
	        },

	        tabColorSelected: {
	            default: 'blue'
	        },
	        screenWidth: {
	            default: 750
	        },
	        tabModels: {
	            //               default:[{'title':'大家说',"isSelected":true},
	            //                    {'title':'详情',"isSelected":false},
	            //                    {'title':'进展',"isSelected":false},
	            //                ]
	            default: []
	        },

	        currentIndex: {
	            default: 0
	        }
	    },

	    data: function data() {
	        return {

	            //                tabModels:[{'title':'大家说',"isSelected":true},
	            //                    {'title':'详情',"isSelected":false},
	            //                    {'title':'进展',"isSelected":false},
	            //                ],
	            //                tabColorNormal:"black",
	            //                tabColorSelected:"blue",
	            //                screenWidth:750,
	            //                currentIndex:0,


	        };
	    },


	    methods: {
	        tabTextClick: function tabTextClick(i, arr) {

	            if (arr[i].isSelected === true) {
	                return;
	            }

	            arr[this.currentIndex].isSelected = false;
	            arr[i].isSelected = !arr[i].isSelected;
	            this.currentIndex = i;

	            modal.toast({ message: '开始 $emit' });
	            this.$emit('tabClick');
	        }
	    }
	};
	module.exports = exports['default'];

/***/ }),

/***/ 90:
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["topMenu"],
	    class: ['kBgColorStyle'],
	    staticStyle: {
	      flexDirection: "row",
	      justifyContent: "space-around",
	      alignItems: "center",
	      position: "sticky"
	    }
	  }, _vm._l((_vm.tabModels), function(mod, i) {
	    return _c('div', {
	      staticStyle: {
	        justifyContent: "space-around"
	      }
	    }, [_c('div', {
	      staticClass: ["tabTextBG"],
	      class: [mod.isSelected ? 'tabTextBGSelected' : 'tabTextBGNormal']
	    }, [_c('text', {
	      staticClass: ["tabText"],
	      style: {
	        color: mod.isSelected ? _vm.tabColorSelected : _vm.tabColorNormal
	      },
	      on: {
	        "click": function($event) {
	          _vm.tabTextClick(i, _vm.tabModels)
	        }
	      }
	    }, [_vm._v(_vm._s(mod.title) + "\n                ")])])])
	  }))])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),

/***/ 91:
/***/ (function(module, exports) {

	module.exports = {}

/***/ }),

/***/ 92:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _topbarMenu = __webpack_require__(93);

	var _topbarMenu2 = _interopRequireDefault(_topbarMenu);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

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
	//
	//
	//
	//

	var animation = weex.requireModule('animation');
	var modal = weex.requireModule('modal');

	exports.default = {
	    components: {
	        topbarmenu: _topbarMenu2.default
	    },

	    data: function data() {
	        return {
	            tabModels: [{ 'title': '大家说', "isSelected": true }, { 'title': '详情', "isSelected": false }, { 'title': '进展', "isSelected": false }],
	            //                tabColorNormal:"black",
	            //                tabColorSelected:"blue",
	            screenWidth: 750,
	            currentIndex: 0
	        };
	    },


	    methods: {
	        topBarClick: function topBarClick(e) {

	            modal.toast({ message: 'e =' + e });
	        },
	        tabClick: function tabClick(i) {
	            modal.toast({ message: 'tabClick : i =' + i });
	        }
	    }
	};
	module.exports = exports['default'];

/***/ }),

/***/ 93:
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(88)
	)

	/* script */
	__vue_exports__ = __webpack_require__(89)

	/* template */
	var __vue_template__ = __webpack_require__(90)
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
	__vue_options__.__file = "D:\\JavaProject\\weexpro\\src\\include\\topbar-menu.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-a8fa3e16"
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


/***/ }),

/***/ 94:
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["mainScroller"],
	    style: {
	      height: _vm.scrollerHeight + 'px',
	      width: _vm.screenWidth + 'px'
	    }
	  }, [_c('text', {
	    staticStyle: {
	      textAlign: "center",
	      width: "750px",
	      marginTop: "44px",
	      marginBottom: "44px"
	    }
	  }, [_vm._v("这里是头部")]), _c('topbarmenu', {
	    attrs: {
	      "tabModels": _vm.tabModels,
	      "tabTextClick": _vm.topBarClick
	    }
	  }), _vm._m(0)], 1)])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticStyle: {
	      width: "750px",
	      height: "1500px",
	      backgroundColor: "darkgrey"
	    }
	  }, [_c('text', [_vm._v("xxx")])])
	}]}
	module.exports.render._withStripped = true

/***/ })

/******/ });
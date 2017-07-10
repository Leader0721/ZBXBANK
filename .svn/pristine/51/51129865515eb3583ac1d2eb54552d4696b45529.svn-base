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
	__vue_styles__.push(__webpack_require__(108)
	)

	/* script */
	__vue_exports__ = __webpack_require__(109)

	/* template */
	var __vue_template__ = __webpack_require__(110)
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
	__vue_options__.__file = "/Users/ZGX/Desktop/Work/移动端Web/trunk/weexpro/src/ui/mifi/ui_mifi_updatelog.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-e99909ac"
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

/***/ 108:
/***/ (function(module, exports) {

	module.exports = {
	  "headerBg": {
	    "width": 750,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "center",
	    "backgroundColor": "rgb(247,247,247)"
	  },
	  "left-image": {
	    "position": "absolute",
	    "left": 28,
	    "top": 14,
	    "width": 60,
	    "height": 60
	  },
	  "cellBubble": {
	    "width": 20,
	    "height": 20,
	    "borderRadius": 10,
	    "backgroundColor": "#008000"
	  },
	  "cellBubbleTopMargin": {
	    "marginTop": 32
	  },
	  "navTitle": {
	    "fontSize": 40,
	    "fontWeight": "bold"
	  },
	  "blackText": {
	    "color": "#000000"
	  },
	  "grayText": {
	    "color": "rgb(167,164,168)"
	  },
	  "borderColorStyle": {
	    "borderColor": "rgb(229,229,229)"
	  },
	  "vLineBGColor": {
	    "backgroundColor": "rgb(229,229,229)"
	  }
	}

/***/ }),

/***/ 109:
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

	var openFlag = false;

	//    var r = parseInt(random(0, 254));
	//    var g = parseInt(random(0, 254));
	//    var b = parseInt(random(0, 254));
	//    var cl =  rgb(r, g, b)


	exports.default = {
	    data: function data() {
	        return {

	            navBarHeight: 88,
	            titleTxt: "更新日志",
	            screenWidth: 750,
	            leftImg: "http://59.110.216.150:8081/images/settingBarIcon@2x.png",
	            //              leftItemImg:"../../../image/arrow.png",


	            lists: [{ "color": "#ddd", "title": "V5.0.1(最新版本)", "subs": ["1.该版本新增功能1234567789444444332222", "2.新增功能2", "3.修复了一些未知bug该版本新增功能1234567789444444332222"] }, { "color": "#fda3a2", "title": "V5.0.0(最新版本)", "subs": ["1.该版本新增功能1", "2.新增功能2", "3.修复了一些未知bug"] }, { "color": "red", "title": "V4.9.9(历史版本)", "subs": ["1.该版本新增功能1", "2.新增功能2"] }, { "color": "green", "title": "V4.9.8(历史版本)", "subs": ["1.该版本新增功能1", "2.新增功能2", "3.修复了一些未知bug"] }, { "color": "blue", "title": "V4.9.7(历史版本)", "subs": ["1.该版本新增功能1", "2.新增功能2", "3.修复了一些未知bug"] }, { "color": "orange", "title": "V4.9.6(历史版本)", "subs": ["1.该版本新增功能1", "2.新增功能2"] }]

	        };
	    },

	    methods: {
	        // 返回按钮的点击事件
	        backNavClick: function backNavClick() {
	            modal.toast({ message: "点击了返回按钮" });
	        },

	        // 上拉加载更多
	        fetch: function fetch(event) {
	            var _this = this;

	            modal.toast({ message: 'loadmore', duration: 1 });

	            setTimeout(function () {
	                var arr = _this.lists;
	                _this.lists = _this.lists.concat(arr);
	                //                    modal.toast({ message: 'lenght' + this.lists.length, duration: 1 })
	            }, 800);
	        },


	        // cell点击
	        cellClick: function cellClick(lists, i) {

	            //                modal.toast({ message: '点击了:'+ lists[i].title, duration:0.8 })
	            modal.toast({ message: '点击了cell:' + i, duration: 0.8 });
	        }

	    }

	};
	module.exports = exports['default'];

/***/ }),

/***/ 110:
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["headerBg"],
	    style: {
	      height: _vm.navBarHeight + 'px'
	    }
	  }, [_c('image', {
	    staticClass: ["left-image"],
	    attrs: {
	      "src": _vm.leftImg
	    },
	    on: {
	      "click": _vm.backNavClick
	    }
	  }), _c('text', {
	    class: ['blackText', 'navTitle']
	  }, [_vm._v(" 更新日志")])]), _c('list', {
	    staticClass: ["list"],
	    attrs: {
	      "loadmoreoffset": "10"
	    },
	    on: {
	      "loadmore": _vm.fetch
	    }
	  }, _vm._l((_vm.lists), function(mod, i) {
	    return _c('cell', {
	      staticClass: ["cell"],
	      appendAsTree: true,
	      attrs: {
	        "append": "tree"
	      }
	    }, [_c('div', {
	      staticClass: ["cellBG"],
	      staticStyle: {
	        flexDirection: "row"
	      },
	      on: {
	        "click": function($event) {
	          _vm.cellClick(_vm.lists, i)
	        }
	      }
	    }, [_c('div', {
	      staticClass: ["celLLeft"],
	      staticStyle: {
	        width: "118px",
	        alignItems: "center"
	      }
	    }, [(i > 0) ? _c('div', {
	      class: ['vLineBGColor'],
	      staticStyle: {
	        width: "2px",
	        height: "32px"
	      }
	    }) : _vm._e(), (i < 1) ? _c('div', {
	      staticStyle: {
	        width: "2px",
	        height: "32px"
	      }
	    }) : _vm._e(), _c('div', {
	      staticClass: ["cellBubble"]
	    }), (i < _vm.lists.length - 1) ? _c('div', {
	      class: ['vLineBGColor'],
	      staticStyle: {
	        width: "2px",
	        flex: "1"
	      }
	    }) : _vm._e()]), _c('div', {
	      staticClass: ["cellRight"],
	      staticStyle: {
	        marginTop: "18px",
	        marginBottom: "18px"
	      }
	    }, [_c('text', {
	      staticClass: ["cellTitle"],
	      class: ['blackText'],
	      staticStyle: {
	        fontSize: "36px",
	        marginBottom: "20px",
	        width: "520px"
	      }
	    }, [_vm._v(_vm._s(mod.title))]), _vm._l((mod.subs), function(txt) {
	      return _c('div', [_c('text', {
	        staticClass: ["cellSubTitle"],
	        class: ['grayText'],
	        staticStyle: {
	          fontSize: "34px",
	          width: "520px"
	        }
	      }, [_vm._v(_vm._s(txt))])])
	    })], 2)])])
	  }))])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ })

/******/ });
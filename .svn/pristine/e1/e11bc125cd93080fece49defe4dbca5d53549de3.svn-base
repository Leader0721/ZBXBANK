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
	__vue_styles__.push(__webpack_require__(84)
	)

	/* script */
	__vue_exports__ = __webpack_require__(85)

	/* template */
	var __vue_template__ = __webpack_require__(86)
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
	__vue_options__.__file = "D:\\JavaProject\\weexpro\\src\\include\\dropdownmenu.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-7171418e"
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

/***/ 84:
/***/ (function(module, exports) {

	module.exports = {
	  "select-container": {
	    "flexDirection": "column",
	    "position": "relative",
	    "zIndex": 1000,
	    "overflow": "hidden"
	  },
	  "content": {
	    "width": 750,
	    "marginTop": 90
	  },
	  "select": {
	    "width": 750,
	    "height": 90,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "space-between",
	    "paddingLeft": 30,
	    "paddingRight": 30,
	    "borderBottomWidth": 1,
	    "borderStyle": "solid",
	    "borderColor": "#dddddd",
	    "backgroundColor": "#eeeeee",
	    "zIndex": 1001,
	    "position": "absolute",
	    "top": 0
	  },
	  "current-text": {
	    "color": "#333333",
	    "fontSize": 33,
	    "flex": 1
	  },
	  "icon-arrow": {
	    "width": 27,
	    "height": 23
	  },
	  "options": {
	    "position": "absolute",
	    "top": -91,
	    "width": 750,
	    "backgroundColor": "#ffffff",
	    "transformOrigin": "center center",
	    "display": "none"
	  },
	  "cell": {
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center",
	    "width": 750,
	    "height": 90,
	    "paddingLeft": 30,
	    "paddingRight": 30,
	    "borderBottomWidth": 1,
	    "borderStyle": "solid",
	    "borderColor": "#dddddd"
	  },
	  "name": {
	    "color": "#333333",
	    "fontSize": 33,
	    "flex": 1
	  },
	  "icon-curr-flag": {
	    "width": 32,
	    "height": 32
	  },
	  "current": {
	    "color": "#0088FB"
	  }
	}

/***/ }),

/***/ 85:
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
	//
	//
	//

	var animation = weex.requireModule('animation');
	var self;
	exports.default = {
	    props: {
	        statusId: {
	            default: '0'
	        },
	        menudata: {
	            default: []
	        }
	    },
	    data: function data() {
	        return {
	            current_translate: 'translate(0, 0)',
	            current_rotate: 0,
	            flagSrc: 'image/flag.png',
	            arrowSrc: 'image/arrowmenu.png'
	        };
	    },

	    computed: {
	        statusName: {
	            get: function get() {
	                var id = this.statusId;
	                return this.menudata.filter(function (s) {
	                    return s.id == id;
	                })[0].name;
	            }
	        }
	    },
	    create: function create() {
	        self = this;
	    },
	    methods: {
	        switchView: function switchView() {
	            this.collapse(this.$refs.options);
	            this.rotate(this.$refs.arrow);
	        },
	        onItemClick: function onItemClick(e) {
	            var vid = e.target.attr.vid;
	            this.updateStatus(vid);
	            this.switchView();
	            this.$emit('statuschange', {
	                id: this.statusId,
	                name: this.statusName
	            });
	            console.log("onItemClick");
	        },
	        updateStatus: function updateStatus(id) {
	            this.statusId = id;
	        },
	        collapse: function collapse(ref, callback) {
	            var platform = this.$getConfig().env.platform;
	            var translate = 'translate(0, 100%)'; // Web need % ;
	            if (platform == 'iOS') {
	                var deg = this.menudata.length * 90;
	                translate = 'translate(0, ' + deg + ')'; // ios bug && fixing
	            }
	            this.current_translate = this.current_translate == 'translate(0, 0)' ? translate : 'translate(0, 0)';
	            this.anim(ref, {
	                transform: this.current_translate
	            }, 'ease', 100, callback);
	        },
	        rotate: function rotate(ref, callback) {
	            var self = this;
	            self.current_rotate = self.current_rotate == 0 ? 180 : 0;
	            self.anim(ref, {
	                transform: 'rotate(' + self.current_rotate + 'deg)'
	            }, 'linear', 100, callback);
	        },

	        anim: function anim(ref, styles, timingFunction, duration, callback) {
	            console.log("anim anim callback" + callback);
	            animation.transition(ref, {
	                styles: styles,
	                timingFunction: timingFunction,
	                duration: duration
	            }, callback || function () {});
	        }
	    }
	};
	module.exports = exports['default'];

/***/ }),

/***/ 86:
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["select-container"]
	  }, [_c('div', {
	    staticClass: ["content"]
	  }, [_vm._t("default")], 2), _c('div', {
	    ref: "options",
	    staticClass: ["options"],
	    style: {
	      top: '-' + ((_vm.menudata.length - 1) * 90)
	    }
	  }, _vm._l((_vm.menudata), function(item) {
	    return _c('div', {
	      staticClass: ["cell"],
	      attrs: {
	        "vid": item.id
	      },
	      on: {
	        "click": _vm.onItemClick
	      }
	    }, [_c('text', {
	      class: ['name', item.id == _vm.statusId ? 'current' : '']
	    }, [_vm._v(_vm._s(item.name))]), (item.id == _vm.statusId) ? _c('image', {
	      staticClass: ["icon-curr-flag"],
	      attrs: {
	        "src": _vm.flagSrc
	      }
	    }) : _vm._e()])
	  })), _c('div', {
	    staticClass: ["select"],
	    on: {
	      "click": _vm.switchView
	    }
	  }, [_c('text', {
	    staticClass: ["current-text"]
	  }, [_vm._v(_vm._s(_vm.statusName))]), _c('image', {
	    ref: "arrow",
	    staticClass: ["icon-arrow"],
	    attrs: {
	      "src": _vm.arrowSrc
	    }
	  })])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ })

/******/ });
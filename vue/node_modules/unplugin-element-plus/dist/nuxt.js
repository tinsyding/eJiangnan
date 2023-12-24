"use strict";Object.defineProperty(exports, "__esModule", {value: true});

var _chunkVTB3FEKNjs = require('./chunk-VTB3FEKN.js');

// src/nuxt.ts
function nuxt_default(options) {
  this.extendBuild((config) => {
    config.plugins = config.plugins || [];
    config.plugins.unshift(_chunkVTB3FEKNjs.src_default.webpack(options));
  });
  this.nuxt.hook("vite:extend", (vite) => {
    vite.config.plugins = vite.config.plugins || [];
    vite.config.plugins.push(_chunkVTB3FEKNjs.src_default.vite(options));
  });
}


exports.default = nuxt_default;

module.exports = exports.default
module.exports = {
    plugins: {
      'postcss-pxtorem': {
        rootValue: 37.5,
        // 根字体大小是 37.5
        propList: ['*'],
        selectorBlackList: ['.no__rem']
        // 过滤掉.no__rem-开头的class，不进行rem转换处理
      }
    }
  }
  
  
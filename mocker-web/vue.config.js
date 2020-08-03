const path = require("path");

module.exports = {
    // chainWebpack: (config) => {
    //     config.module
    //         .rule('images')
    //         .use('url-loader')
    //         .tap(options => Object.assign({}, options, { name: '[name].[ext]' }));
    // },
    outputDir: '../',
    css: {
        extract: {
            filename: '[name]-[hash].css',
            chunkFilename: '[name].css',
        },
    },
    configureWebpack: {
        output: {

            filename: '[name]-[hash].js',
            chunkFilename: '[name].js',
        }
    },
    assetsDir: ''
}
module.exports = {

    outputDir: '../src/main/resources/static',
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
    }

}
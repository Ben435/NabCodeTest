const path = require('path');
const merge = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = merge(common, {
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    devServer: {
        publicPath: '/built/',
        index: 'index.html',
        contentBase: [path.join(__dirname, 'src', 'main', 'resources', 'static')],
        compress: true,
        port: 9000,
        historyApiFallback: true,
        proxy: {
            '/api': 'http://localhost:8080'
        }
    }
});

const path = require('path');

module.exports = {
    entry: path.resolve('src', 'main', 'js', 'app.js'),
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: path.resolve(__dirname, 'src', 'main', 'resources', 'static', 'built'),
        filename: 'bundle.js'
    },
    devServer: {
        publicPath: '/built/',
        index: 'index.html',
        contentBase: [path.join(__dirname, 'src', 'main', 'resources', 'static')],
        compress: true,
        port: 9000,
        proxy: {
            '/api': 'http://localhost:8080'
        }
    },
    module: {
        rules: [
            {
                test:/\.js$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test:/\.scss$/,
                use:['style-loader', 'css-loader', 'sass-loader']
            }
        ]
    }
};
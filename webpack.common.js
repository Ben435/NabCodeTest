const path = require('path');

module.exports = {
    entry: path.resolve('src', 'main', 'js', 'index.js'),
    plugins: [],
    output: {
        path: path.resolve(__dirname, 'src', 'main', 'resources', 'static', 'built'),
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test:/\.js$/,
                exclude: /(node_modules)/,
                loader: 'babel-loader'
            },
            {
                test:/\.scss$/,
                use:['style-loader', 'css-loader', 'sass-loader']
            }
        ]
    }
};
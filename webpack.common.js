const path = require('path');

module.exports = {
    entry: path.resolve('src', 'main', 'js', 'app.js'),
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
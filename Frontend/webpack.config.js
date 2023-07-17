const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
    optimization: {
        usedExports: true
    },
    entry: {
        HomePage: path.resolve(__dirname, 'src', 'pages', 'HomePage.js'),
        header: path.resolve(__dirname, 'src', 'pages', 'header.js'),
        CreateRecipePage: path.resolve(__dirname, 'src', 'pages', 'CreateRecipePage.js'),
        RecipeDetailsPage: path.resolve(__dirname, 'src', 'pages', 'RecipeDetailsPage.js'),
        RecipeListPage: path.resolve(__dirname, 'src', 'pages', 'RecipeListPage.js'),
        PopulateOptions: path.resolve(__dirname, 'src', 'pages', 'PopulateOptions.js'),
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].js',
    },
    devServer: {
        https: false,
        port: 8080,
        open: true,
        proxy: [
            {
                context: [
                    '/recipe'
                ],
                target: 'http://localhost:63342/kenzie-lbc-project/kenzie-lbc-project.Frontend'
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './src/HomePage.html',
            filename: 'HomePage.html',
        }),
        new HtmlWebpackPlugin({
            template: './src/RecipeListPage.html',
            filename: 'RecipeListPage.html',
        }),
        new HtmlWebpackPlugin({
            template: './src/RecipeDetailsPage.html',
            filename: 'RecipeDetailsPage.html',
        }),
        new HtmlWebpackPlugin({
            template: './src/CreateRecipePage.html',
            filename: 'CreateRecipePage.html',
        }),
        new HtmlWebpackPlugin({
            template: './src/RecipeListPage.html',
            filename: 'RecipeListPage.html',
        }),
        new HtmlWebpackPlugin({
            template: './src/RecipeDetailsPage.html',
            filename: 'RecipeDetailsPage.html',
        }),
        new CopyPlugin({
            patterns: [
                {
                    from: path.resolve('src/css'),
                    to: path.resolve("dist/css")
                }
            ]
        }),
        new CleanWebpackPlugin()
    ],
    module: {
        rules: [
            {
                test: /\.js$/, // Apply the loader to JavaScript files
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env'],
                    },
                },
            },
        ],
    },
    resolve: {
        extensions: ['.js'], // Enable importing modules without specifying the extension
        modules: [path.resolve(__dirname, 'src'), 'node_modules'], // Specify the module resolution paths
    },
};

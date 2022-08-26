module.exports = {
    pages: {
      index: {

        entry: 'src/main.js',
        
      },
      
    },
    lintOnSave: false,


    devServer: {
      disableHostCheck:true,
      proxy: {
        '/english': {//请求前缀
          target: 'http://localhost:8081',
          pathRewrite:{'^/english':'/'},
        },
        '/wallpaper/':{
          target: 'https://cn.bing.com',
          pathRewrite:{'^/wallpaper/':'/'},
        },
        '/day/':{
          target: 'http://sentence.iciba.com',
          pathRewrite:{'^/day/':'/'},
        },
        '/soundmark/':{
          target:'https://dict-co.iciba.com',
          pathRewrite:{'^/soundmark/':'/'}
        }
      }
    }
}
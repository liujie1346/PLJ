# frontend

> 个人项目开发

## 项目结构
>>
    ├── build                      # 构建相关                  
    ├── static                     # 静态资源
    │   │── img                    # 图片
    │   │── favicon.ico            # favicon图标
    │   └── index.html             # html模板
    │   │── Tinymce                # 富文本
    ├── src                        # 源代码
    │   ├── api                    # 所有请求
    │   ├── assets                 # 主题 字体等静态资源
    │   ├── components             # 全局公用组件
    │   ├── directive              # 全局指令
    │   ├── icons                  # 项目所有 svg icons
    │   ├── lang                   # 国际化 language
    │   ├── layout                 # 全局 layout
    │   ├── router                 # 路由
    │   ├── store                  # 全局 store管理
    │   ├── styles                 # 全局样式
    │   ├── utils                  # 全局公用方法
    │   ├── views                  # views 所有页面
    │   ├── App.vue                # 入口页面
    │   ├── main.js                # 入口文件 加载组件 初始化等
    │   └── permission.js          # 权限管理
    ├── tests                      # 测试
    ├── .env.xxx                   # 环境变量配置
    ├── .eslintrc.js               # eslint 配置项
    ├── .gitignore                 # git 配置项
    ├── .babelrc                   # babel-loader 配置
    ├── .travis.yml                # 自动化CI配置
    ├── vue.config.js              # vue-cli 配置
    └── package.json               # package.json




 

  ##全屏
  cnpm install --save screenfull@4.2.1
  在5.0上版本时全屏不可用
  https://blog.csdn.net/qq_40954793/article/details/84847392
  
  ## Cookie
  cnpm install js-cookie --save
  https://www.cnblogs.com/SRH151219/p/10500325.html

# 优存（YouCun）- 文档图片存储系统 （学习项目）📂

**![License](https://img.shields.io/badge/license-MIT-blue.svg) ![Frontend](https://img.shields.io/badge/frontend-Vue3-brightgreen.svg) ![Backend](https://img.shields.io/badge/backend-Spring%20Boot-orange.svg) ![Database](https://img.shields.io/badge/database-MySQL-blueviolet.svg)

一款基于 Vue3 + Spring Boot + MySQL 开发的**安全便捷**文档图片存储系统，支持用户管理、文档加密、图片上传等核心功能，适用于个人或小型团队的文档图片管理需求，现已开源，欢迎开发者参与贡献！

> ⚠️ 本项目为开源技术学习案例，代码/命名不是很规范。

## ✅ 核心功能

### 👤 用户模块

- **账号管理**：支持手机号 / 邮箱注册登录，Token 身份验证，保障账号安全。
- **会员体系**：VIP 充值解锁高级功能（如文档加密），存储空间单独扩容，满足不同需求。

### 📄 文档模块

- **权限控制**：文档专属 Token 访问，可设置开启 / 关闭状态，添加访问密码双重防护。

- **内容加密**：支持文档加密密钥设置，对文档元数据、关联图片列表加密存储，隐私性更强。

- **多格式访问**：提供 JSON 格式（程序调用）与 Text 格式（人工查看），适配不同使用场景。

### 🖼️ 图片模块

- **高效上传**：支持单张 / 多张图片上传，限制格式（JPG/PNG/GIF）与大小，生成唯一标识关联文档。

- **便捷管理**：单个 / 批量删除图片，自动更新文档关联列表，释放存储空间。

- **安全访问**：通过图片 ID 或文档 Token 验证访问，支持预览与下载，防止非法获取。

## 🛠️ 技术栈详情

### 前端技术栈

| 技术           | 作用说明                                        |
| -------------- | ----------------------------------------------- |
| **Vue 3**      | 核心框架，采用 Composition API 提升代码可维护性 |
| **Vue Router** | 路由管理，实现页面跳转与权限控制                |
| **Pinia**      | 状态管理（可选），轻量类型安全替代 Vuex         |
| **Axios**      | HTTP 请求工具，支持拦截器与错误处理             |

### 后端技术栈

| 技术            | 作用说明                                 |
| --------------- | ---------------------------------------- |
| **Spring Boot** | 核心框架，简化配置快速开发后端服务       |
| **MySQL**       | 关系型数据库，存储用户、文档、图片元数据 |

## 🚀 快速部署指南

### 1. 环境准备

- **前端**：Node.js v24.6.0

- **后端**：jdk-17.0.8
- **数据库**：MySQL 8.0
- **服务器（可选）**：Linux（CentOS/Ubuntu/Arche），开放 8080（前端/后端）、3306（MySQL）端口

### 2. 后端配置

1. 修改配置文件（application.yml）：

- - 配置 MySQL 连接：spring.datasource.url、username、password
- - 配置 Mail服务：username，password

2. 修改配置文件（com/api/you_cun/config/config.java）：

- - 配置管理：FileSize（NORMAL，VIP），wz，admin_password，allowedExts，MAX_SIZE

```java
public class config {
    //管理员密码
    public static final String admin_password ="mw200464";
    //网站配置
    public static final String wz = "http://api.maolinux.top";
    //设置项目文件夹
    public static final String UPLOAD_PATH = System.getProperty("user.dir") + "/upload/";

    public static final class FileSize {
        public static final int NORMAL = 5242880;  // 普通用户空间
        public static final int VIP = 10485760;    // VIP用户空间
    }
    // 允许上传的文件格式
    public static final String[] allowedExts = {"jpg", "jpeg", "png", "webp"};
    // 单张图片最大内存最大内存限制（单位：字节，5MB）
    public static final long MAX_SIZE = 5242880;
}

```

### 3. 前端配置

1. 进入前端目录：

```shell
cd you_cun1
```

2. 修改/优存/vite.config.ts配置：
```tsx
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    allowedHosts: [
      'localhost',
      '127.0.0.1',
      '前端域名'
    ],

    proxy: {
      '/api': {
        target: 'http://后端域名',
        changeOrigin: true,
      }
    }
  }
})
```

3. 安装依赖并配置：

```shell
npm install
```

4. 运行测试：

```shell
npm run dev
```

## 📝 基础使用流程

1. **账号注册登录**：访问前端页面，完成注册后登录，系统自动存储 Token 保持登录状态。

2. **创建文档**：进入 “文档管理” 页面，点击 “新建文档”，填写名称与描述提交。

3. **上传图片**：进入文档详情页，点击 “上传图片”，选择本地图片完成上传，可实时预览。

4. **访问与管理**：

- - 文档：通过详情页查看，或调用 API 接口获取 JSON/Text 格式数据。

- - 图片：点击预览或调用接口下载，需验证 Token 或文档密码（若设置）。

5. **高级设置**：在 “个人中心” 充值 VIP / 存储空间，在文档 “设置” 中配置密码与加密密钥。

## 🎨 截图预览

<div style="display: flex; gap: 20px; flex-direction: column; justify-content: center;">
  <img 
    src="https://github.com/1228727365/YouCun/blob/main/image/1.png" 
    style="width: 300px; height: auto; object-fit: contain;"
  >
  <img 
    src="https://github.com/1228727365/YouCun/blob/main/image/2.png" 
    style="width: 300px; height: auto; object-fit: contain;"
  >
    <img 
    src="https://github.com/1228727365/YouCun/blob/main/image/3.png" 
    style="width: 300px; height: auto; object-fit: contain;"
  >
</div>


## 🤝 贡献指南

欢迎开发者参与项目贡献，无论是 Bug 修复、功能优化还是新功能开发，流程如下：

## 📄 开源协议

本项目基于 [MIT License](LICENSE) 开源，允许自由使用、修改与分发，使用时请保留原作者信息。

## 📬 联系方式

若使用或开发中遇到问题，可通过以下方式联系：

- QQ：[1228727365](http://wpa.qq.com/msgrd?v=3&uin=1228727365&site=qq&menu=yes)（点击联系）
- Email：maojas@163.com（可选）
- GitHub：[M](https://github.com/1228727365)

## 🙌 致谢

感谢 Vue、Spring Boot等开源框架的开发者，以及所有为项目提供建议与帮助的贡献者！

# MQTTPushExample
SpringBoot MQTT Server &amp; Android MQTT Client  Example.

## 一、MQTT 服务器

市面上的 MQTT 服务器有很多, 如 ActiveMQ "Classic"/ Artemis、EMQX等；在该项目中使用了 EMQX 作为MQTT服务器搭建。

### Mac OS 上 EMQX 搭建指南

1. 下载最新版本的 下载 `emqx-macos-*-amd64.zip` 文件(项目使用 4.3.7版本测试)

   ```shell
   # 如果未安装 `wget`, 需要通过 `brew install wget` 命令安装
   macbox:mqtt $ wget https://www.emqx.com/zh/downloads/broker/4.3.7/emqx-macos-4.3.7-amd64.zip
   ```

2. 解压已经下载好的 `emqx-macos-*-amd64.zip` 文件

   ```shell
   macbox:mqtt $ unzip emqx-macos-4.3.7-amd64.zip
   ```

3. 启动 EMQX 服务器

   ```shell
   macbox:mqtt $ ./emqx/bin/emqx start
   ```

### 其他环境上 EMQX 搭建指南

移步官方文档: https://www.emqx.com/zh/downloads?product=broker

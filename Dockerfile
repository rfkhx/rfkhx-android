FROM registry.cn-shanghai.aliyuncs.com/wgyscsf/docker_buildandroid:0.0.1
MAINTAINER wgyscsf "414850132@qq.com"

# 编译当前项目
ENV PROJECT /project
RUN mkdir $PROJECT
WORKDIR $PROJECT
ADD . $PROJECT
RUN chmod +x ./gradlew
RUN echo "sdk.dir=$ANDROID_HOME" > local.properties
RUN ./gradlew --stacktrace app:dependencies
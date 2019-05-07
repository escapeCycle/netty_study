https://github.com/protocolbuffers/protobuf
https://github.com/protocolbuffers/protobuf/releases
安装protobuf编译器  在PATH中设置环境变量, 编写.proto文件

在项目下运行 protoc --java_out=src/main/java src/protobuf/Student.proto

生成出来的文件永远不要修改,修改后,再生成的时候会被覆盖掉
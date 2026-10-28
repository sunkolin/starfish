### additional-spring-configuration-metadata.json文件是什么？


- 使用maven打包时，spring-boot-configuration-processor会自动理处自动生成spring-configuration-metadata.json文件的，放在target目录下。

- additional-spring-configuration-metadata.json,手动编写的补充元数据，放在源码目录 `src/main/resources/META‑INF/additional‑spring‑configuration‑metadata.json

- 这个是源码文件，自己维护，不会被编译覆盖。编译打包后，会和自动生成的 spring‑configuration‑metadata.json 合并，一起打到META‑INF


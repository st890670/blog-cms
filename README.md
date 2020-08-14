# Blog CMS

- 前端: React  
- 後端: SpringBoot  
- 資料庫: PostgreSQL
  
- SpringBoot DEV Port: 8001  
- React DEV Port: 8899  

- 記得到application.properties修改db連線資訊

docker run command:
```
docker run -d -p 8001:8443 --name tomcat-blog-back -v /home/docker/tomcat/blog-back/webapps:/usr/local/tomcat/webapps:z -v /home/docker/tomcat/blog-back/conf:/usr/local/tomcat/conf:z -v /home/docker/tomcat/blog-back/logs:/usr/local/tomcat/logs:z -v /home/docker/blog-attach:/root/blog-attach:z --restart=always tomcat:9.0.43
```
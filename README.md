docker build -t pool-springboot-demoapp:latest .
docker tag pool-springboot-demoapp:latest williamst/pool-springboot-demoapp:latest
docker push williamst/pool-springboot-demoapp:latest
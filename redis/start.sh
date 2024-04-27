docker build -t redis-service .

docker run -it --name redis-service -p 8080:8080 --network host redis-service
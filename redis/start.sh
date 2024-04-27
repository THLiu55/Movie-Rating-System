docker build -t redis-service .

docker run -it --name redis-service --network host redis-service
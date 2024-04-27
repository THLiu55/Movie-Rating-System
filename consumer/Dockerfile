FROM python:3.8-slim

COPY . /usr/src/consumer

WORKDIR /usr/src/consumer

COPY requirements.txt .

RUN pip install -r requirements.txt

CMD ["python", "main.py"]

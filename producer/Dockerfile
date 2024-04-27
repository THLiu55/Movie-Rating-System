FROM python:3.8-slim

COPY . /usr/src/producer

WORKDIR /usr/src/producer

COPY requirements.txt .

RUN pip install -r requirements.txt

CMD ["python", "main.py"]
